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
import type { ActivityAttempt, AdaptiveSessionState } from './activity-engine';

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
    }));
}

export async function persistAssessment(ownerUid: string, learnerId: string, scores: SkillScore[]) {
  await Promise.all(scores.map((score) => setDoc(
    doc(getFirebaseDb(), 'learners', learnerId, 'skillState', score.skillId),
    {
      ownerUid,
      skillId: score.skillId,
      mastery: score.mastery,
      priority: score.priority,
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

  await Promise.all([
    addDoc(collection(getFirebaseDb(), 'learners', learnerId, 'attempts'), {
      ownerUid,
      kind: 'lesson-activity',
      lessonId,
      ...attempt,
      masteryAfter: Math.round(session.mastery * 100),
      evidenceCount: session.evidenceCount,
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
      updatedAt: serverTimestamp(),
    }, { merge: true }),
  ]);
}
