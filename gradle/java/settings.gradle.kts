rootProject.name = "spoofax2.releng.java.root"

pluginManagement {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
}


buildscript {
    repositories {
        maven("https://artifacts.metaborg.org/content/groups/public/")
    }
    dependencies {
        classpath("org.metaborg:gradle.config:0.5.6")
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


if (repositoryConfigurations.isUpdated("mb-rep")) {
    "mb.rep".run {
        includeProject("org.spoofax.terms")
        includeProject("org.spoofax.interpreter.library.index")
    }
}

if (repositoryConfigurations.isUpdated("mb-exec")) {
    "mb.exec".run {
        includeProject("org.metaborg.util")
        includeProject("util-vfs2")
        includeProject("org.spoofax.interpreter.core")
        includeProject("org.spoofax.interpreter.library.java")
        includeProject("org.spoofax.interpreter.library.xml")
    }
}

if (repositoryConfigurations.isUpdated("jsglr")) {
    "jsglr".run {
        includeProject("jsglr.shared")
        includeProject("org.spoofax.jsglr")
        includeProject("org.spoofax.jsglr2")
        includeProject("org.spoofax.interpreter.library.jsglr")
    }
}

if (repositoryConfigurations.isUpdated("sdf")) {
    "sdf".run {
        includeProject("org.metaborg.parsetable")
        includeProject("sdf2parenthesize", "org.metaborg.sdf2parenthesize")
        includeProject("sdf2table", "org.metaborg.sdf2table")
    }
}

if (repositoryConfigurations.isUpdated("stratego")) {
    "stratego".run {
        includeProject("stratego.build")
        includeProject("stratego.build.spoofax2")
    }
}

if (repositoryConfigurations.isUpdated("strategoxt")) {
    "strategoxt".run {
        includeProject("org.strategoxt.strj", "strategoxt/stratego-libraries/java-backend")
    }
}

if (repositoryConfigurations.isUpdated("nabl")) {
    "nabl".run {
        includeProject("scopegraph")
        includeProject("p_raffrayi")

        includeProject("nabl2.terms")
        includeProject("nabl2.solver")

        includeProject("statix.solver")
        includeProject("statix.generator")

        includeProject("renaming.java")
    }
}

if (repositoryConfigurations.isUpdated("spoofax2")) {
    "spoofax2".run {
        includeProject("org.metaborg.core")
        includeProject("org.metaborg.core.test")
        includeProject("org.metaborg.spoofax.core")
        includeProject("org.metaborg.meta.core")
        includeProject("org.metaborg.spoofax.meta.core")
        includeProject("org.metaborg.spoofax.nativebundle")
    }
}

if (repositoryConfigurations.isUpdated("spoofax.gradle")) {
    "spoofax.gradle".run {
        includeProject("spoofax.gradle", "plugin")
    }
}

if (repositoryConfigurations.isUpdated("spt")) {
    "spt".run {
        includeProject("org.metaborg.mbt.core")
        includeProject("org.metaborg.spt.core")
    }
}
