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
  const clean = skillId.replace(/^g\d+-/, '').replaceAll('-', ' ');
  return clean.charAt(0).toUpperCase() + clean.slice(1);
}

function skillEmoji(skillId: string) {
  if (skillId.includes('math')) return '🧠';
  if (skillId.includes('reading')) return '📖';
  if (skillId.includes('spelling')) return '✍️';
  return '✨';
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
          // Session fallback keeps the learner flow usable when cloud state is unavailable.
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
  const todayPlan = useMemo(() => [...scores].sort((a, b) => scoreForToday(b, now) - scoreForToday(a, now)).slice(0, 4), [scores, now]);
  const reviewCount = useMemo(() => scores.filter((score) => score.nextReviewAt && new Date(score.nextReviewAt) <= now).length, [scores, now]);
  const averageMastery = useMemo(() => scores.length ? Math.round(scores.reduce((sum, score) => sum + score.mastery, 0) / scores.length) : 0, [scores]);
  const badgeCount = useMemo(() => scores.filter((score) => score.mastery >= 75 && (score.evidenceConfidence ?? 0) >= 50).length, [scores]);
  const focusSkill = todayPlan[0];

  if (loading) return <main className="flowPage"><section className="flowCard"><p>Je leerwereld wordt klaargezet…</p></section></main>;
  if (!learner) return <main className="flowPage"><section className="flowCard"><h1>Maak je leerprofiel</h1><p>Daarna maken we een route die bij jouw niveau past.</p><Link className="primaryButton" href="/onboarding">Begin →</Link></section></main>;

  const tutorText = reviewCount > 0
    ? `Ik heb ${reviewCount} slimme herhaling${reviewCount === 1 ? '' : 'en'} voor je klaargezet. We starten rustig en maken het moeilijker als het goed gaat.`
    : focusSkill
      ? `Vandaag bouwen we verder aan ${skillLabel(focusSkill.skillId)}. Ik pas de volgende oefening aan op wat jij laat zien.`
      : 'Doe eerst je korte niveautest. Daarna maak ik jouw persoonlijke leerroute.';

  return (
    <main className="learningHome">
      <aside className="kidRail">
        <Link className="kidBrand" href="/dashboard"><span>LS</span><strong>LeerSprong</strong></Link>
        <nav>
          <Link className="active" href="/dashboard">🏡 <span>Vandaag</span></Link>
          <Link href="/learn">🗺️ <span>Leerwereld</span></Link>
          <Link href="/assessment">🎯 <span>Niveau</span></Link>
          <Link href="/onboarding">🙂 <span>Ik</span></Link>
        </nav>
        <div className="kidMiniProfile"><span>{learner.name.slice(0,1).toUpperCase()}</span><div><strong>{learner.name}</strong><small>Groep {learner.group}</small></div></div>
      </aside>

      <section className="learningCanvas">
        <header className="humanHeader">
          <div><span className="softEyebrow">GOEDEMIDDAG</span><h1>Hoi {learner.name}, klaar voor een kleine sprong?</h1><p>Geen lange lijst. Eerst één goede stap, daarna kijken we samen verder.</p></div>
          <div className="humanCounters"><span>🔥 <strong>Vandaag starten</strong></span><span>🏅 <strong>{badgeCount}</strong> badges</span></div>
        </header>

        <section className="continueCard">
          <div className="continueCopy">
            <span className="continueTag">JOUW VOLGENDE STAP</span>
            <h2>{focusSkill ? skillLabel(focusSkill.skillId) : 'Ontdek jouw startniveau'}</h2>
            <p>{focusSkill ? (reviewCount ? 'Een korte herhaling op precies het juiste moment.' : 'Een korte les die zich aanpast aan jouw antwoorden.') : 'Een korte test helpt ons kiezen waar jij het beste kunt beginnen.'}</p>
            <div className="continueMeta"><span>⏱ 10–12 min</span><span>🎯 Adaptief</span><span>📚 Groep {learner.group}</span></div>
            <Link href={focusSkill ? '/lesson/multiplication' : '/assessment'}>{focusSkill ? 'Ga verder' : 'Start niveautest'} <span>→</span></Link>
          </div>
          <div className="mascotScene" aria-hidden="true"><div className="speechBubble">{reviewCount ? 'Ik heb je beste herhaling al gekozen!' : 'Eén stap tegelijk. Ik help je.'}</div><div className="mascotFace">🤖</div><div className="sceneDots"><i/><i/><i/></div></div>
        </section>

        <div className="learningGrid">
          <section className="journeyCard">
            <div className="sectionTitle"><div><span className="softEyebrow">MIJN LEERROUTE</span><h2>Vandaag</h2></div><small>{todayPlan.length} slimme stappen</small></div>
            <div className="journeyPath">
              {todayPlan.length ? todayPlan.map((score, index) => {
                const due = score.nextReviewAt ? new Date(score.nextReviewAt) <= now : false;
                return <article className={`journeyStep ${index === 0 ? 'current' : ''}`} key={score.skillId}>
                  <div className="journeyNode"><span>{skillEmoji(score.skillId)}</span></div>
                  <div className="journeyContent"><strong>{skillLabel(score.skillId)}</strong><small>{due ? 'Slim herhalen' : score.priority === 'high' ? 'Extra aandacht' : 'Verder bouwen'}</small><div className="journeyMeter"><i style={{width:`${Math.max(6, score.mastery)}%`}}/></div></div>
                  <span className="journeyScore">{score.mastery}%</span>
                  {index === 0 && <Link href="/lesson/multiplication">Start</Link>}
                </article>;
              }) : <div className="emptyJourney"><span>🗺️</span><strong>Je route is nog leeg</strong><p>Na de niveautest verschijnt hier jouw persoonlijke leerpad.</p><Link href="/assessment">Maak mijn route →</Link></div>}
            </div>
          </section>

          <aside className="humanSide">
            <article className="buddyCard"><div className="buddyTop"><span>🤖</span><div><small>AI LEERMAATJE</small><strong>Samen leren</strong></div></div><p>{tutorText}</p><Link href={focusSkill ? '/lesson/multiplication' : '/assessment'}>Kom, we doen er één →</Link></article>
            <article className="calmProgress"><div><span>🌱</span><div><small>Jouw groei</small><strong>{averageMastery}% beheersing</strong></div></div><div className="calmMeter"><i style={{width:`${averageMastery}%`}}/></div><p>{reviewCount ? `${reviewCount} herhaling${reviewCount === 1 ? '' : 'en'} staat klaar.` : 'Je bent bij met je herhalingen.'}</p></article>
            <article className="badgeShelf"><div className="sectionTitle"><div><span className="softEyebrow">TROTS OP</span><h3>Mijn badges</h3></div></div><div><span className={badgeCount > 0 ? 'earned' : ''}>⭐</span><span className={badgeCount > 1 ? 'earned' : ''}>🌟</span><span className={badgeCount > 2 ? 'earned' : ''}>🏆</span></div></article>
            <article className="homeLanguageHint"><strong>🧭 Ontdek je leerwereld</strong><p>Kies een vak of bekijk waar je verder kunt bouwen.</p><Link href="/learn">Open alle vakken →</Link></article>
            {learner.supportLanguageEnabled && <article className="homeLanguageHint"><strong>🌍 Thuistaalhulp staat aan</strong><p>Bij moeilijke uitleg kan je extra taalsteun krijgen, terwijl Nederlands centraal blijft.</p></article>}
          </aside>
        </div>
      </section>
    </main>
  );
}
