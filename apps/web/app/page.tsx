import Link from 'next/link';

const subjects = [
  { icon: '📚', title: 'Nederlands', detail: 'Lezen · Spelling · Woordenschat', progress: 72 },
  { icon: '➗', title: 'Rekenen', detail: 'Getallen · Tafels · Meten', progress: 58 },
  { icon: '🌍', title: 'Wereldoriëntatie', detail: 'Natuur · Geschiedenis · Aardrijkskunde', progress: 41 },
  { icon: '🗣️', title: 'NT2', detail: 'Nederlands stap voor stap', progress: 64 },
];

export default function HomePage() {
  return (
    <main>
      <header className="topbar">
        <div className="brand">
          <span className="brandMark">L</span>
          <div>
            <strong>LeerSprong NL</strong>
            <small>Groei elke dag een beetje</small>
          </div>
        </div>
        <Link className="profileButton" aria-label="Nieuw leerprofiel" href="/onboarding">+</Link>
      </header>

      <section className="hero shell">
        <div>
          <span className="eyebrow">NEDERLANDS BASISONDERWIJS · GROEP 1–8</span>
          <h1>Een slim leerpad<br /><em>voor ieder kind.</em></h1>
          <p>Start met een korte niveautest. Daarna kiest LeerSprong elke dag de oefeningen die het meeste verschil maken.</p>
          <Link className="primaryButton" href="/onboarding">Maak mijn leerpad <span>→</span></Link>
        </div>
        <div className="mascotCard" aria-label="AI leermaatje">
          <div className="bot">🤖</div>
          <span>AI Leermaatje</span>
          <strong>“Ik pas uitleg en oefeningen aan jouw niveau aan.”</strong>
        </div>
      </section>

      <section className="shell stats" aria-label="Platform pijlers">
        <article><strong>🇳🇱 1–8</strong><span>alle basisschoolgroepen</span></article>
        <article><strong>🧠 Adaptief</strong><span>oefenen op zwakke skills</span></article>
        <article><strong>🌍 NT2</strong><span>thuistaal als steun</span></article>
      </section>

      <section className="shell sectionHeader">
        <div><span className="eyebrow">LEERDOMEINEN</span><h2>Gebouwd rond echte vaardigheden</h2></div>
      </section>

      <section className="shell subjectGrid">
        {subjects.map((subject) => (
          <article className="subjectCard" key={subject.title}>
            <span className="subjectIcon">{subject.icon}</span>
            <div className="subjectBody"><h3>{subject.title}</h3><p>{subject.detail}</p><div className="meter"><span style={{ width: `${subject.progress}%` }} /></div></div>
            <strong>{subject.progress}%</strong>
          </article>
        ))}
      </section>
    </main>
  );
}
