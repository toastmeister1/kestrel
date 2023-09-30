@file:Suppress("DSL_SCOPE_VIOLATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
}

/**
 *  version catalog 'libs' ISSUE(refer to the link)
 *  https://github.com/gradle/gradle/issues/22797
 */
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.kotlinAndroid) apply false
}

tasks {
    register<Copy>("copyGitHooks") {
        description = "Copies the git hooks from /git-hooks to the .git folder."
        group = "git hooks"
        from("$rootDir/scripts/pre-commit")
        into("$rootDir/.git/hooks/")
    }

    register<Exec>("installGitHooks") {
        description = "Installs the pre-commit git hooks from /git-hooks."
        group = "git hooks"
        workingDir = rootDir
        commandLine = listOf("chmod")
        args("-R", "+x", ".git/hooks/")
        dependsOn("copyGitHooks")
        doLast {
            logger.info("Git hook installed successfully.")
        }
    }

    afterEvaluate {
        tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
    }
}