# Cocktail Tiles

Offline Android alkalmazás, amely 13 koktélt jelenít meg csempés rácsban, részletes nézettel és automatikus bezárással.

## Adatok és képek bemásolása
- `cocktails.json` fájlt másold a `app/src/main/assets/Coctaildata/cocktails.json` útvonalra.
- A koktélképeket (PNG/JPG) `imageName` néven helyezd el a `app/src/main/res/drawable/` mappában.

A repóban csak vektoralapú helykitöltő képek találhatók; cseréld őket saját képekre ugyanazzal a fájlnévvel, ha szükséges.

## Buildelés
1. Futtasd `gradle wrapper` parancsot, ha a `gradle/wrapper/gradle-wrapper.jar` hiányzik.
2. Ezek után futtatható a build: `./gradlew assembleDebug`.

## Futtatás
- Nyisd meg a projektet Android Studio-ban.
- Válaszd a **Run** opciót, majd indítsd Pixel 7 / Android 14 emulátoron.
- Az alkalmazás teljesen offline módon működik, nem igényel internet-hozzáférést.

## Funkciók
- 2 oszlopos rács portré módban, 3 oszlopos táj módban.
- 13 koktél csempe, görgethető lista, legalább 4 csempe látható egyszerre.
- Érintésre részletes nézet nyílik meg, amely 20 másodperc után automatikusan bezáródik.
- Az adatok és képek helyben tároltak az alkalmazásban.
