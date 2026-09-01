'use client';

import {
  GoogleAuthProvider,
  User,
  createUserWithEmailAndPassword,
  onAuthStateChanged,
  signInWithEmailAndPassword,
  signInWithPopup,
  signOut,
} from 'firebase/auth';
import { getFirebaseAuth } from './firebase';

export async function signInWithGoogle() {
  return signInWithPopup(getFirebaseAuth(), new GoogleAuthProvider());
}

export async function signUpWithEmail(email: string, password: string) {
  return createUserWithEmailAndPassword(getFirebaseAuth(), email, password);
}

export async function signInWithEmail(email: string, password: string) {
  return signInWithEmailAndPassword(getFirebaseAuth(), email, password);
}

export async function signOutCurrentUser() {
  return signOut(getFirebaseAuth());
}

export async function resolveCurrentUser(): Promise<User | null> {
  const auth = getFirebaseAuth();
  if (auth.currentUser) return auth.currentUser;

  return new Promise<User | null>((resolve, reject) => {
    const unsubscribe = onAuthStateChanged(
      auth,
      (user) => {
        unsubscribe();
        resolve(user);
      },
      (error) => {
        unsubscribe();
        reject(error);
      },
    );
  });
}
