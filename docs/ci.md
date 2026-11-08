# GitHub Actions

The workflows use `windows-latest`, Temurin Java 11, Maven caching and Chrome.
PowerShell runs the commands. Tests run sequentially against DEV. No workflow has
a `workflow_dispatch` trigger. No repository secrets are needed for the public
demo configuration.

## PR checks

`.github/workflows/pr-check.yml` runs on PR creation, new commits/force pushes,
edits and reopenings, for every target branch and changed path. Draft PRs also
run. The default checkout tests the PR merge commit. New activity cancels an
older run for the same PR.

All framework and test sources are compiled, then `testng-pr.xml` runs four
existing scenarios: valid login, invalid login, Dashboard-to-PIM navigation,
and nonexistent-employee search/reset. These scenarios do not change demo data.
The job times out after 15 minutes. The stable check name is `PR smoke tests`;
configure it as a required check in the repository's branch rules if desired.
Adding the workflow alone does not enforce merge protection.

## Scheduled regression

`.github/workflows/regression.yml` is automatic only. GitHub schedules it daily
at **04:00 America/Toronto**, following local daylight-saving changes. A small
calendar check runs the test job every second local calendar day, anchored to
**2026-11-08**: November 8, 10, 12, and so on, including across month/year
boundaries. Off days skip the test job before checkout, dependency setup or
browser execution. This avoids the uneven month boundaries of a day-of-month
`*/2` cron expression. The gate uses the Toronto date when it executes.

The workflow must be merged into the repository's default branch to receive
scheduled events. It tests that branch. GitHub can delay or drop scheduled
events, so 04:00 is the requested start, not an exact execution guarantee.
Public repositories may have schedules disabled after 60 days without activity.
See [GitHub's schedule documentation](https://docs.github.com/en/actions/reference/workflows-and-actions/events-that-trigger-workflows#schedule).

`testng-regression.xml` explicitly selects **19 existing scenarios** across all
ten test classes. The test job has a 60-minute timeout. Scheduled runs share a
concurrency group and do not cancel an active regression, allowing cleanup to
finish. There is no browser matrix or parallel TestNG execution.

Employee creation, contact update, deletion, default employee search/edit and
the empty-leave search use unique generated employees. Cleanup checks the
generated employee ID, first name and last name before deleting. It runs before
browser shutdown, including after failures. Read-only employee search/navigation
tests still accept an explicit `empName`; in contact/deletion scenarios that
parameter supplies the generated record's first name, and never selects an
existing record to change. Candidate creation uses its existing identity-checked cleanup.

### Explicit regression exclusions

Six of the 25 existing methods are intentionally outside unattended coverage:

| Class / method | Prerequisite for inclusion |
| --- | --- |
| `LeavePageTest.applyLeave` | Controlled leave entitlement, current dates and cancellation of the created request |
| `LeavePageTest.applyLeaveForMoreDaysThanAvailable` | Controlled leave balance and a deterministic date range |
| `MyInfoPageTest.validateUserIsAbleToUpdatePersonalDetails` | An owned profile or reliable restoration of the shared user's original values |
| `MyInfoPageTest.validateUserIsAbleToDownloadProfileImage` | A test-owned attachment, isolated download directory and a wait for completed download |
| `MyInfoPageTest.validateUserIsAbleToUploadProfileImage` | A portable fixture, verification in the application and attachment cleanup |
| `ReportPageTest.validateUserIsAbleToEditAndSaveTheReport` | A test-owned report or restoration of the shared report's complete original configuration |

These exclusions are visible in suite review, rather than runtime skips or
ignored failures. Existing `testng.xml` selection remains unchanged for local
users; it still includes data-changing leave scenarios. Explicit `-Dtest`
selection also overrides suites and can select excluded scenarios.

## Results and local commands

Both workflows upload `target/surefire-reports/` and `target/artifacts/` after
success or failure, keeping artifacts for 14 days. The latter includes Extent
HTML reports, logs and failure screenshots. Maven test failures fail the job;
there is no automatic retry or ignored exit status.

From the repository root (PowerShell):

```powershell
mvn test-compile
mvn "-Dapp.env=DEV" "-Dbrowser=CHROME" "-Dtestng.suite=testng-pr.xml" test
mvn "-Dapp.env=DEV" "-Dbrowser=CHROME" "-Dtestng.suite=testng-regression.xml" test
```

Omitting `-Dtestng.suite` retains the existing `testng.xml` default.
