import com.android.build.api.dsl.LibraryExtension
import com.toastmeister1.convention.configureComposeAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            extensions.configure<LibraryExtension> {
                configureComposeAndroid(this)
            }
        }
    }

}