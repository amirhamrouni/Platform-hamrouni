import Link from 'next/link';
import styles from './learn.module.css';

const subjects = [
  { icon:'🧠', tone:'blue', title:'Rekenen & wiskunde', text:'Getallen, bewerkingen, breuken, tijd, geld, meten en meetkunde.', chips:['Getallen','Tafels','Breuken'] },
  { icon:'📖', tone:'green', title:'Nederlands', text:'Lezen, begrijpend lezen, woordenschat, spelling, schrijven en taal.', chips:['Lezen','Spelling','Taal'] },
  { icon:'🌍', tone:'yellow', title:'Wereldoriëntatie', text:'Aardrijkskunde, geschiedenis, natuur en techniek in samenhang.', chips:['Aarde','Tijd','Natuur'] },
  { icon:'💬', tone:'purple', title:'Engels', text:'Luisteren, spreken, lezen en praktische woordenschat op niveau.', chips:['Words','Listening','Speaking'] },
  { icon:'🤝', tone:'orange', title:'Burgerschap', text:'Samenleven, democratie, mediawijsheid, rechten en verantwoordelijkheid.', chips:['Samen','Media','Keuzes'] },
  { icon:'💻', tone:'cyan', title:'Digitale geletterdheid', text:'Informatievaardigheden, computational thinking en veilig digitaal gedrag.', chips:['Code','Media','Veilig'] },
  { icon:'🎨', tone:'pink', title:'Kunst & cultuur', text:'Maken, kijken, luisteren en ontdekken met creatieve opdrachten.', chips:['Maken','Muziek','Kijken'] },
  { icon:'🗣️', tone:'indigo', title:'NT2 & taalsteun', text:'Extra uitleg voor kinderen die Nederlands leren, zonder de hoofdles te vervangen.', chips:['Nederlands','Thuistaal','Uitleg'] },
] as const;

export default function LearnPage(){
  return <main className={styles.page}>
    <div className={styles.shell}>
      <header className={styles.top}>
        <Link className={styles.brand} href="/dashboard"><span className={styles.logo}>LS</span><span>LeerSprong NL</span></Link>
        <Link className={styles.back} href="/dashboard">← Terug naar vandaag</Link>
      </header>

      <section className={styles.hero}>
        <div>
          <span className={styles.eyebrow}>JOUW LEERWERELD</span>
          <h1>Kies wat je vandaag wilt ontdekken.</h1>
          <p>LeerSprong combineert je niveau, eerdere fouten en geplande herhalingen om per vak de volgende geschikte stap te kiezen. Je hoeft dus niet zelf door honderden oefeningen te zoeken.</p>
        </div>
        <aside className={styles.heroBadge}>
          <span>🤖</span>
          <strong>Leermaatje kijkt mee</strong>
          <p>Bij een fout krijg je eerst gerichte feedback, daarna zo nodig een eenvoudigere tussenstap of een korte herhaling.</p>
        </aside>
      </section>

      <section className={styles.section}>
        <div className={styles.sectionHead}><div><span className={styles.eyebrow}>VAKKEN</span><h2>Alles op één plek</h2></div><p>Groep 1 t/m 8 · Nederlands curriculum als structuur</p></div>
        <div className={styles.grid}>
          {subjects.map(subject => <article className={styles.card} key={subject.title}>
            <span className={`${styles.icon} ${styles[subject.tone]}`}>{subject.icon}</span>
            <h3>{subject.title}</h3>
            <p>{subject.text}</p>
            <div className={styles.chips}>{subject.chips.map(chip => <span key={chip}>{chip}</span>)}</div>
          </article>)}
        </div>
      </section>

      <section className={styles.section}>
        <div className={styles.sectionHead}><div><span className={styles.eyebrow}>SLIMME ROUTES</span><h2>Niet meer oefenen dan nodig</h2></div></div>
        <div className={styles.focus}>
          <article><span>🔁</span><h3>Herhalen op het juiste moment</h3><p>Vaardigheden die aan herhaling toe zijn komen vooraan. Sterke onderdelen verdwijnen niet, maar krijgen meer ruimte tussen oefenmomenten.</p><Link className={styles.cta} href="/dashboard">Bekijk mijn dagplan →</Link></article>
          <article><span>🧩</span><h3>Fouten worden een leerroute</h3><p>Een fout is geen rood eindpunt. De lesson engine gebruikt het antwoord om een hint, tussenstap of gerichte vervolgactiviteit te kiezen.</p><Link className={styles.cta} href="/lesson/multiplication">Probeer een adaptieve les →</Link></article>
        </div>
      </section>
    </div>
  </main>;
}
