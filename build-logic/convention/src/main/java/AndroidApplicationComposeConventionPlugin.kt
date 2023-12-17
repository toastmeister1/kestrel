import com.android.build.api.dsl.ApplicationExtension
import com.toastmeister1.convention.configureComposeAndroid
import com.toastmeister1.convention.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
            }

            extensions.configure<ApplicationExtension> {
                configureComposeAndroid(this)
                defaultConfig.targetSdk = 33
            }
        }
    }

}