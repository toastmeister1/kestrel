plugins {
    id("kestrel.android.library")
    id("kestrel.android.compose")
    id("kestrel.spotless")
}

android {
    namespace = "com.toastmeister1.kestrel.feature.animation"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))
}