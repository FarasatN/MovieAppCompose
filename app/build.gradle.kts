plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidx.room) // Apply true here for the module
    alias(libs.plugins.kotlinAndroidKsp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "com.farasatnovruzov.movieappcompose"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.farasatnovruzov.movieappcompose"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }

    buildFeatures {
        compose = true
    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.1"
//    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    room { // This block is for the Room Gradle Plugin
        schemaDirectory("$projectDir/schemas")
    }

//    android {
//        compileOptions {
//            sourceCompatibility JavaVersion.VERSION_17
//                    targetCompatibility JavaVersion.VERSION_17
//        }
//
//        kotlinOptions {
//            jvmTarget = '17'
//        }
//    }
}
// ADD or MODIFY this block
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
        // You can add other Kotlin compiler options here if needed
        // For example:
        // freeCompilerArgs.add("-Xcontext-receivers")

    }
}
tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("-Xlint:deprecation")
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp) // Only available on Android/JVM.
//    implementation(libs.coil.network.ktor2)
//    implementation(libs.coil.network.ktor3)

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation(libs.androidx.room.ktx)
    // optional - Paging 3 Integration
    implementation(libs.androidx.room.paging)


    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    // For ViewModels
    implementation(libs.androidx.hilt.navigation.compose) // If using Compose Navigation

//    implementation("com.google.dagger:hilt-android:2.51.1") // Or latest compatible Hilt
//    ksp("com.google.dagger:hilt-compiler:2.51.1")     // Or latest compatible Hilt

    implementation (libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.gson)

    implementation(libs.lottie.compose)
//    implementation(libs.lottie.dotlottie)
    implementation(libs.dotlottie.android)
// Replace VERSION_NUMBER with the latest stable version

//    Example: For Google Mobile Ads (AdMob)
//    implementation("com.google.android.gms:play-services-ads:22.6.0") // Use the latest version

// Example: For basic Play Services tasks
//    implementation("com.google.android.gms:play-services-base:18.2.0") // Use the latest version

}