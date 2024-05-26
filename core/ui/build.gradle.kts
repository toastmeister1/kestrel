plugins {
    id("kestrel.android.library")
    id("kestrel.android.compose")
    id("kestrel.spotless")
}

android {
    namespace = "com.toastmeister1.kestrel.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
}