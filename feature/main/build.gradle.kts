plugins {
    id("kestrel.android.library")
    id("kestrel.android.hilt")
    id("kestrel.android.compose")
    id("kestrel.spotless")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.toastmeister1.kestrel.feature.main"

    sourceSets {
        val main by getting
        main.java.srcDirs("build/generated/ksp/debug/kotlin")
        main.java.srcDirs("build/generated/ksp/release/kotlin")
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:navigation"))

    implementation(project(":feature:home"))
    implementation(project(":feature:animation"))

    implementation(project(":core:common"))
    implementation(project(":processor"))
    ksp(project(":processor"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.testManifest)
}