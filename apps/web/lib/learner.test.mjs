import assert from 'node:assert/strict';
import test from 'node:test';

import { scoreAssessment } from './learner.ts';

test('uses multiple evidence items per skill and reports evidence count', () => {
  const [score] = scoreAssessment([
    { skillId: 'math-table-7', correct: true, confidence: 2, difficulty: 1 },
    { skillId: 'math-table-7', correct: false, confidence: 2, difficulty: 3 },
  ]);

  assert.equal(score.skillId, 'math-table-7');
  assert.equal(score.evidenceCount, 2);
  assert.ok(score.mastery > 25 && score.mastery < 75);
});

test('does not convert one correct answer into 100 percent mastery', () => {
  const [score] = scoreAssessment([
    { skillId: 'nl-reading-main-idea', correct: true, confidence: 3, difficulty: 2 },
  ]);

  assert.ok(score.mastery < 100);
  assert.equal(score.evidenceCount, 1);
});

test('hard correct evidence is worth more than easy correct evidence', () => {
  const [easy] = scoreAssessment([
    { skillId: 'math-number-sense', correct: true, confidence: 2, difficulty: 1 },
  ]);
  const [hard] = scoreAssessment([
    { skillId: 'math-number-sense', correct: true, confidence: 2, difficulty: 3 },
  ]);

  assert.ok(hard.mastery > easy.mastery);
});

test('mastery priority remains bounded and actionable', () => {
  const [score] = scoreAssessment([
    { skillId: 'math-time', correct: false, confidence: 3, difficulty: 3 },
    { skillId: 'math-time', correct: false, confidence: 2, difficulty: 2 },
  ]);

  assert.ok(score.mastery >= 0 && score.mastery <= 100);
  assert.equal(score.priority, 'high');
});
