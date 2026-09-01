'use client';

import { useEffect, useState } from 'react';
import Link from 'next/link';
import type { SkillScore } from '../../lib/learner';

type Learner = { name: string; group: number; homeLanguage: string; supportLanguageEnabled: boolean };

export default function DashboardPage() {
  const [learner, setLearner] = useState<Learner | null>(null);
  const [scores, setScores] = useState<SkillScore[]>([]);

  useEffect(() => {
    const storedLearner = sessionStorage.getItem('leersprong:learner');
    const storedScores = sessionStorage.getItem('leersprong:assessment');
    if (storedLearner) setLearner(JSON.parse(storedLearner));
    if (storedScores) setScores(JSON.parse(storedScores));
  }, []);

  const sorted = [...scores].sort((a, b) => a.mastery - b.mastery);
  const focus = sorted.slice(0, 3);

  if (!learner) {
    return <main className="flowPage"><section className="flowCard"><h1>Nog geen leerprofiel</h1><p>Maak eerst het profiel en de niveautest af.</p><Link className="primaryButton" href="/onboarding">Begin opnieuw →</Link></section></main>;
  }

  return (
    <main className="flowPage">
      <section className="flowCard">
        <span className="eyebrow">PERSOONLIJK LEERPAD</span>
        <h1>Goed gedaan, {learner.name}! 🎉</h1>
        <p>Je startniveau voor groep {learner.group} is klaar. LeerSprong kiest eerst de vaardigheden waar je de meeste winst kunt maken.</p>
        <div className="resultList">
          {focus.map((score) => (
            <article className="resultItem" key={score.skillId}>
              <strong>{score.skillId.replaceAll('-', ' ')}</strong>
              <strong className={`priority-${score.priority}`}>{score.mastery}%</strong>
              <small>Prioriteit: {score.priority === 'high' ? 'nu oefenen' : score.priority === 'medium' ? 'binnenkort herhalen' : 'goed op weg'}</small>
            </article>
          ))}
        </div>
        {learner.supportLanguageEnabled && <p>🌍 Thuistaalhulp staat aan. Moeilijke uitleg kan ondersteund worden zonder het Nederlands te vervangen.</p>}
        <Link className="primaryButton" href="/">Ga naar vandaag <span>→</span></Link>
      </section>
    </main>
  );
}
