plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.plugin.compose") // Apply Compose plugin here
    //alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
   // alias(libs.plugins.compose.compiler)
   // alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.studyspotz"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.studyspotz"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.3.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.ui.graphics.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)


    // Compose Activity
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.runtime.livedata)

    // Voyager for navigation
    val voyagerVersion = "1.1.0-beta02"
    // Android

    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")

    // Hilt integration
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")

    // LiveData integration
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Authentication
    //implementation("com.google.firebase:firebase-auth:23.1.0")
    implementation("com.google.firebase:firebase-auth")

    //Images in Description Page
    implementation("io.coil-kt.coil3:coil-compose:3.0.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
}