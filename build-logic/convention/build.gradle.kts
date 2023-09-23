plugins {
    `kotlin-dsl`
}

group = "com.toastmeister1.kestrel.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "kestrel.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
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
    }
}
