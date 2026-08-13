[![GitHub Repo stars](https://img.shields.io/github/stars/ThothDroid/GlyphConverter?style=for-the-badge&logo=github&color=yellowgreen)](https://github.com/ThothDroid/GlyphConverter)
[![Static part of Badge](https://img.shields.io/badge/Part%20of-Egyptian%20Writer%20App-%233DDC84?style=for-the-badge&logo=android&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/)
[![GitHub License](https://img.shields.io/github/license/ThothDroid/GlyphConverter?style=for-the-badge&logo=gnu&color=yellow)](https://github.com/ThothDroid/GlyphConverter?tab=GPL-3.0-1-ov-file)
[![GitHub forks](https://img.shields.io/github/forks/ThothDroid/GlyphConverter?style=for-the-badge&logo=git&logoColor=white&color=%23F05032)](https://github.com/ThothDroid/GlyphConverter)
\
[![jitpack](https://jitpack.io/v/ThothDroid/GlyphConverter.svg)](https://jitpack.io/#ThothDroid/GlyphConverter)
[![Static wiki Badge](https://img.shields.io/badge/Egyptian%20Writer-WIKI-yellow?style=flat&logo=gitbook&logoColor=white)](https://github.com/ThothDroid/Egyptian_Writer/wiki)
[![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ThothDroid/GlyphConverter?color=blue)](https://github.com/ThothDroid/GlyphConverter)
[![GitHub Release](https://img.shields.io/github/v/release/ThothDroid/GlyphConverter?color=%23F05032)](https://github.com/ThothDroid/GlyphConverter)

# Glyph Converter
A library for converting between MdC (Manuel de Codage) and GlyphX (Hieroglpyh XML). Both are used for displaying egyptian hieroglyphs.

*This library is part of the [Egyptian Writer](https://github.com/ThothDroid/Egyptian_Writer) Android App.*

> [!TIP]
> If you want to render Hieroglyphs in Android try the [Egyptian Writer](https://github.com/ThothDroid/Egyptian_Writer) Android App or these libraries: \
> [THOTH](https://github.com/ThothDroid/THOTH) and [MAAT](https://github.com/ThothDroid/MAAT)

## Implementation with jitpack
Add this to your `settings.gradle.kts` at the end of repositories:
```
dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
  }
}
```
Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation("com.github.ThothDroid:GlyphConverter:1.7.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#ThothDroid/GlyphConverter/)

## Implementation with `.jar` file
Download the [`GlyphConverter_versionname.aar`](https://github.com/ThothDroid/GlyphConverter/releases/latest) file from the latest release, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/GlyphConverter_versionname.aar"))
}
```

> [!IMPORTANT]
> If you renamed the `.jar` file you also have to change the name in the dependencies

## Version Catalog
> [!IMPORTANT]
> Since version `13.08.2026@1.8.0` the version catalog uses the new versioning system [see here](https://medium.com/@wassimsakri/the-ultimate-guide-to-versioning-in-software-development-e846eb292a0d).
### 25.10.2025@1.0.0
This is the first release of the GlyphConverter library.
### 07.11.2025@1.5.0
Support for brackets in MdC added. Now you can type in:
```
N17:(i*(p:t)*(t:p)*i):N17
```
### 08.11.2025@1.6.0
Minor bug fixes. Now you can successfully convert this:
```
N17:i*(p:t)*(t:p)*i:N17
```
### 07.12.2025@1.7.0
- updated dependencies
- Custom exception for `glyphX` parsing added:
`GlyphXParserException.java`
- Support for `\n` and `\t` added
- Support for page break and line break added:
  - `<br/>`: `!`
  - `<pbr/>`: `!!`
### 13.08.2026@1.8.0
- 5
### latest Version
`07.12.2025@1.8.0`
