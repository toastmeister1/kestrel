plugins {
    id("kestrel.android.library")
    id("kestrel.android.hilt")
    id("kestrel.android.compose")
}

android {
    namespace = "com.toastmeister1.kestrel.feature.home"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
}