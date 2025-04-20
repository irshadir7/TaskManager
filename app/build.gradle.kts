plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt") // Needed for annotation processing
    id("com.google.dagger.hilt.android")
    id ("kotlin-parcelize")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.cyber.taskmanager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.cyber.taskmanager"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Jetpack Compose BOM (Updated)
    implementation (platform("androidx.compose:compose-bom:2024.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.03.00"))

    // Core AndroidX dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Jetpack Compose UI
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // ✅ Explicitly add Compose Animation to fix crash
    implementation("androidx.compose.animation:animation:1.6.5")

    // Hilt for Dependency Injection
    implementation("com.google.dagger:hilt-android:2.51.1")
    implementation(libs.androidx.runtime.livedata)
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Lifecycle & ViewModel (Fixed incorrect versions)
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    // Retrofit for Networking
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // Coroutines for Async operations
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coil for Image Loading in Jetpack Compose
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Testing Dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    // Debugging Tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Room components
    val roomVersion = "2.6.1"
    implementation ("androidx.room:room-runtime:$roomVersion")
    implementation ("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:2.6.1")
    androidTestImplementation ("androidx.room:room-testing:$roomVersion")

    // Feather Icons Pack
    implementation("br.com.devsrsouza.compose.icons:feather:1.1.0")
    // System UI controller
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.32.0")
    // Compose Calendar library
    implementation ("com.kizitonwose.calendar:compose:2.6.0-beta02")

    // Custom Toast Library
    implementation ("com.github.GrenderG:Toasty:1.5.2")

    // Compose Date Picker Dialog Library
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.2.0")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.2.0")

    implementation ("androidx.navigation:navigation-compose:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // Compose Number Selector Dialog Library
    implementation ("com.github.MFlisar.ComposeDialogs:core:1.0.8")
    implementation ("com.github.MFlisar.ComposeDialogs:dialog-number:1.0.8")
    implementation("androidx.datastore:datastore-preferences:1.1.4")
    implementation("androidx.compose.material3:material3:1.3.2")
    implementation ("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("org.burnoutcrew.composereorderable:reorderable:0.9.6")
    implementation("androidx.compose.material:material:1.7.8") // Or the latest version compatible with your Compose BOM
    implementation("androidx.compose.foundation:foundation:1.7.8")

}

kapt {
    correctErrorTypes = true
}