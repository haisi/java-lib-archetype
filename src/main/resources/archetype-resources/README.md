# ${artifactId}

[![CI](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/${githubOwner}/${githubRepo}/badge.svg?branch=main)](https://coveralls.io/github/${githubOwner}/${githubRepo}?branch=main)
[![Maven Central](https://img.shields.io/maven-central/v/${groupId}/${artifactId}.svg)](https://central.sonatype.com/artifact/${groupId}/${artifactId})
[![Javadoc](https://javadoc.io/badge2/${groupId}/${artifactId}/javadoc.svg)](https://javadoc.io/doc/${groupId}/${artifactId})
[![License](https://img.shields.io/github/license/${githubOwner}/${githubRepo})](LICENSE)

${libraryDescription}

[**Website**](https://${githubOwner}.github.io/${githubRepo}/)

<!-- TODO: replace this with your library's actual usage instructions. -->

## Usage

Add dependency

```xml
<dependency>
    <groupId>${groupId}</groupId>
    <artifactId>${artifactId}</artifactId>
    <version>VERSION</version>
</dependency>
```

## Building

```shell
./mvnw verify
```

Test coverage is enforced at 100% (line and branch) via JaCoCo; `verify` fails if it drops below that. Run
`open target/site/jacoco/index.html` after a build to see the report.

`verify` also runs Spotless (palantir-java-format + sorted `pom.xml`), Checkstyle, and Error Prone/NullAway via
the compiler plugin. Run `./mvnw spotless:apply` to auto-format before committing.

## Releasing

Releases are published to Maven Central via [JReleaser](https://jreleaser.org). Pushing a tag matching `v*`
(e.g. `v1.0.0`) triggers `.github/workflows/release.yml`, which stages the build artifacts and hands them to
JReleaser to sign and deploy to the [Central Portal](https://central.sonatype.com).

```shell
./bumpPomVersion.sh
git push
./release.sh
```

## Contributing

Bug reports, feature requests and pull requests are welcome — see [CONTRIBUTING.md](CONTRIBUTING.md). This
project follows a [Code of Conduct](CODE_OF_CONDUCT.md); by participating you agree to abide by it.

## License

`${artifactId}` is licensed under the [Apache License, Version 2.0](LICENSE).

See `jreleaser.yml` for the deployment configuration.
