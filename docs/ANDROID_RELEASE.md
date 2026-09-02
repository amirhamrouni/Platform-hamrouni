# LeerSprong NL Android release

## Current release candidate

- Application ID: `nl.leersprong.app`
- Version: `0.9.0-rc1`
- Version code: `9`
- Minimum Android: API 23
- Target Android: API 37

## CI release gates

Every Android pull request must pass:

1. JVM/unit tests, including lesson catalog integrity checks.
2. Android Lint with errors blocking the build.
3. Debug APK build.
4. Release APK build.
5. Release App Bundle (`.aab`) build.

The debug APK is installable for device testing. The release APK/AAB remain unsigned unless release signing environment variables are explicitly provided.

## Release signing

Never commit a keystore or passwords to the repository. A release build becomes signed only when all four environment variables are present:

- `LEERSPRONG_KEYSTORE_PATH`
- `LEERSPRONG_KEYSTORE_PASSWORD`
- `LEERSPRONG_KEY_ALIAS`
- `LEERSPRONG_KEY_PASSWORD`

The keystore path must point to a file mounted securely in the build environment. Store the keystore and credentials in the release system's secret store, not in Git history.

## Play Console gate

Before publishing to Google Play:

- create or import the permanent app signing identity;
- configure Play App Signing;
- inject signing secrets into the trusted release environment;
- build and verify the signed `.aab`;
- complete Play Console privacy/data-safety declarations based on the final Firebase/AI/analytics configuration;
- run device smoke tests on the signed candidate.

An unsigned release artifact proves the production variant compiles, but is not itself a publishable Play Store release.
