plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fairtechtoten"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.fairtechtoten"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //Chave do maps
        manifestPlaceholders["MAPS_API_KEY"] = project.findProperty("MAPS_API_KEY") ?: ""
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
    //Para Gerar automaticamente as Views
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    //Ui Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    //Navigation Libraries
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // RecyclerView + CardView
    implementation(libs.recyclerview)
    implementation(libs.cardview)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    // OkHttp Logging
    implementation(libs.okhttp.logging)

    // Lifecycle
    implementation(libs.lifecycle.runtime)

    //Room
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)

    // Google Maps + GPS
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)
    implementation(libs.maps.utils)

    //Test Libraries
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

}