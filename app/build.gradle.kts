plugins {
    alias(libs.plugins.android.application)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.dam.retrofitbuscadorautores"
    compileSdk {
        version = release(37) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.dam.retrofitbuscadorautores"
        minSdk = 25
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    implementation ("androidx.room3:room3-runtime:3.0.3")
    ksp ("androidx.room3:room3-compiler:3.0.3")
    implementation ("androidx.room:room-ktx:2.8.5")


    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
}