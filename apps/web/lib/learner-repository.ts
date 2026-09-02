'use client';

import {
  addDoc,
  collection,
  doc,
  getDoc,
  getDocs,
  serverTimestamp,
  setDoc,
} from 'firebase/firestore';
import { getFirebaseDb } from './firebase';
import type { LearnerProfileInput, SkillScore } from './learner';
import { calculateNextReviewAt, type ActivityAttempt, type AdaptiveSessionState } from './activity-engine';

export type PersistedLearner = LearnerProfileInput & {
  id: string;
  ownerUid: string;
};

export async function saveUserProfile(uid: string, email: string | null, displayName: string | null) {
  await setDoc(doc(getFirebaseDb(), 'users', uid), {
    uid,
    email,
    displayName,
    role: 'parent',
    updatedAt: serverTimestamp(),
  }, { merge: true });
}

export async function createLearner(ownerUid: string, input: LearnerProfileInput) {
  const ref = await addDoc(collection(getFirebaseDb(), 'learners'), {
    ownerUid,
    ...input,
    createdAt: serverTimestamp(),
    updatedAt: serverTimestamp(),
  });
  return ref.id;
}

export async function loadLearner(ownerUid: string, learnerId: string): Promise<PersistedLearner | null> {
  const snapshot = await getDoc(doc(getFirebaseDb(), 'learners', learnerId));
  if (!snapshot.exists()) return null;

  const data = snapshot.data();
  if (data.ownerUid !== ownerUid) return null;

  return {
    id: snapshot.id,
    ownerUid,
    name: data.name,
    group: data.group,
    homeLanguage: data.homeLanguage,
    supportLanguageEnabled: Boolean(data.supportLanguageEnabled),
  };
}

export async function loadSkillScores(ownerUid: string, learnerId: string): Promise<SkillScore[]> {
  const snapshot = await getDocs(collection(getFirebaseDb(), 'learners', learnerId, 'skillState'));

  return snapshot.docs
    .map((item) => item.data())
    .filter((data) => data.ownerUid === ownerUid)
    .map((data) => ({
      skillId: String(data.skillId),
      mastery: Number(data.mastery),
      priority: data.priority as SkillScore['priority'],
      evidenceCount: Number.isFinite(Number(data.evidenceCount)) ? Number(data.evidenceCount) : undefined,
      evidenceConfidence: Number.isFinite(Number(data.evidenceConfidence)) ? Number(data.evidenceConfidence) : undefined,
    }));
}

export async function loadAdaptiveSkillState(
  ownerUid: string,
  learnerId: string,
  skillId: string,
): Promise<AdaptiveSessionState | null> {
  const snapshot = await getDoc(doc(getFirebaseDb(), 'learners', learnerId, 'skillState', skillId));
  if (!snapshot.exists()) return null;

  const data = snapshot.data();
  if (data.ownerUid !== ownerUid) return null;

  const storedMastery = Number(data.mastery);
  const mastery = Number.isFinite(storedMastery)
    ? Math.max(0, Math.min(1, storedMastery > 1 ? storedMastery / 100 : storedMastery))
    : 0;

  return {
    skillId,
    mastery,
    evidenceCount: Number.isFinite(Number(data.evidenceCount)) ? Number(data.evidenceCount) : 0,
    recentAttempts: [],
  };
}

export async function persistAssessment(ownerUid: string, learnerId: string, scores: SkillScore[]) {
  await Promise.all(scores.map((score) => setDoc(
    doc(getFirebaseDb(), 'learners', learnerId, 'skillState', score.skillId),
    {
      ownerUid,
      skillId: score.skillId,
      mastery: score.mastery,
      priority: score.priority,
      evidenceCount: score.evidenceCount ?? 0,
      evidenceConfidence: score.evidenceConfidence ?? 0,
      source: 'baseline-assessment',
      updatedAt: serverTimestamp(),
    },
    { merge: true },
  )));

  await addDoc(collection(getFirebaseDb(), 'learners', learnerId, 'attempts'), {
    ownerUid,
    kind: 'baseline-assessment',
    scores,
    createdAt: serverTimestamp(),
  });
}

export async function persistLessonAttempt(
  ownerUid: string,
  learnerId: string,
  lessonId: string,
  attempt: ActivityAttempt,
  session: AdaptiveSessionState,
) {
  const priority = session.mastery < 0.5 ? 'high' : session.mastery < 0.75 ? 'medium' : 'low';
  const nextReviewAt = calculateNextReviewAt(session, attempt);

  await Promise.all([
    addDoc(collection(getFirebaseDb(), 'learners', learnerId, 'attempts'), {
      ownerUid,
      kind: 'lesson-activity',
      lessonId,
      ...attempt,
      masteryAfter: Math.round(session.mastery * 100),
      evidenceCount: session.evidenceCount,
      nextReviewAt,
      createdAt: serverTimestamp(),
    }),
    setDoc(doc(getFirebaseDb(), 'learners', learnerId, 'skillState', attempt.skillId), {
      ownerUid,
      skillId: attempt.skillId,
      mastery: Math.round(session.mastery * 100),
      priority,
      source: 'lesson-evidence',
      evidenceCount: session.evidenceCount,
      lastPractisedAt: attempt.attemptedAt,
      nextReviewAt,
      updatedAt: serverTimestamp(),
    }, { merge: true }),
  ]);
}
