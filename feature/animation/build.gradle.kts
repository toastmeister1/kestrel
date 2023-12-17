plugins {
    id("kestrel.android.library")
    id("kestrel.android.compose")
    id("kestrel.verify.detekt")
}

android {
    namespace = "com.toastmeister1.kestrel.feature.animation"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
}