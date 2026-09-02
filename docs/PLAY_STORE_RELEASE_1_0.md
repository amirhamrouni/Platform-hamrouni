# LeerSprong NL 1.0.0 — Play Store release checklist

## Release identity
- Application ID: `nl.leersprong.app`
- Version name: `1.0.0`
- Version code: `11`
- Minimum Android: API 23
- Target/compile SDK: 37
- Release build is produced as APK and AAB in CI.
- Permanent Play signing requires the four release signing environment variables documented in `apps/android/RELEASE.md`.

## Current Android 1.0.0 data behavior
This checklist describes the code shipped in Android 1.0.0, not planned cloud features.

- Learner name, group, home-language preference and support-language setting: stored locally in DataStore.
- Lesson attempts, mastery, FSRS review state, XP and review timing: stored locally in Room.
- Notification permission: requested only for local smart-review reminders on Android versions that require runtime notification permission.
- App backup: disabled in the manifest for this release.
- Cleartext network traffic: disabled.
- No Android Firebase configuration is bundled in 1.0.0.
- No active Android Google login is bundled in 1.0.0.
- No advertising SDK is bundled in 1.0.0.
- No external analytics SDK is bundled in 1.0.0.
- The local `Maatje` coach must not be marketed as a live external AI service in this release.

## Suggested Play Data safety answers for 1.0.0
These must be re-checked against the exact signed AAB before submission.

- Data collected by the app and sent off-device: **No**, for the current Android 1.0.0 code path.
- Data shared with third parties: **No**, for the current Android 1.0.0 code path.
- Data encrypted in transit: not applicable while no Android off-device data path is enabled.
- User can request deletion: local app data can be removed by clearing app storage or uninstalling. Do not claim cloud account deletion until cloud accounts are enabled.
- Ads: **No**.

## Store listing truthfulness
Allowed claims for 1.0.0:
- Native Android learning app for Groep 1–8.
- Offline-first learner profile and progress.
- Smart Level Test for Nederlands and Rekenen.
- Adaptive review using persisted mastery and FSRS-style review scheduling.
- Dutch spelling and math challenges.
- Review reminders, XP and streak/progress views.

Do not claim in the 1.0.0 listing until separately activated and verified:
- Google/Firebase login or multi-device sync.
- Parent/teacher cloud dashboards connected to Android accounts.
- Live external AI tutor.
- Server-side analytics.

## Final submission gate
1. Create or choose the permanent Play App Signing/upload key.
2. Configure signing without committing keystore/passwords to Git.
3. Run the full Android CI gate: unit tests, lint, debug APK, release APK, release AAB.
4. Verify the signed AAB package/version/signature locally or in Play Console internal testing.
5. Install an internal-test build on at least one physical Android device and complete: onboarding → Smart Level Test → lesson → wrong-answer recovery → app restart → progress preserved → review reminder opt-in.
6. Publish the privacy page on a stable HTTPS URL and use that URL in Play Console.
7. Complete Content rating, Target audience/children declarations, Data safety and App access accurately from the signed production build.
8. Upload screenshots, feature graphic, app icon and localized store text.

## External blockers that code cannot fabricate
- A permanent Play signing identity/keystore.
- Access to the publisher's Google Play Console.
- A stable deployed HTTPS domain for the privacy page.

The repository is prepared so these are release operations, not missing application features.
