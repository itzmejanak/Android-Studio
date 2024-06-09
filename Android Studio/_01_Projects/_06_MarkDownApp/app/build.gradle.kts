plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.trex.markdown"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.trex.markdown"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        exclude ("META-INF/LICENSE-LGPL-2.1.txt")
        exclude ("META-INF/LICENSE-LGPL-3.txt")
        exclude ("META-INF/LICENSE-W3C-TEST")
        exclude ("META-INF/DEPENDENCIES")
    }
}

dependencies {
    implementation("com.vladsch.flexmark:flexmark-all:0.64.8")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}