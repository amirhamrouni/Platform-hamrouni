# LeerSprong NL — Android architecture

## Product target
Native Android app, not a WebView wrapper. Child experience mirrors the approved LeerSprong visual language while sharing the same learner/curriculum concepts as the web platform.

## Architecture
- UI: Jetpack Compose + Material 3, adaptive layouts.
- State: ViewModel + StateFlow, immutable UI state.
- Domain: use-cases for daily path, lesson sessions, review scheduling, rewards and tutor context.
- Data: repositories hide local/remote implementation details.
- Local: Room for offline learner state, downloaded lessons and pending sync operations.
- Remote: Firebase Auth/Firestore initially; service interfaces remain replaceable.
- Background: WorkManager for sync and downloaded-content maintenance.
- Navigation: Navigation 3 with typed destinations.

## Feature modules planned
- feature:home — learner journey / continue card / daily tasks.
- feature:lesson — full-screen interactive lesson player.
- feature:review — spaced retrieval queue.
- feature:tutor — contextual Leermaatje UI and guarded AI gateway.
- feature:progress — mastery, XP, badges and streaks.
- feature:profile — learner preferences and home-language support.
- feature:parent — parent dashboard and child switching.

## Data modules planned
- core:model
- core:database
- core:network
- core:data
- core:designsystem
- core:analytics
- core:testing

## Engineering constraints
- No direct Firebase/Room calls from composables.
- No fake progress values in production UI.
- Offline writes enter a durable pending-sync queue.
- AI calls never ship provider secrets in the APK.
- Child-facing AI receives scoped learner context and age-appropriate guardrails.
- Accessibility: touch targets, content descriptions, scalable text, contrast and reduced-motion support.

## Open-source references
Patterns are adapted from official Android architecture guidance and `android/nowinandroid`. External code is only reused when license compatibility and provenance are clear.
