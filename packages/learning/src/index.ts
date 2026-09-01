export type MasteryBand = 'new' | 'learning' | 'developing' | 'secure' | 'mastered';

export interface SkillEvidence {
  skillId: string;
  correct: boolean;
  responseTimeMs?: number;
  hintsUsed?: number;
  attemptAt: string;
}

export interface LearnerSkillState {
  skillId: string;
  mastery: number;
  confidence: number;
  attempts: number;
  correctAttempts: number;
  lastPractisedAt?: string;
  nextReviewAt?: string;
}

export interface DailyPathItem {
  skillId: string;
  reason: 'diagnostic-gap' | 'scheduled-review' | 'current-goal' | 'stretch';
  priority: number;
}

const clamp01 = (value: number) => Math.max(0, Math.min(1, value));

export function updateSkillState(
  previous: LearnerSkillState,
  evidence: SkillEvidence,
): LearnerSkillState {
  const accuracyDelta = evidence.correct ? 0.09 : -0.07;
  const hintPenalty = Math.min((evidence.hintsUsed ?? 0) * 0.015, 0.06);
  const speedSignal =
    evidence.responseTimeMs == null
      ? 0
      : evidence.responseTimeMs < 12_000
        ? 0.01
        : evidence.responseTimeMs > 45_000
          ? -0.01
          : 0;

  const mastery = clamp01(previous.mastery + accuracyDelta + speedSignal - hintPenalty);
  const attempts = previous.attempts + 1;
  const correctAttempts = previous.correctAttempts + (evidence.correct ? 1 : 0);
  const confidence = clamp01(1 - Math.exp(-attempts / 6));

  return {
    ...previous,
    mastery,
    confidence,
    attempts,
    correctAttempts,
    lastPractisedAt: evidence.attemptAt,
  };
}

export function masteryBand(mastery: number): MasteryBand {
  if (mastery >= 0.9) return 'mastered';
  if (mastery >= 0.75) return 'secure';
  if (mastery >= 0.5) return 'developing';
  if (mastery > 0) return 'learning';
  return 'new';
}

export function buildDailyPath(
  states: LearnerSkillState[],
  now = new Date(),
  limit = 8,
): DailyPathItem[] {
  return states
    .map((state): DailyPathItem => {
      const reviewDue = state.nextReviewAt ? new Date(state.nextReviewAt) <= now : false;
      const gap = 1 - state.mastery;
      const uncertainty = 1 - state.confidence;
      const priority = gap * 0.65 + uncertainty * 0.2 + (reviewDue ? 0.15 : 0);

      return {
        skillId: state.skillId,
        reason: reviewDue ? 'scheduled-review' : state.mastery < 0.5 ? 'diagnostic-gap' : 'current-goal',
        priority,
      };
    })
    .sort((a, b) => b.priority - a.priority)
    .slice(0, limit);
}
