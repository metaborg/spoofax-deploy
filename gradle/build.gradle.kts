import org.metaborg.convention.MavenPublishConventionExtension

// Workaround for issue: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.metaborg.gradle.config.root-project") version "0.7.1"
    id("org.metaborg.gitonium") version "1.7.0"
}

gitonium {
    tagPrefix = "devenv-release/"
}

allprojects {
    apply(plugin = "org.metaborg.gitonium")

    version = gitonium.version
    group = "org.metaborg"

    pluginManager.withPlugin("org.metaborg.convention.maven-publish") {
        extensions.configure(MavenPublishConventionExtension::class.java) {
            repoOwner.set("metaborg")
            repoName.set("metaborg-gradle")
        }
    }
}
