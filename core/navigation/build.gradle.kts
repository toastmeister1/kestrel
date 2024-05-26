plugins {
    id("kestrel.android.library")
    id("kestrel.android.compose")
    id("kestrel.spotless")
}

android {
    namespace = "com.toastmeister1.kestrel.core.navigation"
}

dependencies {
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.hilt.navigation.compose)
}