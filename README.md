[![](https://jitpack.io/v/cristmasbox/GlyphConverter.svg)](https://jitpack.io/#cristmasbox/GlyphConverter)

# Glyph Converter
A library for converting between MdC (Manuel de Codage) and GlyphX (Hieroglpyh XML). Both are used for displaying egyptian hieroglyphs.

> [!TIP]
> If you want to render Hieroglyphs in Android try these libraries:
> [THOTH](https://github.com/cristmasbox/THOTH) and [MAAT](https://github.com/cristmasbox/MAAT)

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
  implementation("com.github.cristmasbox:GlyphConverter:1.5.0")
}
```
> [!NOTE]
> For the implementation for other build systems like `Groovy` see [here](https://jitpack.io/#cristmasbox/GlyphConverter/)

## Implementation with `.jar` file
Download the `GlpyhConverter_versionname.jar` file from this repository, create a `libs` folder in your project directory and paste the file there. Then add this dependency to your `build.gradle.kts` file:
```
dependencies {
  implementation(files("../libs/GlpyhConverter_versionname.jar"))
}
```

> [!IMPORTANT]
> If you renamed the `.jar` file you also have to change the name in the dependencies

## Version Catalog
### 25.10.2025@1.0.0
This is the first release of the GlyphConverter library.
### 07.11.2025@1.5.0
Support for brackets in MdC added. Now you can type in:
```
N17:i*(p:t)*(t:p)*i:N17
```
### latest Version
`07.11.2025@1.5.0`