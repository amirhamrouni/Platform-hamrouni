'use client';

import { useMemo, useState } from 'react';
import { useRouter } from 'next/navigation';
import { AssessmentAnswer, scoreAssessment } from '../../lib/learner';
import { getFirebaseAuth, isFirebaseConfigured } from '../../lib/firebase';
import { persistAssessment } from '../../lib/learner-repository';

const questions = [
  { skillId: 'nl-reading-main-idea', subject: 'Nederlands', prompt: 'Mila neemt een paraplu mee, want buiten vallen donkere druppels. Waarom neemt Mila een paraplu mee?', options: ['Omdat het regent', 'Omdat het warm is', 'Omdat ze gaat zwemmen'], answer: 0 },
  { skillId: 'nl-spelling-open-closed', subject: 'Nederlands', prompt: 'Welk woord is goed geschreven?', options: ['lopen', 'loopen', 'loppenn'], answer: 0 },
  { skillId: 'math-number-sense', subject: 'Rekenen', prompt: 'Welk getal ligt precies tussen 40 en 50?', options: ['44', '45', '46'], answer: 1 },
  { skillId: 'math-table-7', subject: 'Rekenen', prompt: 'Wat is 7 × 6?', options: ['36', '42', '48'], answer: 1 },
  { skillId: 'math-time', subject: 'Rekenen', prompt: 'Het is 14:30. Hoe laat is het over 45 minuten?', options: ['15:05', '15:15', '15:25'], answer: 1 },
  { skillId: 'world-orientation-basic', subject: 'Wereldoriëntatie', prompt: 'Welke hoort bij de vier seizoenen?', options: ['Herfst', 'Noord', 'Liter'], answer: 0 },
];

export default function AssessmentPage() {
  const router = useRouter();
  const [index, setIndex] = useState(0);
  const [answers, setAnswers] = useState<AssessmentAnswer[]>([]);
  const [selected, setSelected] = useState<number | null>(null);
  const [confidence, setConfidence] = useState<1 | 2 | 3>(2);
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState('');
  const current = questions[index];
  const progress = useMemo(() => Math.round((index / questions.length) * 100), [index]);

  async function next() {
    if (selected === null || busy) return;
    const updated: AssessmentAnswer[] = [...answers, { skillId: current.skillId, correct: selected === current.answer, confidence }];
    if (index === questions.length - 1) {
      if (!isFirebaseConfigured) return setError('Firebase-configuratie ontbreekt nog.');
      const user = getFirebaseAuth().currentUser;
      const learnerId = sessionStorage.getItem('leersprong:learnerId');
      if (!user) return router.push('/login');
      if (!learnerId) return router.push('/onboarding');
      const result = scoreAssessment(updated);
      setBusy(true); setError('');
      try {
        await persistAssessment(user.uid, learnerId, result);
        sessionStorage.setItem('leersprong:assessment', JSON.stringify(result));
        router.push('/dashboard');
      } catch {
        setError('De niveautest kon niet worden opgeslagen. Probeer opnieuw.');
        setBusy(false);
      }
      return;
    }
    setAnswers(updated);
    setIndex((value) => value + 1);
    setSelected(null);
    setConfidence(2);
  }

  return (
    <main className="flowPage">
      <section className="flowCard assessmentCard">
        <div className="assessmentTop"><span className="eyebrow">NIVEAUTEST · {current.subject.toUpperCase()}</span><strong>{index + 1}/{questions.length}</strong></div>
        <div className="flowProgress"><span style={{ width: `${progress}%` }} /></div>
        <h1>{current.prompt}</h1>
        <div className="answerGrid">
          {current.options.map((option, optionIndex) => <button type="button" className={selected === optionIndex ? 'answer selected' : 'answer'} key={option} onClick={() => setSelected(optionIndex)}>{option}</button>)}
        </div>
        <div className="confidence">
          <span>Hoe zeker ben je?</span>
          {[1, 2, 3].map((value) => <button type="button" key={value} className={confidence === value ? 'selected' : ''} onClick={() => setConfidence(value as 1 | 2 | 3)}>{value === 1 ? '🤔' : value === 2 ? '🙂' : '💪'}</button>)}
        </div>
        {error && <p className="formError" role="alert">{error}</p>}
        <button className="primaryButton" type="button" disabled={selected === null || busy} onClick={next}>{index === questions.length - 1 ? 'Maak mijn leerpad' : 'Volgende'} <span>→</span></button>
      </section>
    </main>
  );
}
