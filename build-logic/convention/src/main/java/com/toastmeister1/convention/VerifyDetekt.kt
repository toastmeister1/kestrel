package com.toastmeister1.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureVerifyDetekt() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    with(pluginManager) {
        apply("io.gitlab.arturbosch.detekt")
    }

    dependencies {
        "detektPlugins"(libs.findLibrary("verify.detektFormatting").get())
    }
}