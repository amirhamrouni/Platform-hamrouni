# Open-source engineering references

LeerSprong NL uses open-source projects as engineering references and only copies/adapts code when the source license and attribution requirements allow it.

## Android / platform architecture

### Now in Android — android/nowinandroid
- License: Apache-2.0.
- Used patterns: modular Compose architecture, feature/core separation, repository contracts, Room as local source of truth, Retrofit-backed remote data, WorkManager synchronization, state-hoisting, testable ViewModels and UI component catalog patterns.
- LeerSprong adaptation: learner progress, lesson attempts and review queue will be read locally first and synchronized remotely in the background. We do not copy Now in Android product UI.

### Oppia Android — oppia/oppia-android
- License: Apache-2.0.
- Used patterns: interactive lesson/exploration player, state-specific interaction rendering, drag/drop-sort interaction structure, learner feedback, audio-player testing, offline lesson access and flashback/review concepts.
- LeerSprong adaptation: a Compose/MVI lesson player with one interaction at a time, corrective feedback, targeted relearning and review. We do not copy Oppia branding or lesson content.

## Web / learner experience

### Oppia Web — oppia/oppia
- License: Apache-2.0.
- Used patterns: branching instructional flow and tutor-like feedback.

### Kiranism next-shadcn-dashboard-starter
- License: MIT.
- Used only as an adult dashboard structure/reference for responsive layout and component discipline. Child-facing surfaces are custom LeerSprong UI.

## Licensing rule

A repository with unclear or unverified licensing may be used only as visual/UX inspiration. Its code, assets and content must not be copied into LeerSprong until the license is verified and compatible.
