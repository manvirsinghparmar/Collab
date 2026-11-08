# Project guide for Codex

## Purpose and sources of truth

Collab is a Java browser test automation framework for the public OrangeHRM demo.
It uses Selenium WebDriver, TestNG, Hamcrest and the Page Object Model.
The repository contains automation code, not the OrangeHRM application.

- Target Java 11. Use `pom.xml` for dependency versions and build configuration.
- Use current source code and `testng.xml` to verify behavior and suite selection.
- Keep this file a concise framework reference. Update it only for meaningful
  changes to architecture, layout, setup, commands or lasting conventions, or when
  the user explicitly requests it. Do not add individual method walkthroughs,
  routine test results, troubleshooting history or entries for small code changes.

## Framework structure

| Location | Responsibility |
| --- | --- |
| `pom.xml` / `testng.xml` | Maven build and sequential suite selection |
| `testng-pr.xml` / `testng-regression.xml` | Explicit CI smoke and unattended regression selection |
| `.github/workflows/` / `docs/ci.md` | GitHub Actions triggers, runtime and regression coverage |
| `src/main/java/basePackage/base/BaseTest.java` | Shared browser initialization, configuration, waits and shutdown |
| `src/main/java/pages/` | Page objects, locators and browser interactions |
| `src/main/java/config/` | Immutable application configuration |
| `src/main/java/env/` | Environment enum and environment name validation in `EnvManagerUtil` |
| `src/main/java/browser/` | Browser enum, selection, options and driver creation |
| `src/main/java/platform/` | Local operating-system detection |
| `src/main/java/listeners/` | TestNG execution logging, HTML reports and Selenium event logging |
| `src/main/java/utilities/` | Reusable Selenium and runtime helpers |
| `src/main/java/hamcrestAssertion/` | Hamcrest soft assertion collector |
| `src/main/resources/config.properties` | Default URLs and public demo credentials |
| `src/test/java/basePackage/testPages/` | TestNG scenarios and assertions |
| `target/` | Generated Maven output, reports, logs and screenshots; ignored by Git |

## Coding and test conventions

- Tests extend `basePackage.base.BaseTest` and use package `basePackage.testPages`.
  Keep package declarations, directories and suite references consistent.
- Tests initialize the browser in `@BeforeMethod`. Prefer a `tearDown()` method
  delegating to `super.teardown()`; ensure browser shutdown still occurs after
  setup/test failures and after any test-data cleanup.
- Page objects extend `BaseTest`, initialize elements with `PageFactory`, and
  declare fixed elements as private `@FindBy` fields. Use stable semantic locators.
- Keep browser interactions and readiness waits in page objects; keep scenario
  orchestration and outcome assertions in tests.
- Reuse `SelUtils` static helpers and condition-based waits. Avoid fixed sleeps.
  Use the existing text/date helpers so platform-specific input behavior is shared.
- Keep page objects limited to what approved scenarios need. Preserve existing
  method contracts and nearby formatting unless the requested change requires more.
- Use Hamcrest assertions, not TestNG assertion classes. Hard assertions suit
  prerequisites; soft assertions suit independent checks that should continue.
- Name a fresh local `HamcrestSoftAssertions` collector `hmAssert` and use
  try-with-resources. Closing it reports collected failures; no `assertAll()` call
  is needed. Do not share collectors across threads or store them in test fields.
- Pass actual values to appropriate matchers and give assertions useful messages.
  Keep tests concise; avoid repeating login/dashboard assertions in feature tests.
- Use configured credentials and unique generated data where appropriate. Clean up
  only records owned by the test, using an identity check before deletion.
- Keep Java compatibility at release 11. Avoid unrelated dependency upgrades,
  package changes and cleanup.

## Runtime configuration

- `ConfigReader` loads an immutable configuration snapshot from the classpath.
  Precedence is JVM system property, environment variable, then properties file.
- Select DEV, STAGE or PROD through `-Dapp.env`, then `APP_ENV`, then `app.env`
  in `config.properties` (DEV by default). Names are case-insensitive. One
  environment is selected per JVM; blank or unsupported values fail early.
- Environment variable names are uppercase property names with dots replaced by
  underscores, for example `app.base.url.stage` becomes `APP_BASE_URL_STAGE`.
- Each environment has its own origin: `app.base.url.dev`, `app.base.url.stage`
  and `app.base.url.prod`. These replace the single `app.base.url` setting and
  its overrides. Only the selected origin is required; an unset origin fails
  before browser creation without falling back to another environment. DEV uses
  the public demo; supply STAGE/PROD origins in the file or through overrides.
- Application settings cover the base URL, login/dashboard paths and credentials.
  The base URL is an HTTP(S) origin; shared page paths start with `/` and
  credentials retain their existing overrides. Missing, blank or invalid required
  settings fail early.
- Tests extending `BaseTest` can call `getEnvironment()` to obtain the selected
  `env.Environment` enum, or access `config.environment()`. These report the
  configured environment rather than detecting it from the browser's current URL.
  `env.EnvManagerUtil` validates environment names separately from configuration loading.
- Browser selection is independent: `-Dbrowser`, then `BROWSER`, then Chrome.
  Supported names are CHROME, FIREFOX, EDGE and SAFARI; names are case-insensitive.
- Use `BrowserUtil` for driver creation and `PlatformUtil` for local OS checks.
  WebDriverManager handles Chrome, Firefox and Edge drivers without hardcoded paths.
- Keep only public demo credentials in source. Supply real credentials through
  environment variables or the CI secret store.

## Setup and commands

Install JDK 11, Maven and the selected browser. Configure `JAVA_HOME` and `PATH`.
Allow dependency/driver downloads and access to the configured application host.
Browsers run visibly by default. Safari requires macOS and `safaridriver --enable`;
it uses the system driver. Keep browser/JDK architecture consistent on macOS.

Run commands from the repository root:

```powershell
# Compile framework and test sources without browser execution.
mvn test-compile

# Run the four existing PR smoke scenarios.
mvn "-Dtestng.suite=testng-pr.xml" test

# Run the configured sequential suite (includes data-changing scenarios).
mvn test

# Select a browser and a single test explicitly.
mvn "-Dbrowser=EDGE" "-Dtest=basePackage.testPages.LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test

# Select STAGE after supplying app.base.url.stage (or APP_BASE_URL_STAGE).
mvn "-Dapp.env=STAGE" "-Dtest=basePackage.testPages.LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
```

Explicit `-Dtest` selection overrides the suite and can run data-changing scenarios.
Surefire results are under `target/surefire-reports/`.

`-Dtestng.suite=<file>` selects a TestNG suite, defaulting to `testng.xml`.
CI runs Chrome on `windows-latest` with Java 11. PR checks run on opening,
code updates, edits and reopenings. Regression runs automatically at 04:00
America/Toronto every second calendar day from 2026-11-08. It selects 19
existing scenarios; six exclusions and ownership/cleanup rules are documented
in `docs/ci.md`. Neither workflow supports manual dispatch.

Tests inherit `CustomExecutionListener` and `EventReportListener` through
`BaseTest`'s `@Listeners`; do not also register them in `testng.xml`.
`CustomExecutionListener` owns lifecycle logging and failure screenshots.
`EventReportListener` extends `TestListenerAdapter` and writes an Extent Spark
HTML report once execution finishes, including failed/skipped configuration
methods as separate entries. Report totals include those configuration entries.
Reports are saved to `target/artifacts/reports/<unique-run-id>/index.html`;
`-Dframework.artifacts.dir=<path>` overrides the artifact root for reports,
logs and screenshots. Captured failure screenshots are embedded in the HTML.

## Working rules and framework constraints

- Read this guide and inspect relevant files before changing code. Check
  `git status --short` and `git branch --show-current`; preserve existing changes
  and untracked files.
- Keep scope focused. Provide requested analysis/plans before implementation.
  Show proposed new test scenarios before implementing them.
- Add unit tests only with explicit approval for the current task. This includes
  new methods in existing test classes and temporary unit-test harnesses.
- For code changes, compile first and run relevant existing tests when supported.
  For documentation-only changes, check accuracy and the diff without browsers.
  Report what ran and distinguish code, environment and scenario failures.
- Driver and wait state are shared/static. Keep execution sequential; parallel
  execution requires a separate change to isolate browser state and test data.
- Tests use shared public demo data, and some create, update or delete records.
  Inspect scenario effects before running a broad suite; do not assume fixed
  names/counts or interpret every demo-data failure as a framework defect.
- Some `SelUtils` driver-interface methods are stubs; use its static helpers.
- Do not add real personal data, secrets, IDE files, binaries or generated reports
  to version control.
- Do not commit, push or change branches without authorization.
