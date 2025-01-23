plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.work_manager"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.work_manager"
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
    buildFeatures {
        viewBinding = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //--------------------------------------------------------------------------------------firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.messaging)
    //----------------------------------------------------------------------------------work-manager
    implementation(libs.androidx.work.runtime.ktx)
    //-----------------------------------------------------------------------------------dagger-hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    //--------------------------------------------------------------------------------------retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    //------------------------------------------------------------------------------------------ktor

    implementation("io.ktor:ktor-client-android:3.0.3")
}