// Apply plugin the old way for compatibility with both Gradle 5.6.4 and 6+.
buildscript {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
    dependencies {
        classpath("org.metaborg:gradle.config:0.5.6")
    }
}
apply(plugin = "org.metaborg.gradle.config.root-project")

plugins {
    id("org.metaborg.gitonium") version "1.2.0"

    // Set versions for plugins to use, only applying them in subprojects (apply false here).
    id("org.metaborg.devenv.spoofax.gradle.langspec") apply false // No version: use plugin built by composite build.
}

gitonium {
    tagPrefix = "devenv-release/"
}

subprojects {
    configure<mb.gradle.config.MetaborgExtension> {
        configureSubProject()
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
