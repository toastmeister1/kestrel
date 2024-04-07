import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("kestrel.android.library")
    id("kestrel.verify.detekt")
    id("kestrel.android.hilt")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.toastmeister1.kestrel.core.data"

    defaultConfig {
        buildConfigField("String", "TMDB_BASE_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "TMDB_API_KEY",  "\"${getApiKey("TMDB_API_KEY")}\"")
        buildConfigField("String", "UNSPLASH_BASE_URL", "\"blank\"")
        buildConfigField("String", "UNSPLASH_API_KEY", "\"blank\"")
    }
}

fun getApiKey(propertyName: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyName)
}

dependencies {
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okhttp.core)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.espresso.core)
}