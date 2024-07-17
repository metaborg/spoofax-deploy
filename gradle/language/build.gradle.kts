import org.metaborg.convention.MavenPublishConventionExtension

// Workaround for issue: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.metaborg.convention.root-project")
    alias(libs.plugins.gitonium)
    // Set versions for plugins to use, only applying them in subprojects (apply false here).
    id("org.metaborg.devenv.spoofax.gradle.langspec") apply false // No version: use plugin built by composite build.
}

gitonium {
    tagPrefix.set("devenv-release/")
}

allprojects {
    apply(plugin = "org.metaborg.gitonium")

    version = gitonium.version
    group = "org.metaborg.devenv"

    pluginManager.withPlugin("org.metaborg.convention.maven-publish") {
        extensions.configure(MavenPublishConventionExtension::class.java) {
            repoOwner.set("metaborg")
            repoName.set("spoofax-deploy")
        }
    }
}

val spoofax2Version: String = System.getProperty("spoofax2Version")
val spoofax2BaselineVersion: String = System.getProperty("spoofax2BaselineVersion")
val spoofax2DevenvVersion: String = System.getProperty("spoofax2DevenvVersion")
allprojects {
    group = "org.metaborg.devenv"
    ext["spoofax2Version"] = spoofax2Version
    ext["spoofax2BaselineVersion"] = spoofax2BaselineVersion
    ext["spoofax2DevenvVersion"] = spoofax2DevenvVersion
}
