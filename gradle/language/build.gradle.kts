// Apply plugin the old way for compatibility with both Gradle 5.6.4 and 6+.
buildscript {
  repositories {
    maven("https://artifacts.metaborg.org/content/groups/public/")
  }
  dependencies {
    classpath("org.metaborg:gradle.config:0.4.7")
  }
}
apply(plugin = "org.metaborg.gradle.config.root-project")

plugins {
  id("org.metaborg.gitonium") version "0.1.5"

  // Set versions for plugins to use, only applying them in subprojects (apply false here).
  id("org.metaborg.devenv.spoofax.gradle.langspec") apply false // No version: use plugin built by composite build.
  id("de.set.ecj") version "1.4.1" apply false
}

gitonium {
  tagPattern = java.util.regex.Pattern.compile("""devenv-release/(.+)""")
}

subprojects {
  configure<mb.gradle.config.MetaborgExtension> {
    configureSubProject()
  }
}

val spoofax2Version: String = System.getProperty("spoofax2Version")
val spoofax2BaselineVersion: String = System.getProperty("spoofax2BaselineVersion")
allprojects {
  group = "org.metaborg.devenv"
  ext["spoofax2Version"] = spoofax2Version
  ext["spoofax2BaselineVersion"] = spoofax2BaselineVersion
}
