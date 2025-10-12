plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.blueapps.glyphconvert"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.blueapps.glyphconvert"
        minSdk = 24
        targetSdk = 36
        versionCode = 100
        versionName = "12.10.2025@1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // Custom library
    implementation(project(":glyphconverter"))

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}