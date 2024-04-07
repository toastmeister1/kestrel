plugins {
    id("kestrel.android.application")
    id("kestrel.android.application.compose")
    id("kestrel.android.application.flavors")
    id("kestrel.android.hilt")
    id("kestrel.verify.detekt")
}

android {
    defaultConfig {
        applicationId = "com.toastmeister1.kestrel"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }

        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

            signingConfig = signingConfigs.getByName("debug")
        }
    }

    namespace = "com.toastmeister1.kestrel"
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))

    implementation(project(":feature:home"))
    implementation(project(":feature:main"))
    implementation(project(":feature:animation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}