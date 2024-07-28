import org.metaborg.convention.Person
import org.metaborg.convention.MavenPublishConventionExtension

// Workaround for issue: https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("org.metaborg.convention.root-project")
    alias(libs.plugins.gitonium)
    // Set versions for plugins to use, only applying them in subprojects (apply false here).
    id("org.metaborg.devenv.spoofax.gradle.langspec") apply false // No version: use plugin built by composite build.
}

allprojects {
    apply(plugin = "org.metaborg.gitonium")

    // Configure Gitonium before setting the version
    gitonium {
        mainBranch.set("master")
        tagPrefix.set("devenv-release/")
    }

    version = gitonium.version
    group = "org.metaborg.devenv"

    pluginManager.withPlugin("org.metaborg.convention.maven-publish") {
        extensions.configure(MavenPublishConventionExtension::class.java) {
            repoOwner.set("metaborg")
            repoName.set("spoofax-deploy")

            metadata {
                inceptionYear.set("2008")
                developers.set(listOf(
                    Person("Gohla", "Gabriel Konat", "gabrielkonat@gmail.com"),
                    Person("lennartcl", "Lennart Kats", "lclkats@gmail.com"),
                    Person("hendrikvanantwerpen", "Hendrik van Antwerpen", "hendrik@van-antwerpen.net"),
                    Person("Apanatshka", "Jeff Smits", "mail@jeffsmits.net"),
                    Person("Virtlink", "Daniel A. A. Pelsmaeker", "developer@pelsmaeker.net"),
                ))
            }
        }
    }
}
