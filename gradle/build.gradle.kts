plugins {
  id("org.metaborg.gradle.config.root-project") version "0.4.2"
  id("org.metaborg.gitonium") version "0.1.4"
}

gitonium {
  tagPattern = java.util.regex.Pattern.compile("""devenv-release/(.+)""")
}
