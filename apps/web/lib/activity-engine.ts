export type ActivityKind = 'multiple-choice' | 'fill-blank' | 'ordering' | 'listen-choose';

export type Difficulty = 1 | 2 | 3 | 4 | 5;

export interface BaseActivity {
  id: string;
  skillId: string;
  kind: ActivityKind;
  prompt: string;
  explanation: string;
  difficulty: Difficulty;
  tags: string[];
}

export interface MultipleChoiceActivity extends BaseActivity {
  kind: 'multiple-choice' | 'listen-choose';
  options: string[];
  correctOption: number;
  audioText?: string;
}

export interface FillBlankActivity extends BaseActivity {
  kind: 'fill-blank';
  acceptedAnswers: string[];
}

export interface OrderingActivity extends BaseActivity {
  kind: 'ordering';
  items: string[];
  correctOrder: string[];
}

export type LearningActivity = MultipleChoiceActivity | FillBlankActivity | OrderingActivity;

export interface ActivityAttempt {
  activityId: string;
  skillId: string;
  correct: boolean;
  difficulty: Difficulty;
  responseTimeMs: number;
  hintsUsed: number;
  attemptedAt: string;
}

export interface AdaptiveSessionState {
  skillId: string;
  mastery: number;
  evidenceCount: number;
  recentAttempts: ActivityAttempt[];
}

function clamp(value: number, min = 0, max = 1) {
  return Math.max(min, Math.min(max, value));
}

export function evaluateActivity(activity: LearningActivity, response: unknown): boolean {
  if (activity.kind === 'multiple-choice' || activity.kind === 'listen-choose') {
    return typeof response === 'number' && response === activity.correctOption;
  }

  if (activity.kind === 'fill-blank') {
    const normalized = String(response ?? '').trim().toLocaleLowerCase('nl-NL');
    return activity.acceptedAnswers.some((answer) => answer.trim().toLocaleLowerCase('nl-NL') === normalized);
  }

  if (activity.kind !== 'ordering' || !Array.isArray(response)) return false;
  return response.length === activity.correctOrder.length
    && response.every((item, index) => item === activity.correctOrder[index]);
}

export function updateAdaptiveMastery(state: AdaptiveSessionState, attempt: ActivityAttempt): AdaptiveSessionState {
  const difficultyWeight = 0.7 + attempt.difficulty * 0.08;
  const hintPenalty = Math.min(attempt.hintsUsed * 0.025, 0.1);
  const speedAdjustment = attempt.responseTimeMs < 12_000 ? 0.015 : attempt.responseTimeMs > 60_000 ? -0.015 : 0;
  const baseDelta = attempt.correct ? 0.085 * difficultyWeight : -0.07 * difficultyWeight;
  const mastery = clamp(state.mastery + baseDelta + speedAdjustment - hintPenalty);

  return {
    ...state,
    mastery,
    evidenceCount: state.evidenceCount + 1,
    recentAttempts: [...state.recentAttempts.slice(-5), attempt],
  };
}

export function recommendedDifficulty(state: AdaptiveSessionState): Difficulty {
  const recent = state.recentAttempts.slice(-3);
  const recentAccuracy = recent.length === 0 ? 0.5 : recent.filter((attempt) => attempt.correct).length / recent.length;

  if (state.mastery >= 0.82 && recentAccuracy >= 0.67) return 5;
  if (state.mastery >= 0.68) return 4;
  if (state.mastery >= 0.48) return 3;
  if (state.mastery >= 0.25) return 2;
  return 1;
}

export function selectNextActivity(
  activities: LearningActivity[],
  state: AdaptiveSessionState,
  seenActivityIds: string[],
): LearningActivity | null {
  const target = recommendedDifficulty(state);
  const unseen = activities.filter((activity) => !seenActivityIds.includes(activity.id));
  if (unseen.length === 0) return null;

  return unseen
    .sort((a, b) => Math.abs(a.difficulty - target) - Math.abs(b.difficulty - target))[0] ?? null;
}

export function masteryPercent(state: AdaptiveSessionState) {
  return Math.round(clamp(state.mastery) * 100);
}
