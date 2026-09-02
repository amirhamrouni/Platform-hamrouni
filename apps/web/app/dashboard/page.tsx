'use client';

import { useEffect, useMemo, useState } from 'react';
import Link from 'next/link';
import type { SkillScore } from '../../lib/learner';
import { isFirebaseConfigured } from '../../lib/firebase';
import { resolveCurrentUser } from '../../lib/auth';
import { loadLearner, loadSkillScores, type PersistedLearner } from '../../lib/learner-repository';

type Learner = Pick<PersistedLearner, 'name' | 'group' | 'homeLanguage' | 'supportLanguageEnabled'>;

function scoreForToday(score: SkillScore, now: Date) {
  const reviewDue = score.nextReviewAt ? new Date(score.nextReviewAt) <= now : false;
  const masteryGap = 1 - Math.max(0, Math.min(100, score.mastery)) / 100;
  const uncertainty = 1 - Math.max(0, Math.min(100, score.evidenceConfidence ?? 0)) / 100;
  const priorityBoost = score.priority === 'high' ? 0.2 : score.priority === 'medium' ? 0.1 : 0;
  return masteryGap * 0.55 + uncertainty * 0.2 + priorityBoost + (reviewDue ? 0.35 : 0);
}

export default function DashboardPage() {
  const [learner, setLearner] = useState<Learner | null>(null);
  const [scores, setScores] = useState<SkillScore[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    let cancelled = false;

    async function hydrateDashboard() {
      const learnerId = sessionStorage.getItem('leersprong:learnerId');

      if (isFirebaseConfigured && learnerId) {
        try {
          const user = await resolveCurrentUser();
          if (user) {
            const [storedLearner, storedScores] = await Promise.all([
              loadLearner(user.uid, learnerId),
              loadSkillScores(user.uid, learnerId),
            ]);

            if (!cancelled && storedLearner) {
              setLearner(storedLearner);
              setScores(storedScores);
              setLoading(false);
              return;
            }
          }
        } catch {
          // Fall back to session state for the current browser session.
        }
      }

      const sessionLearner = sessionStorage.getItem('leersprong:learner');
      const sessionScores = sessionStorage.getItem('leersprong:assessment');
      if (!cancelled) {
        if (sessionLearner) setLearner(JSON.parse(sessionLearner));
        if (sessionScores) setScores(JSON.parse(sessionScores));
        setLoading(false);
      }
    }

    void hydrateDashboard();
    return () => { cancelled = true; };
  }, []);

  const todayPlan = useMemo(() => {
    const now = new Date();
    return [...scores]
      .sort((a, b) => scoreForToday(b, now) - scoreForToday(a, now))
      .slice(0, 3);
  }, [scores]);

  const reviewCount = useMemo(() => {
    const now = new Date();
    return scores.filter((score) => score.nextReviewAt && new Date(score.nextReviewAt) <= now).length;
  }, [scores]);

  if (loading) {
    return <main className="flowPage"><section className="flowCard"><p>Je leerpad wordt geladen…</p></section></main>;
  }

  if (!learner) {
    return <main className="flowPage"><section className="flowCard"><h1>Nog geen leerprofiel</h1><p>Maak eerst het profiel en de niveautest af.</p><Link className="primaryButton" href="/onboarding">Begin opnieuw →</Link></section></main>;
  }

  return (
    <main className="flowPage">
      <section className="flowCard dashboardCard">
        <span className="eyebrow">PERSOONLIJK LEERPAD</span>
        <h1>Hoi {learner.name}! 👋</h1>
        <p>Je startniveau voor groep {learner.group} is klaar. Vandaag oefenen we eerst wat jou de meeste vooruitgang geeft.</p>

        <article className="todayLesson">
          <div>
            <span className="eyebrow">VANDAAG VOOR JOU</span>
            <h2>✖️ Rekenen · Tafels begrijpen</h2>
            <p>{reviewCount > 0 ? `Je hebt ${reviewCount} herhaling${reviewCount === 1 ? '' : 'en'} klaarstaan. We beginnen met wat nu het meeste oplevert.` : 'Leer vermenigvuldigen met gelijke groepjes. De oefeningen passen zich aan jouw antwoorden aan.'}</p>
            <div className="lessonMeta"><span>Groep 4</span><span>± 12 min</span><span>Adaptief</span>{reviewCount > 0 && <span>🔁 {reviewCount} review</span>}</div>
          </div>
          <Link className="primaryButton" href="/lesson/multiplication">Start les <span>→</span></Link>
        </article>

        <div className="resultList">
          {todayPlan.map((score) => {
            const reviewDue = score.nextReviewAt ? new Date(score.nextReviewAt) <= new Date() : false;
            return (
              <article className="resultItem" key={score.skillId}>
                <strong>{score.skillId.replaceAll('-', ' ')}</strong>
                <strong className={`priority-${score.priority}`}>{score.mastery}%</strong>
                <small>{reviewDue ? '🔁 Herhaling is nu klaar' : score.priority === 'high' ? 'Nu oefenen' : score.priority === 'medium' ? 'Binnenkort herhalen' : 'Goed op weg'}{score.evidenceConfidence != null ? ` · zekerheid ${score.evidenceConfidence}%` : ''}</small>
              </article>
            );
          })}
        </div>
        {learner.supportLanguageEnabled && <p>🌍 Thuistaalhulp staat aan. Moeilijke uitleg kan ondersteund worden zonder het Nederlands te vervangen.</p>}
      </section>
    </main>
  );
}
