'use client';

import { FormEvent, useState } from 'react';
import { useRouter } from 'next/navigation';
import { isFirebaseConfigured } from '../../lib/firebase';
import { signInWithEmail, signInWithGoogle, signUpWithEmail } from '../../lib/auth';
import { saveUserProfile } from '../../lib/learner-repository';

export default function LoginPage() {
  const router = useRouter();
  const [mode, setMode] = useState<'signin' | 'signup'>('signin');
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);

  async function finish(user: { uid: string; email: string | null; displayName: string | null }) {
    await saveUserProfile(user.uid, user.email, user.displayName);
    router.push('/onboarding');
  }

  async function google() {
    if (!isFirebaseConfigured) return setError('Firebase-configuratie ontbreekt nog.');
    setBusy(true); setError('');
    try {
      const credential = await signInWithGoogle();
      await finish(credential.user);
    } catch {
      setError('Inloggen met Google is niet gelukt. Probeer het opnieuw.');
    } finally { setBusy(false); }
  }

  async function emailSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault();
    if (!isFirebaseConfigured) return setError('Firebase-configuratie ontbreekt nog.');
    const form = new FormData(event.currentTarget);
    const email = String(form.get('email') ?? '');
    const password = String(form.get('password') ?? '');
    setBusy(true); setError('');
    try {
      const credential = mode === 'signup' ? await signUpWithEmail(email, password) : await signInWithEmail(email, password);
      await finish(credential.user);
    } catch {
      setError(mode === 'signup' ? 'Account aanmaken is niet gelukt.' : 'E-mailadres of wachtwoord klopt niet.');
    } finally { setBusy(false); }
  }

  return (
    <main className="flowPage">
      <section className="flowCard">
        <span className="eyebrow">OUDERACCOUNT</span>
        <h1>{mode === 'signin' ? 'Welkom terug' : 'Maak een ouderaccount'}</h1>
        <p>Het ouderaccount bewaart leerprofielen en voortgang veilig per gezin.</p>
        <button className="primaryButton" type="button" onClick={google} disabled={busy}>Ga verder met Google</button>
        <form className="flowForm" onSubmit={emailSubmit}>
          <label>E-mailadres<input name="email" type="email" autoComplete="email" required /></label>
          <label>Wachtwoord<input name="password" type="password" minLength={6} autoComplete={mode === 'signup' ? 'new-password' : 'current-password'} required /></label>
          {error && <p className="formError" role="alert">{error}</p>}
          <button className="primaryButton" type="submit" disabled={busy}>{mode === 'signin' ? 'Inloggen' : 'Account maken'} <span>→</span></button>
        </form>
        <button className="textButton" type="button" onClick={() => setMode(mode === 'signin' ? 'signup' : 'signin')}>{mode === 'signin' ? 'Nog geen account? Aanmelden' : 'Al een account? Inloggen'}</button>
      </section>
    </main>
  );
}
