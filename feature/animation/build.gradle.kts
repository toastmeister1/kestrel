plugins {
    id("kestrel.android.library")
    id("kestrel.android.compose")
    id("kestrel.verify.detekt")
}

android {
    namespace = "com.toastmeister1.kestrel.feature.animation"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
}