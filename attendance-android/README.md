# Attendance App — Session 7: Polish & Merge Edge Cases

See the repo-root `README.md` for what's new this session (sync-conflict
handling, widget crash-hardening). The Session 5 notes below still describe
the Publish tab accurately — nothing in this session changed it.

---

# Attendance App — Session 5: GitHub Publish

Checkpoint 5 of 7. Builds on your working Session 4 project (Room DB,
Subjects/Attendance/Sync tabs, homescreen widget) — this session adds a
fourth **Publish** tab that exports an `attendance.json` summary and pushes
it to a GitHub repo via the Contents API, ready for the Session 6 Vercel
site to fetch. No changes to the Subjects/Attendance/Sync screens, the sync
engine, or the widget's behavior (its rendering logic moved, see below, but
what it draws is unchanged).

## What's new in this zip

- **New Publish tab**: enter a fine-grained GitHub PAT, repo owner, repo
  name, file path (defaults to `attendance.json`), and branch (defaults to
  `main`); "Save settings" persists them, "Publish now" exports and pushes.
  Shows created-vs-updated, a link to the raw file URL, and a last-published
  timestamp.
- `data/publish/GitHubSettingsManager.kt` — persists the PAT and repo
  config in `EncryptedSharedPreferences` (AES-256), not plain
  `SharedPreferences` — a PAT sitting in an unencrypted XML file felt like
  the wrong default even for a personal single-user app.
- `data/publish/AttendanceJsonExporter.kt` — builds the `attendance.json`
  payload from the local Room DB: overall present/absent/cancelled counts,
  per-subject summaries, and a `days` array (one entry per day that has at
  least one record — see schema below). Exports **full history**, not a
  rolling window — this runs once per tap, not on a timer, so the cost is
  fine, and Session 6's dashboard likely wants the complete picture.
- `data/publish/GitHubPublisher.kt` — talks to GitHub's REST Contents API.
  Always does a fresh `GET` for the file's current `sha` immediately before
  every `PUT`, rather than caching a `sha` from a previous publish, so an
  out-of-band edit to the file on GitHub won't cause a stale-sha conflict.
  Handles first-push (no `sha`, file created) vs update-push (`sha`
  required) automatically, and turns GitHub's HTTP error codes (401/403/
  404/409/422) into a plain-English message in the UI instead of a raw
  status code.
- `data/AttendanceAggregation.kt` — the day-status aggregation rule (absent
  beats present beats cancelled when a day has mixed marks) used to live
  only inside the widget's bitmap renderer; pulled it out here so the
  exported JSON's `days` array and the widget's dots are guaranteed to
  agree on what a given day means, rather than two copies of the same
  logic quietly drifting apart over later sessions. `AttendanceMatrixRenderer`
  now calls this instead of having its own private copy — its actual output
  is unchanged.
- Two additive one-shot DAO queries (`getAllSnapshot`,
  `getSubjectSummariesSnapshot`) — nothing in the existing Flow-based
  screens changed.
- New dependencies in `app/build.gradle.kts`: `okhttp:4.12.0` (HTTP calls)
  and `androidx.security:security-crypto:1.1.0-alpha06` (encrypted PAT
  storage). `INTERNET` permission added to the manifest — first time this
  app has needed network access.

## `attendance.json` schema (what Session 6 will fetch)

```json
{
  "generatedAt": "2026-08-15T10:32:00Z",
  "deviceId": "android-abcd1234",
  "overall": { "present": 42, "absent": 5, "cancelled": 2, "percentage": 89 },
  "subjects": [
    { "name": "Data Structures", "code": "CS201", "present": 10, "absent": 1, "cancelled": 0, "percentage": 90 }
  ],
  "days": [
    { "date": "2026-06-01", "status": "present" },
    { "date": "2026-06-02", "status": "absent" }
  ]
}
```

`days` only includes dates with at least one record — treat a missing date
as "no data" client-side, same as the widget does.

## Design decisions worth knowing about

**PAT and repo config live in one `EncryptedSharedPreferences` file**, not
split across encrypted (PAT) and plain (everything else) storage — simpler
to reason about, and owner/repo/path/branch aren't sensitive enough to
justify the extra complexity of splitting them out.

**Branch is a required field, defaulted to `main`.** Fetching the repo's
actual default branch would need an extra API call (`GET /repos/{owner}/
{repo}`) and a slightly broader token permission — not worth it for this
session. If your repo's default branch isn't `main`, change the field.

**GET-then-PUT is not atomic.** There's a small window between reading the
current `sha` and writing with it where someone else (or another of your
own devices, publishing at the exact same moment) could change the file
first, in which case GitHub returns 409 and the UI shows a "try again"
message. For one person publishing their own attendance, this is an
acceptable trade for not building a full retry-with-backoff loop this
session.

**Save-then-publish on one tap.** Tapping "Publish now" saves whatever's
currently typed in the form first, so you don't have to remember to hit
"Save settings" separately before every publish.

## Known limitations carried over (unchanged from Session 3/4)

Same-subject-same-date conflicts across devices still keep both rows
rather than resolving to one (Session 7). Subject deletion is still
local-only, not synced. The widget's coloring-rule assumption flagged last
session is now shared code (see above) but still unverified against the
actual Session 1 CLI output.

## Not built this session (by design, out of scope)

No automatic/scheduled publish — it's a manual button tap only. No repo
picker or GitHub OAuth flow — the PAT is pasted in directly. No validation
that the PAT actually has the right scopes before you hit publish; a bad
token just surfaces as a 401/403 after the fact.

## Try it

1. Rebuild in Android Studio (two new dependencies — Gradle sync should
   pick them up automatically). You'll need network access on the test
   device/emulator.
2. On GitHub, create a fine-grained PAT scoped to one repo (an empty repo
   is fine to test with) with **Contents: Read and write** permission.
3. Open the new **Publish** tab, paste the token, fill in owner/repo, leave
   path as `attendance.json` and branch as `main` (or your repo's actual
   default branch), tap **Save settings**.
4. Make sure you have at least one attendance record marked (Attendance
   tab), then tap **Publish now**. First time should say "Created
   attendance.json on GitHub" with a raw-file link.
5. Check the file actually landed on GitHub, then mark another attendance
   record and publish again — should say "Updated" this time, not
   "Created".
6. Try an obviously wrong token or repo name to confirm the error message
   is readable rather than a raw stack trace.

## Next session (Session 6)

Vercel dashboard: Next.js (or static HTML+JS) site that fetches
`attendance.json` from `raw.githubusercontent.com`, renders the dot-matrix
+ summary stats using the schema above, and deploys.
