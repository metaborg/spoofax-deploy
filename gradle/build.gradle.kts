plugins {
    id("org.metaborg.gradle.config.root-project") version "0.7.1"
    id("org.metaborg.gitonium") version "1.2.0"
}

gitonium {
    tagPrefix = "devenv-release/"
}
