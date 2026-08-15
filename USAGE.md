# Attendance Tracker — Usage Guide (after Session 7)

One attendance dataset, synced across devices with no always-on server.
Three surfaces: a terminal viewer, an Android app (with a homescreen
widget), and a public web dashboard. This doc covers setup order,
day-to-day use, and troubleshooting for all three — written from the
actual round-trip testing done across Sessions 6–7, not just the design.

## What each piece does

- **Terminal CLI** (Python, `rich`) — local dot-matrix view of your own
  device's attendance.db. *Not included in this zip* — see the note at
  the bottom.
- **Android app** — where you actually mark attendance day to day.
  Subjects tab, Attendance tab, Sync tab, Publish tab, plus a homescreen
  widget.
- **Web dashboard** — public, read-only, shows whatever was last
  published from the Android app.

## First-time setup, in order

1. **Install Syncthing** on every device you want attendance synced
   across, and share one folder between them. This is external to the
   app — Syncthing itself isn't part of this project.
2. **Install the Android app** (`attendance-android/`, build in Android
   Studio — Gradle 8.4 + JVM 17, see that folder's own README for
   specifics). Add your subjects (Subjects tab), then start marking
   attendance day to day (Attendance tab).
3. **Point the app at your Syncthing folder** (Sync tab → Choose sync
   folder). Do this on every device. Each device writes its own
   `changes_<deviceId>.jsonl` there and reads everyone else's.
4. **Add the homescreen widget** (long-press homescreen → Widgets →
   Attendance) if you want the dot-matrix visible without opening the
   app. It reads your local DB only — it does not trigger a sync.
5. **Set up GitHub publishing** (Publish tab): a fine-grained PAT scoped
   to one repo with Contents: Read and write, plus owner/repo/path/
   branch. **Make sure that repo is public** if you want the web
   dashboard to work — see Troubleshooting below, this bit it us during
   testing.
6. **Deploy the web dashboard** (`attendance-web/`): edit `config.js`
   with your owner/repo, then deploy to Vercel with **Root Directory set
   to `attendance-web`** and every build/install/output toggle left off
   — it's static files, nothing to build.

## Day to day

- Mark attendance in the Android app as normal.
- Tap **Sync now** (Sync tab) when you want to pull in marks made on
  your other devices. This is manual, not automatic — nothing runs in
  the background except the widget's own periodic local refresh.
- Tap **Publish now** (Publish tab) when you want the web dashboard to
  reflect your latest data. Also manual.
- The widget updates itself locally every ~15 minutes (WorkManager) or
  on a manual tap — it doesn't need Sync or Publish to have run first,
  it just reads whatever's already in the local DB.

## Troubleshooting

**Web dashboard shows "attendance.json not found... OR the repo is
private."** This is the single most likely first problem. Both cases
produce the exact same 404 from `raw.githubusercontent.com`, and an
unauthenticated site can't tell them apart. Check, in order: (1) has
Publish actually been tapped at least once, (2) is the GitHub repo
public — open it in a browser logged *out* of GitHub, or check for a
"Private" label. Making a private repo public is the fix if that's it;
there's no way to keep it private and have this static site read it
without adding a server, which defeats the "no server" design goal.

**Vercel deploy shows a 404 or the default Vercel welcome page.** Root
Directory almost certainly isn't set to `attendance-web` — the repo has
both `attendance-android/` and `attendance-web/` at the top level, and
Vercel needs to be told which one is the site.

**Sync tab shows "N were sync-conflict copies."** New in Session 7 —
Syncthing occasionally creates a `*.sync-conflict-*.jsonl` file when it
sees the same filename change on two sides at once. This mostly
shouldn't happen given the architecture (each device only writes its own
file), so seeing it repeatedly is worth investigating — the most likely
cause is two physical devices ending up with the same internal device ID
(e.g. an Android backup/restore that copied the app's local storage onto
a second phone). Nothing is lost either way — the app already merges a
conflict file's events safely, and duplicate marks are now resolved by
which one is actually more recent rather than whichever file happened to
be read first. Tap **Clean up conflict files** once you're not worried
about it, to stop them being re-read on every sync.

**Widget shows "Couldn't load attendance data."** New fallback added in
Session 7 — previously an unexpected error here (rare, but a bad DB read
or similar) had no handling and could crash the app. Now it degrades to
this message instead. Tap the widget to open the app and check the
Attendance/Sync tabs directly; if this persists, it's worth filing as a
real bug rather than just tapping past it.

## Known limitations (final list, not fixed by design or by choice)

- **Subject deletion doesn't sync.** Deleting a subject is local-only —
  it won't disappear on your other devices. No tombstone/delete-event
  type was built for this.
- **Same subject+date marked differently from two *different* devices**
  (not a sync-conflict-file duplicate — a genuine two-device
  disagreement) still keeps both rows rather than resolving to one. The
  app shows your local device's own mark as "today's status" in that
  case. Real cross-device conflict resolution is a bigger design problem
  than fit in any single session here.
- **Publish is manual, not automatic.** No scheduled publish — you have
  to remember to tap it.
- **No GitHub OAuth / repo picker.** The PAT is pasted in directly, and
  there's no validation that it has the right scope until a publish
  actually fails.
- **The web dashboard is public by design** — no login, fetched
  unauthenticated. Anyone with the URL (and a public repo) can see it.

## About the terminal CLI

The Session 1 Python/`rich` terminal tool was never included in the zips
handed off from Session 3 onward, so it isn't in this combined zip either
and none of the Session 7 polish work touched it — I don't have its
source to have touched. If you still have it, it's worth folding into a
proper combined package by hand; the architecture doc describes it as a
dot-matrix viewer over the same schema this Android app uses, reading the
same `changes_*.jsonl` sync log, but I can't confirm anything more
specific than that (its own error handling, empty-state behavior, etc.)
without seeing the actual code.
