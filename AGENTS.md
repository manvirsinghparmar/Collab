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
- Selenium 4.34.0, TestNG 7.10.2, and WebDriverManager 6.3.4.
- WebDriverManager was updated from 6.1.0 because Edge driver setup used the retired `msedgedriver.azureedge.net` host. Version 6.3.4 includes the replacement download URL and requires no browser factory changes. See the [WebDriverManager changelog](https://github.com/bonigarcia/webdrivermanager/blob/master/CHANGELOG.md#633---2025-11-08).
- Maven Compiler Plugin 3.13.0 and Surefire 3.3.0.
- The POM also declares Lombok, Log4j2/SLF4J, Extent Reports, and Apache POI. A dependency declaration alone does not mean reporting or spreadsheet integration is implemented.
- TestNG has compile scope because framework code lives under `src/main/java` and uses TestNG classes. Preserve this unless reorganizing the framework is part of the task.
- There is currently no Maven wrapper or checked-in CI configuration. Use installed Maven and a JDK supporting release 17.
- Windows and macOS use the same browser factory. WebDriverManager resolves platform-specific drivers for Chrome, Firefox, and Edge; Safari uses the driver included with macOS. Do not hardcode driver paths or OS/architecture overrides without evidence of a detection problem.

## Windows and macOS setup

- Install JDK 17 and Maven. JDK 21 has also been validated with the project's release 17 target.
- Set `JAVA_HOME` to the JDK directory and put its `bin` directory and Maven's `bin` directory on `PATH`.
- Install the browser you want to use: Chrome (default), Firefox, or Edge on Windows or macOS. Safari is also supported on macOS only.
- Allow Maven dependency resolution, WebDriverManager driver downloads, and access to the configured application host. Drivers are managed automatically; do not copy Windows `.exe` drivers to a Mac.

On Apple Silicon, use an ARM64 JDK and native browser installation; on Intel Macs,
use x64 installations. WebDriverManager handles driver resolution.

On macOS with JDK 17 installed, configure the current zsh session with:

```bash
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH="$JAVA_HOME/bin:$PATH"
```

Add these lines to `~/.zshrc` to persist them. Make sure Maven is also on `PATH`.
On Windows, set `JAVA_HOME` and add `%JAVA_HOME%\bin` and Maven's `bin` directory
through the environment variable settings.

For Safari on macOS, enable WebDriver once in Terminal before running tests:

```bash
safaridriver --enable
mvn "-Dbrowser=SAFARI" test
```

Follow any authorization prompt from macOS. Safari uses the system's bundled
`safaridriver`, so WebDriverManager does not download a driver for it. Selecting
Safari on Windows or another non-macOS host fails before driver creation with a
clear macOS-only message. Chrome remains the default on both systems; Safari is
selected explicitly with `-Dbrowser=SAFARI`, `BROWSER=SAFARI`, or
`BrowserUtil.createDriver(Browser.SAFARI)`. See Apple's
[Enable WebDriver on macOS](https://developer.apple.com/documentation/safari-developer-tools/macos-enabling-webdriver)
and [Selenium's Safari documentation](https://www.selenium.dev/documentation/webdriver/browsers/safari/).

## Repository map

| Path | Responsibility |
| --- | --- |
| `pom.xml` | Dependencies, Java release, compiler, TestNG suite configuration |
| `testng.xml` | Sequential smoke suite: the valid-login scenario only |
| `AGENTS.md` | Project guidance, Windows/macOS setup, commands, and compatibility validation |
| `.gitignore` | Excludes build output, IDE files, generated Extent reports, and macOS metadata |
| `src/main/resources/config.properties` | Default application host, page paths, public demo credentials |
| `src/main/java/utilities/ConfigReader.java` | Immutable configuration snapshot, overrides, validation, URL construction |
| `src/main/java/browser/Browser.java` | Supported browser enum: CHROME, FIREFOX, EDGE, SAFARI (macOS only) |
| `src/main/java/browser/BrowserUtil.java` | Default browser, runtime selection, driver setup, browser-specific options |
| `src/main/java/org/base/BaseTest.java` | Shared driver, configured browser initialization, waits, teardown, soft assertions |
| `src/main/java/pages/` | Page objects: `LoginPage`, `DashboardPage`, `PimPage`, `PersonalDetailsPage`, `ContactDetailsPage`, `LeavePage` |
| `src/main/java/utilities/SelUtils.java` | Shared explicit waits, clicking, scrolling, and text entry |
| `src/main/java/utilities/Utils.java` | Existing sleep helper |
| `src/test/java/org/testPages/` | TestNG test classes for the page flows |
| `target/` | Generated Maven output; ignored by Git |

The dashboard test filename is `DashBoardPageTest.java`; the page object is `DashboardPage.java`. Use the actual names when selecting tests.

## How the framework works

1. Tests extend `BaseTest` and call `initialization()` from `@BeforeMethod`.
2. Initialization calls `BrowserUtil.createDriver()`, maximizes its window, opens the configured login URL, and sets a 20-second shared wait. Chrome is the default in `BrowserUtil`; Edge and Firefox are also supported, plus Safari on macOS.
3. Page objects extend `BaseTest`, inherit the shared `wd`, and initialize private `@FindBy` elements with `PageFactory.initElements(wd, this)`.
4. Navigation methods return the destination page object, for example `LoginPage.clickOnlogin(...)` returns `DashboardPage`.
5. Tests perform assertions and call `teardown()` from `@AfterMethod` to quit the browser. Soft assertion tests must call `assertAll()`.

`SelUtils` exposes static helpers with 15-second explicit waits. Its click helper scrolls and waits, then falls back to a JavaScript click for selected Selenium exceptions. It also implements `WebDriver` and `JavascriptExecutor`, but several instance methods are stubs; use its existing static helpers rather than treating it as a complete driver implementation.

All four leave date-entry methods use `SelUtils.enterDate`. It selects Command+A
on macOS or Ctrl+A on Windows using Selenium's local platform detection, deletes
the selection, enters the date, waits for the input value to match, then presses
Tab so the application can apply its display format on blur. Reuse this helper
for date fields instead of hardcoding `Keys.CONTROL`. Selection, deletion, and
typing are sent in one `sendKeys` call to keep the replacement together. The
application may reformat `2026-09-23` as `2026-23-09` with a `yyyy-dd-mm`
display preference after Tab. Ordinary text fields still use
`SelUtils.clearAndSendKeys`. This assumes local browser sessions; remote
execution would need to use the browser host's platform instead.

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
constructor for Chrome, Firefox, and Edge. Safari uses `SafariDriver` with fresh
default `SafariOptions` after checking that the local platform is macOS, without
WebDriverManager setup. Chrome retains the disabled autofill profile preference;
Firefox, Edge, and Safari use default options. All browsers remain visible by default.

In macOS zsh, set an environment override with `export BROWSER=FIREFOX`; in
PowerShell, use `$env:BROWSER = "FIREFOX"`. Clear it with `unset BROWSER` or
`Remove-Item Env:BROWSER`, respectively. The application environment variables
in the table above use the same shell syntax.

Examples (PowerShell or macOS zsh):

```powershell
mvn "-Dbrowser=EDGE" "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
mvn "-Dapp.base.url=https://your-test-host.example" "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
```

## Commands and validation

Run commands from the repository root. `mvn test` uses the sequential smoke suite
in `testng.xml`: valid login only. Explicit `-Dtest`
selection overrides the suite and may run scenarios that change remote demo data.

```powershell
java -version
mvn -version

# Compile framework and test sources without executing browser tests.
mvn test-compile

# Execute the configured sequential smoke suite.
mvn test

# Select an installed browser for the same smoke suite.
mvn "-Dbrowser=FIREFOX" test
mvn "-Dbrowser=EDGE" test

# macOS only, after enabling Safari WebDriver.
mvn "-Dbrowser=SAFARI" test

# Select a test class explicitly (includes the existing invalid-login scenario).
mvn "-Dtest=LoginPageTest" test

# Select a single test method and browser.
mvn "-Dbrowser=CHROME" "-Dtest=LoginPageTest#validateUserIsAbleToLoginWithValidCredentials" test
```

Browser execution requires the selected browser, access to the demo site, and any driver downloads needed by WebDriverManager. Chrome currently runs with a visible window; no headless option is configured. Maven may also need network access to resolve dependencies.

For compatibility validation, run the existing valid-login scenario separately
for each installed browser on Windows and macOS.
To claim coverage for both Intel and Apple Silicon Macs, run on each architecture.
Verify browser startup, login, and shutdown, then verify that replacing an
existing leave date selects the intended date without submitting an application.
Account for the application's configured date display format after Tab. The
existing leave-search test depends on public demo employee data and record counts;
inspect a failure before attributing it to the OS. Leave-application tests have
the caller/locator issues recorded below and are excluded from the smoke suite.

For code changes, compile first and run relevant existing tests when the environment supports them. Do not add unit tests without explicit user approval for the current task; approval to implement a feature, fix, or refactor does not itself authorize new unit tests. This restriction includes new unit-test classes, new test methods in existing classes, and temporary unit-test harnesses. Use compilation, existing tests, and diff review for validation unless the user approves additional unit tests. For documentation-only changes, check accuracy and the diff without launching browsers. Report which checks ran and distinguish build failures, environment limitations, and scenario failures. Surefire normally writes test results under `target/surefire-reports/`.

## Known gaps reviewed on 2026-10-14

- `PimPage.clickAddEmployeeLink()` is commented out. `AddEmployeePage` is absent, but the commented method is not a compilation blocker. Baseline `mvn test-compile` succeeded on Windows with JDK 21 targeting release 17 on 2026-10-14.
- `wd` and the shared wait are static. The current driver lifecycle is unsuitable for parallel test execution; do not enable TestNG parallelism without isolating browser state.
- Tests depend on shared demo data, hardcoded employee names, and fixed leave dates. Contact updates, employee deletion, and leave applications change remote demo state. Choose a targeted test appropriate to the task and inspect its effects before running a broad suite.
- The 2026-10-14 Windows Chrome leave-search run passed both date-entry value waits, then timed out selecting the hardcoded `Demo Open Source` employee suggestion. The complete scenario is not a passing compatibility check. Chrome and Edge smoke suites passed; macOS, Firefox, and Safari execution remain unverified.
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
