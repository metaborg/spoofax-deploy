// Apply plugin the old way for compatibility with both Gradle 5.6.4 and 6+.
buildscript {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
    dependencies {
        classpath("org.metaborg:gradle.config:0.7.3")
    }
}
apply(plugin = "org.metaborg.gradle.config.root-project")

plugins {
    id("org.metaborg.gitonium") version "1.7.4"
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
    apply(plugin = "org.metaborg.gitonium")

    gitonium {
        tagPrefix.set("devenv-release/")
        firstParentOnly.set(false)
    }

    version = gitonium.version
    group = "org.metaborg.devenv"

    ext["spoofax2Version"] = spoofax2Version
    ext["spoofax2BaselineVersion"] = spoofax2BaselineVersion
    ext["spoofax2DevenvVersion"] = spoofax2DevenvVersion
}
