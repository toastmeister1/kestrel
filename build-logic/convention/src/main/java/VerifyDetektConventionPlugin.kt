import com.toastmeister1.convention.configureVerifyDetekt
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class VerifyDetektConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            configureVerifyDetekt()
            configureDetekt()
        }
    }

}

private fun Project.configureDetekt() {
    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        // Target version of the generated JVM bytecode. It is used for type resolution.
        jvmTarget = JavaVersion.VERSION_17.majorVersion

        buildUponDefaultConfig = true // preconfigure defaults
        allRules = false // activate all available (even unstable) rules.
        parallel = true
        config.setFrom(listOf(file("$rootDir/config/detekt/detekt.yml")))
    }
}

