rootProject.name = "spoofax2.releng.language.root"

// This allows us to use plugins from Metaborg Artifacts
pluginManagement {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
}

// This allows us to use the catalog in dependencies
dependencyResolutionManagement {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
    versionCatalogs {
        create("libs") {
            from("org.metaborg.spoofax3:catalog:0.3.3")
        }
    }
}

// This downloads an appropriate JVM if not already available
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

buildscript {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
    dependencies {
        classpath("org.metaborg:gradle.config:0.7.1")
    }
}

val devenvRootRelativePath = "../../../"
val repositoryConfigurations =
    mb.gradle.config.devenv.RepositoryConfigurations.fromRootDirectory(rootDir.resolve(devenvRootRelativePath))

fun String.includeProject(id: String, dir: String = id, path: String = "$devenvRootRelativePath/$this/$dir") {
    val projectDir = file(path)
    include(id)
    project(":$id").projectDir = projectDir
}


if (repositoryConfigurations.isUpdated("sdf")) {
    "sdf".run {
        includeProject("org.metaborg.meta.lang.template")
        includeProject("sdf3.ext.statix")
    }
}

if (repositoryConfigurations.isUpdated("stratego")) {
    "stratego".run {
        includeProject("org.metaborg.meta.lang.stratego")
        includeProject("stratego.lang")
        includeProject("strategolib")
        includeProject("gpp")
        //includeProject("stratego.build.spoofax2.integrationtest") // Disabled because tests take a long time.
    }
}

if (repositoryConfigurations.isUpdated("esv")) {
    "esv".run {
        includeProject("org.metaborg.meta.lang.esv")
    }
}

if (repositoryConfigurations.isUpdated("nabl")) {
    "nabl".run {
        includeProject("org.metaborg.meta.nabl2.lang", "nabl2.lang")
        includeProject("org.metaborg.meta.nabl2.runtime", "nabl2.runtime")
        includeProject("org.metaborg.meta.nabl2.shared", "nabl2.shared")

        includeProject("statix.lang")
        includeProject("statix.runtime")
    }
}

if (repositoryConfigurations.isUpdated("spoofax2")) {
    "spoofax2".run {
        includeProject("meta.lib.spoofax")
    }
}

if (repositoryConfigurations.isUpdated("spt")) {
    "spt".run {
        includeProject("org.metaborg.meta.lang.spt")
    }
}
