'use client';

import { FormEvent, useState } from 'react';
import { useRouter } from 'next/navigation';
import { learnerProfileSchema } from '../../lib/learner';
import { getFirebaseAuth, isFirebaseConfigured } from '../../lib/firebase';
import { createLearner } from '../../lib/learner-repository';

const languages = [
  ['nl', 'Nederlands'], ['ar', 'Arabisch'], ['tr', 'Turks'], ['pl', 'Pools'],
  ['uk', 'Oekraïens'], ['en', 'Engels'], ['fr', 'Frans'], ['other', 'Anders'],
] as const;

export default function OnboardingPage() {
  const router = useRouter();
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);

  async function submit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!isFirebaseConfigured) {
      setError('Firebase-configuratie ontbreekt nog.');
      return;
    }
    const user = getFirebaseAuth().currentUser;
    if (!user) {
      router.push('/login');
      return;
    }

    const form = new FormData(event.currentTarget);
    const result = learnerProfileSchema.safeParse({
      name: form.get('name'),
      group: form.get('group'),
      homeLanguage: form.get('homeLanguage'),
      supportLanguageEnabled: form.get('supportLanguageEnabled') === 'on',
    });

    if (!result.success) {
      setError('Controleer de naam, groep en thuistaal.');
      return;
    }

    setBusy(true); setError('');
    try {
      const learnerId = await createLearner(user.uid, result.data);
      sessionStorage.setItem('leersprong:learner', JSON.stringify(result.data));
      sessionStorage.setItem('leersprong:learnerId', learnerId);
      router.push('/assessment');
    } catch {
      setError('Het leerprofiel kon niet worden opgeslagen. Probeer opnieuw.');
    } finally { setBusy(false); }
  }

  return (
    <main className="flowPage">
      <section className="flowCard">
        <span className="eyebrow">STAP 1 VAN 2</span>
        <h1>Wie gaat er leren?</h1>
        <p>We maken een leerpad dat past bij de groep, het niveau en de taalachtergrond van het kind.</p>
        <form className="flowForm" onSubmit={submit}>
          <label>Naam van het kind<input name="name" placeholder="Bijv. Adam" autoComplete="given-name" required /></label>
          <label>Groep<select name="group" defaultValue="4">{Array.from({ length: 8 }, (_, i) => <option value={i + 1} key={i + 1}>Groep {i + 1}</option>)}</select></label>
          <label>Thuistaal<select name="homeLanguage" defaultValue="nl">{languages.map(([value, label]) => <option value={value} key={value}>{label}</option>)}</select></label>
          <label className="checkRow"><input type="checkbox" name="supportLanguageEnabled" />Leg moeilijke uitleg ook uit in de thuistaal</label>
          {error && <p className="formError" role="alert">{error}</p>}
          <button className="primaryButton" type="submit" disabled={busy}>Start niveautest <span>→</span></button>
        </form>
      </section>
    </main>
  );
}
