# Project guide for Codex

## Start here for every task

- Read this file, then inspect the files relevant to the requested change.
- Check `git status --short` and `git branch --show-current` before editing. Preserve existing changes and untracked files; do not assume a particular branch is active.
- Use `pom.xml` and current source code as the source of truth. Recheck the known gaps below before treating them as current failures.
- Keep changes focused on the user's request. If the request is for analysis or a plan, provide that before changing implementation.
- Update this guide when an authorized change alters the project layout, setup, commands, or architecture.

## What this project does

Collab is a Java browser test automation project for the public OrangeHRM demo application. It uses Selenium WebDriver, TestNG, and the Page Object Model. The repository contains the automation framework and tests, rather than the OrangeHRM application itself.

The default target is `https://opensource-demo.orangehrmlive.com/web/index.php/auth/login`.
Application settings are centralized in `src/main/resources/config.properties` and loaded by
`utilities.ConfigReader`. Browser selection, driver setup, and options live in `browser.BrowserUtil`,
using the `browser.Browser` enum; `BaseTest` consumes both components.

Existing scenarios cover login, dashboard navigation, PIM employee search/edit/delete, navigation to personal details, updating contact details, and leave search/application.

## Stack and build

- Maven coordinates: `org.example:Collab:1.0-SNAPSHOT`; packaging: JAR.
- Java release: 17; source encoding: UTF-8.
- Selenium 4.34.0, TestNG 7.10.2, and WebDriverManager 6.1.0.
- Maven Compiler Plugin 3.13.0 and Surefire 3.3.0.
- The POM also declares Lombok, Log4j2/SLF4J, Extent Reports, and Apache POI. A dependency declaration alone does not mean reporting or spreadsheet integration is implemented.
- TestNG has compile scope because framework code lives under `src/main/java` and uses TestNG classes. Preserve this unless reorganizing the framework is part of the task.
- There is currently no Maven wrapper or checked-in CI configuration. Use installed Maven and a JDK supporting release 17.

## Repository map

| Path | Responsibility |
| --- | --- |
| `pom.xml` | Dependencies, Java release, compiler, TestNG suite configuration |
| `src/main/resources/config.properties` | Default application host, page paths, public demo credentials |
| `src/main/java/utilities/ConfigReader.java` | Immutable configuration snapshot, overrides, validation, URL construction |
| `src/main/java/browser/Browser.java` | Supported browser enum: CHROME, FIREFOX, EDGE |
| `src/main/java/browser/BrowserUtil.java` | Default browser, runtime selection, driver setup, browser-specific options |
| `src/main/java/org/base/BaseTest.java` | Shared driver, configured browser initialization, waits, teardown, soft assertions |
| `src/main/java/pages/` | Page objects: `LoginPage`, `DashboardPage`, `PimPage`, `PersonalDetailsPage`, `ContactDetailsPage`, `LeavePage` |
| `src/main/java/utilities/SelUtils.java` | Shared explicit waits, clicking, scrolling, and text entry |
| `src/main/java/utilities/Utils.java` | Existing sleep helper |
| `src/test/java/org/example/` | TestNG test classes for the page flows |
| `target/` | Generated Maven output; ignored by Git |

The dashboard test filename is `DashBoardPageTest.java`; the page object is `DashboardPage.java`. Use the actual names when selecting tests.

## How the framework works

1. Tests extend `BaseTest` and call `initialization()` from `@BeforeMethod`.
2. Initialization calls `BrowserUtil.createDriver()`, maximizes its window, opens the configured login URL, and sets a 20-second shared wait. Chrome is the default in `BrowserUtil`; Edge and Firefox are also supported.
3. Page objects extend `BaseTest`, inherit the shared `wd`, and initialize private `@FindBy` elements with `PageFactory.initElements(wd, this)`.
4. Navigation methods return the destination page object, for example `LoginPage.clickOnlogin(...)` returns `DashboardPage`.
5. Tests perform assertions and call `teardown()` from `@AfterMethod` to quit the browser. Soft assertion tests must call `assertAll()`.

`SelUtils` exposes static helpers with 15-second explicit waits. Its click helper scrolls and waits, then falls back to a JavaScript click for selected Selenium exceptions. It also implements `WebDriver` and `JavascriptExecutor`, but several instance methods are stubs; use its existing static helpers rather than treating it as a complete driver implementation.

## Runtime configuration

`ConfigReader.getInstance()` loads `/config.properties` from the classpath once per JVM
and validates all settings before browser initialization. Each key resolves in this order:
JVM system property, environment variable, then the properties file. Environment variable
names are the uppercase property names with dots replaced by underscores:

| Property | Environment variable |
| --- | --- |
| `app.base.url` | `APP_BASE_URL` |
| `app.login.path` | `APP_LOGIN_PATH` |
| `app.dashboard.path` | `APP_DASHBOARD_PATH` |
| `login.username` | `LOGIN_USERNAME` |
| `login.password` | `LOGIN_PASSWORD` |

The base URL is an HTTP(S) origin (host and optional port); page paths start with `/`.
Missing or blank required values and invalid URL settings fail immediately.
Blank overrides are rejected rather than falling back silently.
Configuration is an immutable snapshot; changing overrides after loading does not reload it.
Wait durations remain in `BaseTest` (20 seconds) and `SelUtils` (15 seconds).
Only public demo credentials belong in the file; supply real credentials through environment
variables or the CI secret store. Invalid-login credentials and scenario data stay in tests.

Browser settings are independent of `ConfigReader` and `config.properties`.
`BrowserUtil.createDriver()` resolves `-Dbrowser`, then the `BROWSER` environment variable,
then its single `DEFAULT_BROWSER` constant (`Browser.CHROME`). Names are case-insensitive;
blank or unsupported overrides are rejected before driver setup. `createDriver(Browser.EDGE)`
explicitly selects an enum value and bypasses runtime overrides. Selection is resolved on each
no-argument call. Each creation returns a new driver and uses fresh options objects; the utility
does not store a shared driver. It uses WebDriverManager setup and the matching Selenium driver
constructor for all three browsers. Chrome retains the disabled autofill profile preference;
Firefox and Edge use default options. All browsers remain visible by default.

After the existing build/suite blockers are resolved, examples are:

```powershell
mvn "-Dbrowser=EDGE" "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
mvn "-Dapp.base.url=https://your-test-host.example" "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
# Existing browser utility checks without launching browsers
# (still require framework compilation).
mvn "-Dtest=browser.BrowserUtilTest" test
```

## Commands and validation

Run commands from the repository root. These are intended workflows, not a claim that the current checkout builds successfully.

```powershell
java -version
mvn -version

# Compile framework and test sources without executing browser tests.
mvn test-compile

# Execute the configured TestNG suite once testng.xml exists.
mvn test

# Select a test class explicitly after compilation blockers are resolved.
mvn "-Dtest=LoginPageTest" test

# Select a single test method.
mvn "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
```

Browser execution requires the selected browser, access to the demo site, and any driver downloads needed by WebDriverManager. Chrome currently runs with a visible window; no headless option is configured. Maven may also need network access to resolve dependencies.

For code changes, compile first and run relevant existing tests when the environment supports them. Do not add unit tests without explicit user approval for the current task; approval to implement a feature, fix, or refactor does not itself authorize new unit tests. This restriction includes new unit-test classes, new test methods in existing classes, and temporary unit-test harnesses. Use compilation, existing tests, and diff review for validation unless the user approves additional unit tests. For documentation-only changes, check accuracy and the diff without launching browsers. Report which checks ran and distinguish build failures, environment limitations, and scenario failures. Surefire normally writes test results under `target/surefire-reports/`.

## Known gaps observed on 2026-10-10

- Surefire references root `testng.xml`, but that file is absent. The configured suite cannot be assumed runnable until the file is supplied or the configuration is deliberately changed.
- `PimPage.clickAddEmployeeLink()` returns and instantiates `AddEmployeePage`, but no such class exists in the checkout. `mvn test-compile` with Java 17 confirmed this compilation blocker on 2026-10-10.
- `wd` and the shared wait are static. The current driver lifecycle is unsuitable for parallel test execution; do not enable TestNG parallelism without isolating browser state.
- Tests depend on shared demo data, hardcoded employee names, and fixed leave dates. Contact updates, employee deletion, and leave applications change remote demo state. Choose a targeted test appropriate to the task and inspect its effects before running a broad suite.
- Some tests perform actions without outcome assertions, including the invalid-login scenario. A passing run of these tests alone does not prove the expected behavior.
- Leave application tests currently call the list-page date-entry methods despite separate `enterFromDateUnderApplyLeave` and `enterToDateUnderApplyLeave` methods existing. Check the actual page and locators when working on these scenarios.

These gaps are context, not instructions to fix unrelated code automatically.

## Conventions for changes

- Keep UI locators and browser interactions in the relevant page object; keep scenario orchestration and assertions in tests.
- Reuse `SelUtils` explicit waits and wait for an observable condition after navigation or submission. Prefer condition-based waits over adding fixed sleeps.
- Prefer stable semantic locators over positional XPath expressions when adding or repairing elements.
- Preserve existing method contracts and package layout unless the task calls for a refactor; update callers together when changing a contract.
- Add unit tests only after explicit user approval, including new methods in existing unit-test classes and temporary unit-test harnesses.
- Give authorized new tests clear outcome assertions. Keep setup and teardown consistent with the existing TestNG lifecycle.
- Keep Java compatibility at release 17 and match nearby formatting. Avoid dependency upgrades and unrelated cleanup unless needed for the requested change.
- Do not add real credentials, personal data, local IDE files, browser binaries, or generated reports to version control. Existing demo credentials in tests belong to the public demo environment.
- Do not commit, push, or change branches unless the task authorizes it. Finish with a concise description of changes, validation, and any remaining blocker.
