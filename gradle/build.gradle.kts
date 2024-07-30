plugins {
    id("org.metaborg.gradle.config.root-project") version "0.7.3"
    id("org.metaborg.gitonium") version "1.7.4"
}

allprojects {
    apply(plugin = "org.metaborg.gitonium")

    gitonium {
        tagPrefix.set("devenv-release/")
        firstParentOnly.set(false)
    }

    version = gitonium.version
    group = "org.metaborg.devenv"
}
