# java-lib-archetype

[![CI](https://github.com/haisi/java-lib-archetype/actions/workflows/ci.yml/badge.svg?branch=main)](https://github.com/haisi/java-lib-archetype/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/li.selman/java-lib-archetype.svg)](https://central.sonatype.com/artifact/li.selman/java-lib-archetype)

A Maven archetype that scaffolds a Java library set up to publish to Maven Central: Error Prone/NullAway,
Spotless (palantir-java-format), Checkstyle, a 100% JaCoCo coverage gate, GitHub Actions CI/release/Pages
workflows, JReleaser, and the usual community-health files (CONTRIBUTING, CODE_OF_CONDUCT, SECURITY, issue/PR
templates). It's the reusable scaffolding extracted from
[`li.selman:null-markeder`](https://github.com/haisi/null-markeder) — see that repo's write-up for the
rationale behind each piece.

The generated project also wires up `null-markeder` itself as a test dependency, with an `ArchitectureTest`
that enforces every package carries JSpecify's `@NullMarked` — so new libraries start null-safe by default.

## Using it

Not yet published, so install it to your local repo first:

```shell
./mvnw install
```

Then generate a new project (interactive prompts for every property):

```shell
mvn archetype:generate \
  -DarchetypeGroupId=li.selman \
  -DarchetypeArtifactId=java-lib-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT
```

Or non-interactively:

```shell
mvn archetype:generate \
  -DarchetypeGroupId=li.selman \
  -DarchetypeArtifactId=java-lib-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT \
  -DinteractiveMode=false \
  -DgroupId=li.selman \
  -DartifactId=my-new-lib \
  -Dversion=0.1.0-SNAPSHOT \
  -Dpackage=li.selman.mynewlib \
  -DlibraryDescription="One sentence describing what the library does." \
  -DgithubOwner=haisi \
  -DgithubRepo=my-new-lib \
  -DauthorName="Hasan Selman Kara" \
  -DauthorEmail=hasan.selman.kara@gmail.com \
  -DlicenseYear=2026
```

`githubRepo` defaults to `artifactId`, `authorId` defaults to `githubOwner`, `javaVersion` defaults to `25`,
`nullMarkederVersion` defaults to the latest known `null-markeder` release — override any of them with `-D`.

## After generating

A few things `archetype:generate` can't do for you:

- **Executable bits are lost.** `mvnw`, `bumpPomVersion.sh`, `setPomVersions.sh`, `release.sh` and
  `dryrun-release.sh` come out non-executable. Run:
  ```shell
  chmod +x mvnw bumpPomVersion.sh setPomVersions.sh release.sh dryrun-release.sh
  ```
- **`docs/index.html`** ships with placeholder "The problem" / "What it does" copy — replace it with your
  library's actual pitch before enabling GitHub Pages.
- **`README.md`**'s Usage section is a bare dependency snippet — flesh it out once the library has an API.
- **GPG keys and Central Portal credentials** (for `dryrun-release.sh` / real releases) are not part of the
  archetype and are never templated in — set those up per the target repo's own process.
- **`git init` and the initial commit** are up to you; `archetype:generate` doesn't touch git.

## Releasing this archetype itself

This repo publishes to Maven Central the same way projects generated from it do: `release.yml` fires on a
`vX.Y.Z` tag push, stages the build with `mvn deploy`, and hands off to JReleaser (`jreleaser.yml`) for
signing and the Central Portal deploy. Two differences from a generated project's own release flow, both
because this project has no Java source of its own (only the Velocity templates under
`src/main/resources`): no javadoc jar is attached (nothing to document), and the sources jar
(`maven-source-plugin`, `<excludeResources>false</excludeResources>` in `pom.xml`) bundles
`src/main/resources` instead of `src/main/java`.

```shell
./bumpPomVersion.sh
git push
./release.sh
```

## Developing this archetype

```shell
./mvnw install                    # build + install to ~/.m2, needed before archetype:generate can find it
./mvnw verify                     # also runs the integration test under src/test/resources/projects/basic
```

The integration test (`archetype:integration-test`, wired into the `verify` phase via `maven-archetype-plugin`)
generates a sample project from `src/test/resources/projects/basic/archetype.properties` and runs the goals
in `goal.txt` against it — the fastest way to check a template edit didn't break the output.

`goal.txt` deliberately stops at `test-compile`, not `test` or `verify`. The plugin always generates the IT
project under `target/test-classes/projects/basic/project/<artifactId>/`, and ArchUnit's
`ImportOption.DoNotIncludeTests` — which `ArchitectureTest.java` uses, matching `null-markeder`'s own recommended
pattern — decides whether a class is a test by checking whether its classpath location *contains the substring*
`test-classes`. Since that's true here for reasons that have nothing to do with the generated project's own
main/test split, it wrongly excludes every class (main included) and `ArchitectureTest` fails with `This
package does not contain any sub package '<package>'` — a false negative specific to this nested-IT layout, not
a real bug. Confirmed by copying a generated project outside any `test-classes`-named path and running
`mvn verify` there directly, where it passes cleanly. If you need to verify the full `verify` lifecycle
(Checkstyle/Spotless/JaCoCo included) after a template change, generate into `/tmp` or similar rather than
trusting the IT for that part:

```shell
cd /tmp && mvn archetype:generate -DarchetypeGroupId=li.selman -DarchetypeArtifactId=java-lib-archetype \
  -DarchetypeVersion=1.0.0-SNAPSHOT -DinteractiveMode=false -DgroupId=com.example -DartifactId=example-lib \
  -Dpackage=com.example.lib -DlibraryDescription=x -DgithubOwner=example -DauthorName=x -DauthorEmail=x@x.com \
  -DlicenseYear=2026
cd example-lib && chmod +x mvnw *.sh && ./mvnw verify
```

Template files live under `src/main/resources/archetype-resources/`; which ones get Velocity-filtered (and
which are copied byte-for-byte) is controlled by `src/main/resources/META-INF/maven/archetype-metadata.xml`.

Two Velocity gotchas to know about in filtered files (there is no working escape hatch for either — avoid the
shape instead, there's no `symbol_dollar`-style helper in this archetype-plugin's fileset generator, unlike
the old `archetype:create` mechanism some tutorials describe):

- `${...}` that isn't one of this archetype's properties (groupId/artifactId/version/package or the custom
  ones in `archetype-metadata.xml`) is left untouched, which is exactly what makes Maven's own
  `${some.pom.property}` references survive into the generated `pom.xml` unresolved, ready for Maven itself
  to resolve at build time — nothing to do for those, as long as the name isn't one of ours.
  GitHub Actions' own `${{ ... }}` expressions are safe the same way — the doubled `{` already stops Velocity
  from recognizing them as a reference.
- Two things break outright and have no fix but rewording: a bare `${var}` whose name collides with one of
  this archetype's properties (e.g. a shell variable literally named `${version}` would get resolved to the
  *archetype's* version, not the shell's — see how `release.yml` uses `${releaseVersion}` instead), and any
  `${...}` Velocity can't parse as a reference at all, e.g. bash's `${VAR#pattern}` — the `#` is a hard parse
  error, not just a bad substitution (`release.yml`'s tag-stripping step uses `cut -c2-` instead). Backslash-escaping
  does not help with either case; rename the variable or restructure the shell instead.
