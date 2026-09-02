# Engagement open-source references

LeerSprong NL uses original implementation code in this repository. The projects below were reviewed for interaction and architecture patterns. Their code/assets are not copied wholesale.

## Sources used for the Engagement Pack

- **EzekielWachira/Quizzo** — MIT License — Jetpack Compose quiz-game presentation patterns, compact question cards, score/progress hierarchy.
  - https://github.com/EzekielWachira/Quizzo
- **Yugyd/quiz-platform** — Apache-2.0 — Compose quiz architecture, separation of question state from presentation, optional Firebase integration pattern.
  - https://github.com/Yugyd/quiz-platform
- **julianegner/coshanu** — MIT License — card matching / short-session game interaction patterns.
  - https://github.com/julianegner/coshanu
- **ndenicolais/SpeechAndText** — MIT License — Android/Compose Text-to-Speech and Speech-to-Text integration reference for the planned Luister & Spreek expansion.
  - https://github.com/ndenicolais/SpeechAndText
- **amsavarthan/trivia-revamp** — MIT License — trivia progression and reusable game-screen organization patterns.
  - https://github.com/amsavarthan/trivia-revamp
- **opatry/wordle-kt** — MIT License — existing WoordChallenge letter-feedback inspiration already documented elsewhere in this repository.
  - https://github.com/opatry/wordle-kt

## What was added to LeerSprong

The first Engagement Pack adds an original native `Speelplein` screen with:

- playable Memory & Match game;
- Quick Quiz Arena with group-aware questions;
- daily-mission presentation;
- direct challenge lanes for Rekenen, Nederlands and Engels when available in the learner's group;
- a trophy-shelf presentation tied to real learning concepts rather than fabricated production scores;
- a prominent Home entry point so the learner sees more than one linear lesson path.

## Licensing rule

MIT/Apache-2.0 projects may inform implementation while preserving the required notices when code is substantially reused. Projects without a verified compatible license may be used only as visual/product inspiration; no code or assets from them are copied into LeerSprong NL.
