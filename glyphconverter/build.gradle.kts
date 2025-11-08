plugins {
    id("java-library")
    `maven-publish`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies{
    implementation(libs.commons.lang3)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.github.cristmasbox"
            artifactId = "glyphconverter"
            version = "1.6.0"

            pom {
                name.set("GlpyhConverter")
                description.set("A library for converting between MdC (Manuel de Codage) and GlyphX (Hieroglyph XML). Both are used for displaying egyptian hieroglyphs.")
                url.set("https://github.com/cristmasbox/GlyphConverter/")
                licenses {
                    license {
                        name.set("GNU License V3")
                        url.set("https://www.gnu.org/licenses/")
                    }
                }
                developers {
                    developer {
                        id.set("cristmasbox")
                        name.set("Georg Schierholt")
                    }
                }
            }
        }
    }
}