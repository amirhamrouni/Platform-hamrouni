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
        <button className="profileButton" aria-label="Profiel">A</button>
      </header>

      <section className="hero shell">
        <div>
          <span className="eyebrow">GOEDEMORGEN 👋</span>
          <h1>Klaar voor jouw<br /><em>leersprong?</em></h1>
          <p>Je persoonlijke leerpad staat klaar. Vandaag oefenen we slim verder op wat jij nodig hebt.</p>
          <button className="primaryButton">Start mijn dag <span>→</span></button>
        </div>
        <div className="mascotCard" aria-label="AI leermaatje">
          <div className="bot">🤖</div>
          <span>AI Leermaatje</span>
          <strong>“We pakken vandaag de tafel van 7 aan!”</strong>
        </div>
      </section>

      <section className="shell stats" aria-label="Dagelijkse voortgang">
        <article><strong>🔥 6</strong><span>dagen reeks</span></article>
        <article><strong>⭐ 1.240</strong><span>XP verdiend</span></article>
        <article><strong>🎯 3/5</strong><span>doelen vandaag</span></article>
      </section>

      <section className="shell sectionHeader">
        <div>
          <span className="eyebrow">JOUW LEERPAD</span>
          <h2>Vandaag voor jou</h2>
        </div>
        <button className="textButton">Alles bekijken →</button>
      </section>

      <section className="shell recommendationGrid">
        <article className="focusCard">
          <span className="pill danger">Extra oefenen</span>
          <div className="focusIcon">✕</div>
          <h3>Tafel van 7</h3>
          <p>Je vond deze gisteren nog lastig. Met 8 minuten oefenen kan je al een stap vooruit.</p>
          <div className="meter"><span style={{ width: '39%' }} /></div>
          <footer><span>39% beheerst</span><button>Oefenen →</button></footer>
        </article>
        <article className="focusCard secondary">
          <span className="pill">Herhalen</span>
          <div className="focusIcon">Aa</div>
          <h3>Hoofdgedachte</h3>
          <p>Lees een korte tekst en ontdek waar die vooral over gaat.</p>
          <div className="meter"><span style={{ width: '74%' }} /></div>
          <footer><span>74% beheerst</span><button>Start →</button></footer>
        </article>
      </section>

      <section className="shell sectionHeader compact">
        <div>
          <span className="eyebrow">VAKKEN</span>
          <h2>Ontdek & leer</h2>
        </div>
      </section>

      <section className="shell subjectGrid">
        {subjects.map((subject) => (
          <article className="subjectCard" key={subject.title}>
            <span className="subjectIcon">{subject.icon}</span>
            <div className="subjectBody">
              <h3>{subject.title}</h3>
              <p>{subject.detail}</p>
              <div className="meter"><span style={{ width: `${subject.progress}%` }} /></div>
            </div>
            <strong>{subject.progress}%</strong>
          </article>
        ))}
      </section>

      <nav className="bottomNav" aria-label="Hoofdnavigatie">
        <a className="active" href="#">⌂<span>Vandaag</span></a>
        <a href="#">▦<span>Leren</span></a>
        <a className="navAction" href="#">▶</a>
        <a href="#">★<span>Beloningen</span></a>
        <a href="#">☺<span>Profiel</span></a>
      </nav>
    </main>
  );
}
