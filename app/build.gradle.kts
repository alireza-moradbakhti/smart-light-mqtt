import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.devtools.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.smartlightapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.smartlightapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        // Exclude these files from being bundled in the APK
        exclude("META-INF/INDEX.LIST")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/io.netty.versions.properties") // Add this line
    }
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // room db
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    kapt(libs.room.compiler)
    testImplementation(libs.room.testing)


    //Dagger - Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt (libs.hilt.compiler)

    // --- Retrofit ---
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // --- OkHttp Logging (Optional but recommended for debugging) ---
    implementation(libs.okhttp.logging)

    //constraint Layout for compose
    implementation(libs.androidx.constraintlayout.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //MQTT hive
    implementation(libs.hivemq.mqtt.client)
}

kapt {
    correctErrorTypes = true
}