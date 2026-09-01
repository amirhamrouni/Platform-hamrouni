import { z } from 'zod';

export const learnerProfileSchema = z.object({
  name: z.string().trim().min(2).max(40),
  group: z.coerce.number().int().min(1).max(8),
  homeLanguage: z.enum(['nl', 'ar', 'tr', 'pl', 'uk', 'en', 'fr', 'other']),
  supportLanguageEnabled: z.boolean().default(false),
});

export type LearnerProfileInput = z.infer<typeof learnerProfileSchema>;

export type AssessmentAnswer = {
  skillId: string;
  correct: boolean;
  confidence: 1 | 2 | 3;
};

export type SkillScore = {
  skillId: string;
  mastery: number;
  priority: 'high' | 'medium' | 'low';
};

export function scoreAssessment(answers: AssessmentAnswer[]): SkillScore[] {
  const grouped = new Map<string, AssessmentAnswer[]>();
  for (const answer of answers) {
    const list = grouped.get(answer.skillId) ?? [];
    list.push(answer);
    grouped.set(answer.skillId, list);
  }

  return [...grouped.entries()].map(([skillId, items]) => {
    const raw = items.reduce((sum, item) => {
      const correctness = item.correct ? 1 : 0;
      const confidenceAdjustment = item.correct ? (item.confidence - 1) * 0.05 : -(item.confidence - 1) * 0.05;
      return sum + Math.max(0, Math.min(1, correctness + confidenceAdjustment));
    }, 0) / items.length;

    const mastery = Math.round(raw * 100);
    return {
      skillId,
      mastery,
      priority: mastery < 50 ? 'high' : mastery < 75 ? 'medium' : 'low',
    };
  });
}
