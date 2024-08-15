plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":core:common"))
    implementation(libs.symbol.processing.api)
    implementation(libs.kotlinpoet)
}