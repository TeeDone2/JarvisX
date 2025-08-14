plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
}

android {
  namespace = "com.jarvisx.app"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.jarvisx.app"
    minSdk = 24
    targetSdk = 34
    versionCode = 1
    versionName = "1.0.0"

    vectorDrawables.useSupportLibrary = true
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
      // Signing will be injected by CI via env + decoded keystore
      signingConfig = signingConfigs.getByName("release")
    }
    debug {
      signingConfig = signingConfigs.getByName("debug")
    }
  }

  signingConfigs {
    getByName("release") {
      // Values get injected by CI – left blank locally
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {
  implementation("androidx.core:core-ktx:1.13.1")
  implementation("androidx.appcompat:appcompat:1.7.0")
  implementation("com.google.android.material:material:1.12.0")

  // Biometrics
  implementation("androidx.biometric:biometric:1.2.0-alpha05")

  // (Optional) Speech to text basic
  implementation("androidx.activity:activity-ktx:1.9.2")

  // (Stub) Supabase (left as placeholder to avoid network issues)
  // implementation("io.github.jan-tennert.supabase:postgrest-kt:2.5.5")
}
