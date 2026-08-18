# ${artifactId}

[![CI](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/${githubOwner}/${githubRepo}/actions/workflows/ci.yml)
[![Coverage Status](https://coveralls.io/repos/github/${githubOwner}/${githubRepo}/badge.svg?branch=main)](https://coveralls.io/github/${githubOwner}/${githubRepo}?branch=main)
[![Maven Central](https://img.shields.io/maven-central/v/${groupId}/${artifactId}.svg)](https://central.sonatype.com/artifact/${groupId}/${artifactId})
[![Javadoc](https://javadoc.io/badge2/${groupId}/${artifactId}/javadoc.svg)](https://javadoc.io/doc/${groupId}/${artifactId})
[![License](https://img.shields.io/github/license/${githubOwner}/${githubRepo})](LICENSE)
[![Mutation Score](https://${githubOwner}.github.io/${githubRepo}/pit/badge.svg)](https://${githubOwner}.github.io/${githubRepo}/pit/)

[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=bugs)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=duplicated_lines_density)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=reliability_rating)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=sqale_index)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=sqale_rating)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=${githubOwner}_${githubRepo}&metric=vulnerabilities)](https://sonarcloud.io/summary/new_code?id=${githubOwner}_${githubRepo})

${libraryDescription}

[**Website**](https://${githubOwner}.github.io/${githubRepo}/)

<!-- TODO: replace this with your library's actual usage instructions, and delete src/main/java/Placeholder.java
     and its test - they only exist so this freshly generated project builds and documents out of the box. -->

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

`verify` also runs Spotless (palantir-java-format + sorted `pom.xml`) and Error Prone/NullAway via the compiler
plugin. Run `./mvnw spotless:apply` to auto-format before committing.

## Mutation Testing

[![Mutation Score](https://${githubOwner}.github.io/${githubRepo}/pit/badge.svg)](https://${githubOwner}.github.io/${githubRepo}/pit/)

Line/branch coverage only proves a test executed some code, not that it would notice a bug in it. [PIT
mutation testing](https://pitest.org) seeds small deliberate bugs ("mutants") into the compiled classes and
checks whether the test suite actually fails for each one; a mutant that survives is a gap in the tests.

Mutation testing runs nightly at around 02:00 UTC via `.github/workflows/pit-mutation-testing.yml`, and only
when at least one new commit has landed on `main` since the last successful run - so it stays off the critical
path for every push/PR while still picking up changes automatically. It can also be triggered manually from
the Actions tab.

See the full HTML mutation report at `https://${githubOwner}.github.io/${githubRepo}/pit/` for a per-class,
per-mutator breakdown, or run it locally with:

```shell
./mvnw test-compile org.pitest:pitest-maven:mutationCoverage
open target/pit-reports/index.html
```

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
