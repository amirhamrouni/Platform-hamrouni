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

function skillLabel(skillId: string) {
  return skillId.replaceAll('-', ' ').replace(/^g\d+\s+/, '');
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

  const now = useMemo(() => new Date(), []);
  const todayPlan = useMemo(() => [...scores]
    .sort((a, b) => scoreForToday(b, now) - scoreForToday(a, now))
    .slice(0, 4), [scores, now]);

  const reviewCount = useMemo(() => scores.filter((score) => score.nextReviewAt && new Date(score.nextReviewAt) <= now).length, [scores, now]);
  const averageMastery = useMemo(() => scores.length ? Math.round(scores.reduce((sum, score) => sum + score.mastery, 0) / scores.length) : 0, [scores]);
  const badgeCount = useMemo(() => scores.filter((score) => score.mastery >= 75 && (score.evidenceConfidence ?? 0) >= 50).length, [scores]);
  const focusSkill = todayPlan[0];

  if (loading) {
    return <main className="flowPage"><section className="flowCard"><p>Je leerpad wordt geladen…</p></section></main>;
  }

  if (!learner) {
    return <main className="flowPage"><section className="flowCard"><h1>Nog geen leerprofiel</h1><p>Maak eerst het profiel en de niveautest af.</p><Link className="primaryButton" href="/onboarding">Begin opnieuw →</Link></section></main>;
  }

  const tutorText = reviewCount > 0
    ? `Ik heb ${reviewCount} herhaling${reviewCount === 1 ? '' : 'en'} voor je klaargezet. We beginnen met ${focusSkill ? skillLabel(focusSkill.skillId) : 'je belangrijkste oefening'} en passen daarna de moeilijkheid aan.`
    : focusSkill
      ? `Vandaag focussen we op ${skillLabel(focusSkill.skillId)}. Ik kies telkens een volgende oefening op basis van je antwoorden.`
      : 'Maak eerst een paar oefeningen. Daarna bouw ik je persoonlijke dagplan op basis van echte leerdata.';

  return (
    <main className="learnerDashboard">
      <aside className="learnerSidebar">
        <Link className="learnerBrand" href="/dashboard"><span>LS</span><strong>LeerSprong</strong></Link>
        <nav>
          <Link className="active" href="/dashboard">🏠 <span>Vandaag</span></Link>
          <Link href="/lesson/multiplication">📘 <span>Leren</span></Link>
          <Link href="/assessment">🎯 <span>Niveautest</span></Link>
          <Link href="/onboarding">👤 <span>Profiel</span></Link>
        </nav>
        <div className="sidebarProfile"><span>{learner.name.slice(0, 1).toUpperCase()}</span><div><strong>{learner.name}</strong><small>Groep {learner.group}</small></div></div>
      </aside>

      <section className="learnerMain">
        <header className="dashboardWelcome">
          <div><span className="eyebrow">JOUW LEERDAG</span><h1>Hoi {learner.name}! 👋</h1><p>Je plan past zich aan jouw niveau, fouten en herhalingen aan.</p></div>
          <div className="dashboardStatus"><span>🔥 <strong>Start vandaag</strong><small>leerreeks</small></span><span>🏅 <strong>{badgeCount}</strong><small>behaald</small></span></div>
        </header>

        <div className="dashboardStatsGrid">
          <article><span className="statIcon purple">◎</span><div><small>Gemiddelde beheersing</small><strong>{averageMastery}%</strong></div><div className="miniMeter"><i style={{ width: `${averageMastery}%` }} /></div></article>
          <article><span className="statIcon orange">↻</span><div><small>Herhalingen vandaag</small><strong>{reviewCount}</strong></div><p>{reviewCount ? 'Klaar voor retrieval practice' : 'Alles bijgewerkt'}</p></article>
          <article><span className="statIcon green">✓</span><div><small>Sterke vaardigheden</small><strong>{badgeCount}</strong></div><p>Beheersing ≥75% met voldoende bewijs</p></article>
        </div>

        <div className="dashboardColumns">
          <section className="dashboardPanel dailyPlanPanel">
            <div className="panelHeading"><div><span className="eyebrow">DAGELIJKSE ROUTE</span><h2>Jouw plan voor vandaag</h2></div><span className="planCount">{todayPlan.length} stappen</span></div>
            <div className="dailyPlanList">
              {todayPlan.length ? todayPlan.map((score, index) => {
                const reviewDue = score.nextReviewAt ? new Date(score.nextReviewAt) <= now : false;
                return (
                  <article className={`dailyPlanItem ${index === 0 ? 'primaryPlanItem' : ''}`} key={score.skillId}>
                    <span className="planNumber">{index + 1}</span>
                    <div className="planBody"><strong>{skillLabel(score.skillId)}</strong><small>{reviewDue ? '🔁 Nu herhalen' : score.priority === 'high' ? 'Extra aandacht' : score.priority === 'medium' ? 'Verder oefenen' : 'Vaardigheid versterken'}</small><div className="skillMeter"><i style={{ width: `${Math.max(4, score.mastery)}%` }} /></div></div>
                    <strong className={`priority-${score.priority}`}>{score.mastery}%</strong>
                    {index === 0 && <Link className="planStart" href="/lesson/multiplication">Start →</Link>}
                  </article>
                );
              }) : <div className="emptyPlan"><strong>Nog geen leerdata</strong><p>Rond de niveautest af om je persoonlijke dagplan te maken.</p><Link href="/assessment">Start niveautest →</Link></div>}
            </div>
          </section>

          <aside className="dashboardRightRail">
            <article className="tutorCard">
              <div className="tutorHead"><span className="tutorAvatar">🤖</span><div><span>AI LEERMAATJE</span><strong>Ik leer met je mee</strong></div></div>
              <p>{tutorText}</p>
              <Link href="/lesson/multiplication">Samen oefenen →</Link>
            </article>
            <article className="badgeCard"><span className="eyebrow">MIJN VOORUITGANG</span><h3>Badges</h3><div className="badgeRow"><span className={badgeCount > 0 ? 'earned' : ''}>⭐</span><span className={badgeCount > 1 ? 'earned' : ''}>🚀</span><span className={badgeCount > 2 ? 'earned' : ''}>🏆</span></div><small>Badges worden alleen verdiend op basis van opgeslagen beheersing en voldoende bewijs.</small></article>
            {learner.supportLanguageEnabled && <article className="languageSupportCard"><strong>🌍 Thuistaalhulp actief</strong><p>Moeilijke uitleg kan ondersteund worden zonder het Nederlands te vervangen.</p></article>}
          </aside>
        </div>
      </section>
    </main>
  );
}
