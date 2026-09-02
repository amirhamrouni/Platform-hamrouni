import assert from 'node:assert/strict';
import test from 'node:test';

import {
  type AdaptiveSessionState,
  type ActivityAttempt,
  type LearningActivity,
  calculateNextReviewAt,
  evaluateActivity,
  needsRelearning,
  recommendedDifficulty,
  selectNextActivity,
  selectRelearningActivity,
  updateAdaptiveMastery,
} from './activity-engine.ts';

const baseState: AdaptiveSessionState = {
  skillId: 'math-multiplication-foundations',
  mastery: 0.5,
  evidenceCount: 0,
  recentAttempts: [],
};

function attempt(overrides: Partial<ActivityAttempt> = {}): ActivityAttempt {
  return {
    activityId: 'a1',
    skillId: baseState.skillId,
    correct: true,
    difficulty: 3,
    responseTimeMs: 20_000,
    hintsUsed: 0,
    attemptedAt: '2026-09-02T06:00:00.000Z',
    ...overrides,
  };
}

const activities: LearningActivity[] = [
  {
    id: 'easy-related',
    skillId: baseState.skillId,
    kind: 'multiple-choice',
    prompt: 'easy',
    explanation: 'easy explanation',
    difficulty: 1,
    tags: ['groups', 'visual'],
    options: ['1', '2'],
    correctOption: 0,
  },
  {
    id: 'medium-related',
    skillId: baseState.skillId,
    kind: 'fill-blank',
    prompt: 'medium',
    explanation: 'medium explanation',
    difficulty: 2,
    tags: ['groups'],
    acceptedAnswers: ['12'],
  },
  {
    id: 'hard-unrelated',
    skillId: baseState.skillId,
    kind: 'multiple-choice',
    prompt: 'hard',
    explanation: 'hard explanation',
    difficulty: 5,
    tags: ['facts'],
    options: ['1', '2'],
    correctOption: 1,
  },
];

test('evaluates child responses without case or surrounding-space sensitivity', () => {
  assert.equal(evaluateActivity(activities[1], ' 12 '), true);
  assert.equal(evaluateActivity(activities[1], '13'), false);
});

test('correct evidence raises mastery while incorrect evidence lowers it', () => {
  const correct = updateAdaptiveMastery(baseState, attempt({ correct: true }));
  const incorrect = updateAdaptiveMastery(baseState, attempt({ correct: false }));

  assert.ok(correct.mastery > baseState.mastery);
  assert.ok(incorrect.mastery < baseState.mastery);
  assert.equal(correct.evidenceCount, 1);
  assert.equal(incorrect.evidenceCount, 1);
});

test('hints reduce the mastery gain for otherwise identical correct attempts', () => {
  const noHints = updateAdaptiveMastery(baseState, attempt({ hintsUsed: 0 }));
  const withHints = updateAdaptiveMastery(baseState, attempt({ hintsUsed: 3 }));

  assert.ok(noHints.mastery > withHints.mastery);
});

test('recommended difficulty increases as mastery becomes secure', () => {
  assert.equal(recommendedDifficulty({ ...baseState, mastery: 0.2 }), 1);
  assert.equal(recommendedDifficulty({ ...baseState, mastery: 0.55 }), 3);
  assert.equal(recommendedDifficulty({ ...baseState, mastery: 0.72 }), 4);
  assert.equal(recommendedDifficulty({
    ...baseState,
    mastery: 0.9,
    recentAttempts: [attempt(), attempt({ activityId: 'a2' }), attempt({ activityId: 'a3', correct: false })],
  }), 5);
});

test('adaptive selection chooses the closest unseen difficulty', () => {
  const state = { ...baseState, mastery: 0.55 };
  const next = selectNextActivity(activities, state, ['medium-related']);

  assert.equal(next?.id, 'easy-related');
});

test('relearning prioritizes a related unseen activity and steps difficulty down', () => {
  const failed = {
    ...activities[2],
    id: 'failed-hard',
    tags: ['groups', 'visual'],
    difficulty: 3 as const,
  };
  const next = selectRelearningActivity(activities, failed, []);

  assert.equal(next?.id, 'medium-related');
});

test('recent incorrect evidence keeps relearning active even above the mastery threshold', () => {
  const state: AdaptiveSessionState = {
    ...baseState,
    mastery: 0.8,
    recentAttempts: [attempt({ correct: false })],
  };

  assert.equal(needsRelearning(state), true);
});

test('review interval is short after an error and expands for mastered evidence', () => {
  const wrongAttempt = attempt({ correct: false });
  const masteredAttempt = attempt({ correct: true });
  const wrongReview = new Date(calculateNextReviewAt({ ...baseState, mastery: 0.35 }, wrongAttempt));
  const masteredReview = new Date(calculateNextReviewAt({ ...baseState, mastery: 0.95, evidenceCount: 9 }, masteredAttempt));
  const anchor = new Date(masteredAttempt.attemptedAt);

  assert.equal((wrongReview.getTime() - anchor.getTime()) / 3_600_000, 6);
  assert.equal((masteredReview.getTime() - anchor.getTime()) / 3_600_000, 24 * 21);
});
