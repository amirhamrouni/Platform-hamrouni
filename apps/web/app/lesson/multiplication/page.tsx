'use client';

import { useMemo, useState } from 'react';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import {
  AdaptiveSessionState,
  LearningActivity,
  evaluateActivity,
  masteryPercent,
  selectNextActivity,
  updateAdaptiveMastery,
} from '../../../lib/activity-engine';
import { g4MultiplicationLesson } from '../../../lib/lessons/g4-multiplication';
import { isFirebaseConfigured } from '../../../lib/firebase';
import { resolveCurrentUser } from '../../../lib/auth';
import { persistLessonAttempt } from '../../../lib/learner-repository';

const initialState: AdaptiveSessionState = {
  skillId: g4MultiplicationLesson.skillId,
  mastery: 0.28,
  evidenceCount: 0,
  recentAttempts: [],
};

export default function MultiplicationLessonPage() {
  const router = useRouter();
  const [session, setSession] = useState(initialState);
  const [seen, setSeen] = useState<string[]>([]);
  const [current, setCurrent] = useState<LearningActivity>(g4MultiplicationLesson.activities[0]);
  const [choice, setChoice] = useState<number | null>(null);
  const [textAnswer, setTextAnswer] = useState('');
  const [ordering, setOrdering] = useState<string[]>(current.kind === 'ordering' ? current.items : []);
  const [feedback, setFeedback] = useState<{ correct: boolean; explanation: string } | null>(null);
  const [startedAt, setStartedAt] = useState(() => Date.now());
  const [hintsUsed, setHintsUsed] = useState(0);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');

  const progress = useMemo(
    () => Math.min(100, Math.round((seen.length / Math.min(6, g4MultiplicationLesson.activities.length)) * 100)),
    [seen.length],
  );

  function responseValue() {
    if (current.kind === 'multiple-choice' || current.kind === 'listen-choose') return choice;
    if (current.kind === 'fill-blank') return textAnswer;
    return ordering;
  }

  function resetInputs(next: LearningActivity) {
    setChoice(null);
    setTextAnswer('');
    setOrdering(next.kind === 'ordering' ? [...next.items] : []);
    setFeedback(null);
    setHintsUsed(0);
    setStartedAt(Date.now());
  }

  async function submit() {
    if (saving || feedback) return;
    const value = responseValue();
    if ((current.kind === 'multiple-choice' || current.kind === 'listen-choose') && choice === null) return;
    if (current.kind === 'fill-blank' && textAnswer.trim() === '') return;

    const correct = evaluateActivity(current, value);
    const attempt = {
      activityId: current.id,
      skillId: current.skillId,
      correct,
      difficulty: current.difficulty,
      responseTimeMs: Date.now() - startedAt,
      hintsUsed,
      attemptedAt: new Date().toISOString(),
    };
    const nextSession = updateAdaptiveMastery(session, attempt);
    setSession(nextSession);
    setSeen((items) => [...items, current.id]);
    setFeedback({ correct, explanation: current.explanation });

    if (!isFirebaseConfigured) return;
    const learnerId = sessionStorage.getItem('leersprong:learnerId');
    if (!learnerId) return;

    try {
      setSaving(true);
      const user = await resolveCurrentUser();
      if (user) await persistLessonAttempt(user.uid, learnerId, g4MultiplicationLesson.id, attempt, nextSession);
    } catch {
      setError('Je antwoord is lokaal verwerkt, maar kon nog niet worden gesynchroniseerd.');
    } finally {
      setSaving(false);
    }
  }

  function continueLesson() {
    const next = selectNextActivity(g4MultiplicationLesson.activities, session, [...seen, current.id]);
    if (!next || seen.length + 1 >= 6) {
      router.push('/dashboard');
      return;
    }
    setCurrent(next);
    resetInputs(next);
  }

  function moveOrderingItem(index: number, direction: -1 | 1) {
    setOrdering((items) => {
      const target = index + direction;
      if (target < 0 || target >= items.length) return items;
      const copy = [...items];
      [copy[index], copy[target]] = [copy[target], copy[index]];
      return copy;
    });
  }

  return (
    <main className="flowPage lessonPage">
      <section className="flowCard lessonCard">
        <div className="assessmentTop">
          <div>
            <span className="eyebrow">GROEP 4 · REKENEN</span>
            <h2>{g4MultiplicationLesson.title}</h2>
          </div>
          <Link href="/dashboard">Stoppen</Link>
        </div>

        <div className="lessonStats">
          <span>Mastery <strong>{masteryPercent(session)}%</strong></span>
          <span>Bewijs <strong>{session.evidenceCount}</strong></span>
          <span>± {g4MultiplicationLesson.estimatedMinutes} min</span>
        </div>
        <div className="flowProgress"><span style={{ width: `${progress}%` }} /></div>

        <p className="lessonGoal">🎯 {g4MultiplicationLesson.goal}</p>
        <span className="difficultyPill">Niveau {current.difficulty}/5</span>
        <h1>{current.prompt}</h1>

        {(current.kind === 'multiple-choice' || current.kind === 'listen-choose') && (
          <div className="answerGrid">
            {current.options.map((option, index) => (
              <button type="button" className={choice === index ? 'answer selected' : 'answer'} key={option} onClick={() => setChoice(index)} disabled={Boolean(feedback)}>{option}</button>
            ))}
          </div>
        )}

        {current.kind === 'fill-blank' && (
          <div className="flowForm">
            <label>Jouw antwoord<input value={textAnswer} onChange={(event) => setTextAnswer(event.target.value)} inputMode="numeric" disabled={Boolean(feedback)} /></label>
          </div>
        )}

        {current.kind === 'ordering' && (
          <div className="orderingList">
            {ordering.map((item, index) => (
              <div className="orderingItem" key={item}>
                <strong>{index + 1}. {item}</strong>
                <div><button type="button" onClick={() => moveOrderingItem(index, -1)} disabled={index === 0 || Boolean(feedback)}>↑</button><button type="button" onClick={() => moveOrderingItem(index, 1)} disabled={index === ordering.length - 1 || Boolean(feedback)}>↓</button></div>
              </div>
            ))}
          </div>
        )}

        {!feedback && (
          <div className="lessonActions">
            <button type="button" className="hintButton" onClick={() => setHintsUsed((value) => value + 1)}>💡 Hint {hintsUsed > 0 ? `(${hintsUsed})` : ''}</button>
            <button type="button" className="primaryButton" onClick={submit} disabled={saving}>Controleer <span>→</span></button>
          </div>
        )}

        {feedback && (
          <div className={feedback.correct ? 'feedbackBox correctFeedback' : 'feedbackBox retryFeedback'}>
            <strong>{feedback.correct ? 'Goed gedaan! 🌟' : 'Bijna. Kijk naar de uitleg 👀'}</strong>
            <p>{feedback.explanation}</p>
            <button type="button" className="primaryButton" onClick={continueLesson}>{seen.length >= 5 ? 'Klaar' : 'Volgende oefening'} <span>→</span></button>
          </div>
        )}

        {error && <p className="formError" role="alert">{error}</p>}
      </section>
    </main>
  );
}
