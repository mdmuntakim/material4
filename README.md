# Material 4

**Material 4 is an unofficial, community design-system extension of Google Material 3.**
It is not affiliated with, endorsed by or published by Google. "Material 4" is not an
official Google product. The library depends on the public
`com.google.android.material:material` artifact and subclasses its public View classes;
it does not fork, patch or redistribute Material 3 source.

Material 4 is written in Java and targets Android 8.0+ (`minSdk 26`).

## Modules

| Module | Contents |
| --- | --- |
| `material4` | `io.material4` library: same-named View subclasses in `io.material4.widget`, token/theme APIs, shape, depth, motion, adaptive and accessibility helpers |
| `sample` | **Material4Gallery**, the catalogue APK that imports `io.material4.widget.*` |

## Build

```bash
./gradlew :material4:assembleDebug
./gradlew :sample:assembleDebug
./gradlew :sample:assembleRelease
```

Java 17 and Android SDK 36 are required. The release build uses the environment variables
`MATERIAL4_KEYSTORE`, `MATERIAL4_KEYSTORE_PASSWORD`, `MATERIAL4_KEY_ALIAS` and
`MATERIAL4_KEY_PASSWORD`; the keystore is never committed.

## Token model

Components resolve `Material4Tokens` from the theme overlay. A screen never styles a widget:

```java
Material4Tokens tokens = Material4Tokens.from(context);
Material4Shape.apply(shapeable, context, tokens.radiusLarge / density);
Material4Depth.applyGlass(view, tokens, 0f);
```

`Material4Theme.apply(activity, config)` applies light/dark/auto, wallpaper dynamic colour,
a content-based seed colour, high contrast, reduced motion and the Material 3 ↔ Material 4
comparison overlay.

## Licensing

Material Components for Android and AndroidX are Apache 2.0 licensed. See
`NOTICE` and `LICENSE`. No proprietary assets are included; the launcher icon is an
original vector drawn for this project.
