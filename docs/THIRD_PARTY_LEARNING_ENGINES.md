# Third-party learning-engine references

LeerSprong NL uses or studies the following open-source projects. Code is only adapted when the license permits it; otherwise the repository is used for UX/architecture research only.

## Integrated/adapted

### Open Spaced Repetition — android-fsrs
- Repository: https://github.com/open-spaced-repetition/android-fsrs
- License: MIT
- Use in LeerSprong: FSRS scheduling equations/state concepts (difficulty, stability, retrievability and Again/Hard/Good/Easy) are adapted into the native Android review scheduler. LeerSprong keeps its own persistence/API surface and child-facing mastery model.
- Copyright notice: Copyright (c) 2023 Open Spaced Repetition. MIT license applies to adapted portions.

### Multiply
- Repository: https://github.com/stephenWanjala/Multiply
- License: MIT
- Use in LeerSprong: the smart-math distractor strategy is adapted for deterministic RekenChallenge lessons (off-by-one/off-by-two, doubling/halving and plausible offsets).
- Copyright notice: Copyright (c) 2025 WANJALA STEPHEN. MIT license applies to adapted portions.

### wordle-kt
- Repository: https://github.com/opatry/wordle-kt
- License: MIT
- Use in LeerSprong: the duplicate-safe two-pass letter evaluation strategy is adapted for the Dutch `WoordChallenge`; Wordle branding, word lists and UI are not copied.
- Copyright notice: Copyright (c) 2022 Olivier Patry. MIT license applies to adapted portions.

## Research/inspiration only

### ai4kids_android
- Repository: https://github.com/alfredang/ai4kids_android
- License: no root LICENSE was found during the 2026-09-02 audit.
- Use: UX/feature inspiration only. No source code or assets copied.

### Oppia Android
- Repository: https://github.com/oppia/oppia-android
- License: Apache-2.0
- Use: interactive lesson, feedback, review and offline architecture patterns.

### Now in Android
- Repository: https://github.com/android/nowinandroid
- License: Apache-2.0
- Use: Android architecture, local source-of-truth, repository and sync patterns.

Always re-check a repository's current license before copying new files or substantial code.