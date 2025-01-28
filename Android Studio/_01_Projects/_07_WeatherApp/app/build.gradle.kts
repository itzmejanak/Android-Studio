plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.weather.SkyScout"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weather.SkyScout"
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)  // Keep this line
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Lottie Animation
    implementation("com.airbnb.android:lottie:6.4.1")

    // Volley Library
    implementation("com.android.volley:volley:1.2.1")
}