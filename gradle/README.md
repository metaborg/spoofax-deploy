# Spoofax 2 Gradle devenv build

A Gradle build of Spoofax 2, built under the `org.metaborg.devenv` group ID to prevent clashes with regular Spoofax 2 artifacts.

## Development

### Git conventions
The `master` branch of this repository is both buildable in isolation, and built via the [devenv repository](https://github.com/metaborg/devenv).

### Building

This repository is buildable in isolation, but requires a checkout via the [devenv repository](https://github.com/metaborg/devenv) because it requires multiple Git repositories to be checked out.
First follow the instructions from devenv to check out and/or update all repositories.

This repository is built with Gradle, which requires a JDK of at least version 8 to be installed. Higher versions may work depending on [which version of Gradle is used](https://docs.gradle.org/current/userguide/compatibility.html).

To build this repository, run `./gradlew buildAll` on Linux and macOS, or `gradlew.bat buildAll` on Windows.

### Automated Builds

This project has no automated builds.

### Updating the packaged Eclipse (and Java) version

This repository uses the [Coronium](https://github.com/metaborg/coronium) Gradle plugin to download and edit Eclipse builds. These Eclipse builds are packaged with the Spoofax plugin(s), and have a version with a packaged JDK. Coronium has a default version for Eclipse hardcoded, which can be overridden. While it is overridden in many places through this repository (and/or submodules), it is currently unclear whether the overrides are spanning every relevant gradle project. The safest and easiest way to update the Eclipse version is therefore to replace the version string not only in every `build.gradle.kts` file that uses `majorVersion.set("2022-06")` in `mavenize`, but to also update the default version in the Coronium code itself, and update the `baseRepositories` property of `EclipseCreateInstallation` in Coronium.

The JDK that is downloaded and packaged into Eclipse is managed entirely within Coronium in the `EclipseInstallationAddJvm` class. Last changes to both of these versions can be found in [this commit](https://github.com/metaborg/coronium/commit/1c3a4e4957e3597d1d5ed48f04c7de4727f48666).

Note that all JDK and Eclipse downloads are downloaded via our [artifact server](https://artifacts.metaborg.org) in the [releases repository](https://artifacts.metaborg.org/content/repositories/releases/). For both we use our artifact server as a mirror, where we manually upload the JDK and Eclipse for the supported architectures. In addition, the Eclipse version requires a _group_ of p2 proxies. Follow [a link to an existing group](https://artifacts.metaborg.org/#view-repositories;eclipse-2022-06~configuration) to find the three required proxies of packages, releases, and updates.

### Publishing
This repository is published via Gradle and Git with the [Gitonium](https://github.com/metaborg/gitonium) and [Gradle Config](https://github.com/metaborg/gradle.config) plugins.
It is published to our [artifact server](https://artifacts.metaborg.org) in the [releases repository](https://artifacts.metaborg.org/content/repositories/releases/).

Ensure that the `master` branches in this repository and the other Spoofax 2 repositories are up-to-date.

Then ensure that you depend on only released versions of Spoofax 2. That is, no `SNAPSHOT` or other development versions.
All dependencies are managed in the `gradle.properties` file:
- `systemProp.spoofax2Version` sets the version of Spoofax 2 that this build uses.
- `systemProp.spoofax2BaselineVersion` sets the version of Spoofax 2 that this build uses as a baseline for bootstrapping.

To make a new release, create a tag in the `spoofax-deploy` repository in the form of `devenv-release/*.*.*` where `*.*.*` is the version of the release you'd like to make. The tag must be the only tag for the commit. Then build the `devenv` root using `./gradlew buildAll` to check if building succeeds.

> [!NOTE]
> If there are no new commits in this repository, you can create an empty commit for a version bump:
>
> ```shell
> git commit --allow-empty -m "Empty commit for version bump"`.
> ```
>

This project must be published from your local system.
You will need an account with write access to our artifact server, and tell Gradle about this account.
Create the `~/.gradle/gradle.properties` file if it does not exist.
Add the following lines to it, replacing `<username>` and `<password>` with those of your artifact server account:
```
publish.repository.metaborg.artifacts.username=<username>
publish.repository.metaborg.artifacts.password=<password>
```
Then run `./gradlew publishAll` to publish all built artifacts.
You should also push the release tag you made to the remote such that this release is reproducible by others.


#### Using the published version
After publishing a new release, update the version in the following places:
- https://github.com/metaborg/spoofax-pie/blob/develop/gradle.properties#L4
- https://github.com/metaborg/devenv/blob/develop/gradle.properties#L4

If you've bumped `systemProp.spoofax2Version` and/or `systemProp.spoofax2BaselineVersion`, also update:
- https://github.com/metaborg/spoofax-pie/blob/develop/gradle.properties#L3
- https://github.com/metaborg/devenv/blob/develop/gradle.properties#L2-L3

