'use client';

import { addDoc, collection, doc, serverTimestamp, setDoc } from 'firebase/firestore';
import { getFirebaseDb } from './firebase';
import type { LearnerProfileInput, SkillScore } from './learner';

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
