# College Attendance Tracker — Final Checkpoint (after Session 7)

7-session plan, complete. Cross-device attendance tracking with no
always-on server. **Start with `USAGE.md`** for setup order and
troubleshooting across all three surfaces — this file is just the
changelog for this session.

## Folders

- `attendance-android/` — Kotlin app (Room DB, Subjects/Attendance/Sync/
  Publish tabs, homescreen widget).
- `attendance-web/` — static Vercel dashboard, fetches `attendance.json`
  from GitHub and renders it.
- `USAGE.md` — **read this first.** End-to-end setup, day-to-day use,
  troubleshooting (including two real issues hit while testing Sessions
  6–7: private-repo 404s and Vercel Root Directory).

(The Session 1 Python/`rich` terminal tool isn't in this zip — it wasn't
part of the handoff from Session 3 onward, and Session 7 couldn't touch
what it never received. See `USAGE.md`'s last section.)

## What changed this session

**Android — sync-conflict handling (the main ask for this session):**
- `SyncFolderManager` now explicitly detects `*.sync-conflict-*.jsonl`
  files (they were already being read before — they still matched
  `changes_*.jsonl` — just never called out separately) and can archive
  them (rename to `archived_...`, not delete) once merged, via a new
  **Clean up conflict files** button on the Sync tab.
- **Real fix, not just visibility:** duplicate `(subjectId, date,
  deviceId)` events arriving from a conflict file used to resolve by
  whichever file happened to be read first (`INSERT OR IGNORE`). Now
  `AttendanceRepository.mergeAttendanceEvent` compares `createdAt` and
  keeps whichever mark is actually more recent. This was a genuine
  correctness bug, found while implementing the conflict-file work, not
  a cosmetic one.
- Sync tab now reports conflict-file count and a created/updated split
  in the summary text, and calls out repeated conflicts as a likely sign
  of a duplicated device ID (e.g. a restored backup) rather than staying
  silent about it.

**Android — widget crash-hardening (found during the error-handling
pass):** `WidgetUpdater.updateWidget` had no error handling at all — a
bad DB read or a launcher-supplied width value would throw with nothing
catching it, in a coroutine with no exception handler. Now wrapped; on
failure the widget shows "Couldn't load attendance data" instead of
taking the whole app down.

**Web — two small fixes, not a rebuild** (per the Session 6 README's own
note to sanity-check rather than rebuild): Settings panel now validates
owner/repo before saving instead of silently reloading into "not
configured"; the 404 error message now explains that
`raw.githubusercontent.com` 404s for both "not published yet" and
"repo is private" — the exact confusion hit while testing the real
deploy between sessions.

**Not touched, deliberately:** subject-deletion sync, cross-*device*
(not cross-file) conflict resolution, the terminal CLI. All three are
listed with reasons in `USAGE.md`'s Known Limitations / About the
terminal CLI sections rather than attempted here — each is either a
larger design problem than fits one session, or source Claude was never
given.

## Session status

1. ✅ Schema + terminal viewer
2. ✅ Android foundation
3. ✅ Syncthing sync layer
4. ✅ Homescreen widget
5. ✅ GitHub publish
6. ✅ Vercel dashboard
7. ✅ **Polish & merge edge cases — this checkpoint, final session**

## Before you trust this end-to-end

Same caveat as every session: no build/network access here, so none of
this session's changes have been run against a real device pair yet.
Worth a real test — mark the same subject+date differently on two
devices sharing one Syncthing folder (or, easier, just wait for a
genuine conflict to occur naturally) and confirm the Sync tab's summary
and Clean up button behave as described.
