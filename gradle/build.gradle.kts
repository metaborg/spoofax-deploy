plugins {
  id("org.metaborg.gradle.config.root-project") version "0.5.6"
  id("org.metaborg.gitonium") version "1.1.0"
}

gitonium {
  tagPrefix = "devenv-release/"
}
