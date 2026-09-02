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
  difficulty?: 1 | 2 | 3;
};

export type SkillScore = {
  skillId: string;
  mastery: number;
  priority: 'high' | 'medium' | 'low';
  evidenceCount?: number;
};

export function scoreAssessment(answers: AssessmentAnswer[]): SkillScore[] {
  const grouped = new Map<string, AssessmentAnswer[]>();
  for (const answer of answers) {
    const list = grouped.get(answer.skillId) ?? [];
    list.push(answer);
    grouped.set(answer.skillId, list);
  }

  return [...grouped.entries()].map(([skillId, items]) => {
    const priorWeight = 1;
    const priorMastery = 0.5;
    let weightedEvidence = 0;
    let totalWeight = 0;

    for (const item of items) {
      const difficultyWeight = item.difficulty === 3 ? 1.15 : item.difficulty === 1 ? 0.9 : 1;
      const confidenceWeight = item.confidence === 3 ? 1.08 : item.confidence === 1 ? 0.94 : 1;
      const weight = difficultyWeight * confidenceWeight;
      weightedEvidence += (item.correct ? 1 : 0) * weight;
      totalWeight += weight;
    }

    const posterior = (priorMastery * priorWeight + weightedEvidence) / (priorWeight + totalWeight);
    const mastery = Math.round(Math.max(0, Math.min(1, posterior)) * 100);

    return {
      skillId,
      mastery,
      priority: mastery < 50 ? 'high' : mastery < 75 ? 'medium' : 'low',
      evidenceCount: items.length,
    };
  });
}
