import type { Metadata } from 'next';
import './globals.css';

export const metadata: Metadata = {
  title: 'LeerSprong NL',
  description: 'Slim leren voor kinderen in groep 1 t/m 8.',
};

export default function RootLayout({ children }: Readonly<{ children: React.ReactNode }>) {
  return (
    <html lang="nl">
      <body>{children}</body>
    </html>
  );
}
