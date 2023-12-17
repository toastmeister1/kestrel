plugins {
    `kotlin-dsl`
}

group = "com.toastmeister1.kestrel.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.verify.detektPlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kestrel.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose") {
            id = "kestrel.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidApplicationFlavors") {
            id = "kestrel.android.application.flavors"
            implementationClass = "AndroidApplicationFlavorConventionPlugin"
        }

        register("androidHilt") {
            id = "kestrel.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidLibrary") {
            id = "kestrel.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("kotlinLibrary") {
            id = "kestrel.kotlin.library"
            implementationClass = "KotlinLibraryConventionPlugin"
        }

        register("androidCompose") {
            id = "kestrel.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("deteKt") {
            id = "kestrel.verify.detekt"
            implementationClass = "VerifyDetektConventionPlugin"
        }
    }
}
