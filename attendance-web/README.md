# Attendance Dashboard — Session 6: Vercel Site

**Session 7 update:** two small fixes, not a rebuild (see repo-root
`README.md` for the full session 7 summary). The Settings panel now
validates owner/repo before saving instead of silently reloading into a
broken "not configured" state, and the 404 error message now mentions that
`raw.githubusercontent.com` returns 404 for both "file doesn't exist" and
"repo is private" — we hit that exact confusion while testing the Session 6
deploy, and there was no way for the site itself to tell the two apart.

---

Checkpoint 6 of 7. New top-level folder in this zip: `attendance-web/` — a
static site (no framework) that fetches `attendance.json` published by the
Session 5 Android "Publish" tab and renders the same dot-matrix + summary
stats. `attendance-android/` is carried over unchanged from Session 5.

## Why plain HTML+JS, not Next.js

The README left this "not yet decided." Deciding now: plain static
HTML/CSS/JS, no build step, no dependencies. Reasoning:
- The whole job is "fetch one JSON file, render it" — no routing, no
  server-side rendering, nothing Next.js buys you here.
- Vercel deploys a static folder with zero config — no `package.json`,
  no build command to get wrong.
- One less thing to break between sessions with no way for me to `npm
  install` and verify it here.

## Files in `attendance-web/`

- `index.html` — page structure: topbar, collapsible settings panel, stats
  cards, activity matrix, per-subject list, loading/error/empty states.
- `style.css` — styled with the same palette as the Android app
  (`colors.xml`): green `#2E9E5B` present, red `#D64545` absent, grey
  `#8A8F98` cancelled, `#E4E6EA` empty.
- `app.js` — all logic: resolves config, fetches the JSON, draws the SVG
  matrix, renders stats/subjects, handles the three non-happy-path states.
- `config.js` — **edit this before deploying.** Sets the default
  owner/repo/path/branch the site fetches from.

## Config: two layers

1. `config.js` — baked-in defaults, edited by you and committed/deployed.
2. Settings panel (gear icon) — overrides `config.js` via `localStorage`,
   per-browser, no redeploy needed. Useful if you ever want to point the
   same deployed URL at a different repo temporarily. "Reset to defaults"
   clears the override.

## Matrix rendering — kept consistent with the widget, sized for the web

Reuses the Session 5 `AttendanceAggregation` day-status rule indirectly:
that rule already ran on-device when `attendance.json` was exported, so
`days[].status` in the JSON *is* the aggregated one-status-per-day value —
the site just colors it in, no re-aggregation needed here.

Layout constants differ from the Android widget on purpose — a browser tab
has far more width than a homescreen widget:

| | Widget (Session 4) | Web (this session) |
|---|---|---|
| weeks shown | 4–20, default 12 | 8–53, default 26 |
| responsive to | widget's `OPTION_APPWIDGET_MIN_WIDTH` | window resize (debounced) |

Both use the same Sun-top-to-Sat-bottom, 7-row grid and the same
absent-red / present-green / cancelled-grey / empty-grey coloring. Colors
are hardcoded hex (not CSS `var()`) inside the SVG — presentation
attributes resolve custom properties inconsistently on older browsers, so
this avoids a class of "matrix renders as black squares" bugs. If
`colors.xml` ever changes, `STATUS_COLORS` in `app.js` needs a matching
manual edit — the two aren't wired together.

## Non-happy-path states handled

- **No owner/repo configured** — points at Settings, no fetch attempted.
- **404** — "attendance.json not found yet, publish once from the Android
  app first" (the most likely first-run state for anyone testing this
  fresh).
- **Other HTTP errors / malformed JSON** — plain-English message + Retry
  and Check Settings buttons.
- **Valid JSON, but empty** (no days, no subjects) — distinct empty state,
  not treated as an error.
- **Background auto-refresh failure** (not the first load) — doesn't wipe
  a working dashboard; just flags "Refresh failed — showing last known
  data" next to the timestamp and keeps the stale-but-valid view up.

## Things worth knowing

- **Cache-busting**: every fetch appends `?t=<timestamp>` because
  `raw.githubusercontent.com` sits behind a CDN that caches by full URL —
  a static path would keep serving a ~5-minute-stale copy after you
  publish from the app.
- **No CORS workaround needed** — `raw.githubusercontent.com` sends
  `Access-Control-Allow-Origin: *`, so this fetches directly from the
  browser with no proxy/serverless function in between, keeping "no
  server that has to stay online" true for this piece too.
- **Public by design, like the rest of this architecture** — the site has
  no login and the JSON is fetched unauthenticated, so anyone with the
  URL (and the repo/path, if the repo is public) can see the dashboard.
  If that's not wanted, the GitHub repo itself needs to be private and
  this approach stops working (raw file needs a token then) — out of
  scope for this session; flagging it in case it matters to you.
- **Not verified against a real deploy** — no network access on my end
  to actually push this to Vercel or fetch a real `attendance.json`; the
  date/column math was checked locally with Node against fixed sample
  dates, but the live fetch path, CORS behavior, and Vercel's static
  detection are unverified. Worth confirming for real before trusting it.

## Try it

1. Edit `config.js`: set `owner`, `repo` to wherever your Session 5 app is
   publishing `attendance.json`.
2. Deploy `attendance-web/` to Vercel: `vercel` CLI from inside the folder,
   or connect the repo in the Vercel dashboard and set it as the project
   root — no build command or output directory needed, it's static.
3. Open the deployed URL. First visit before ever publishing from the app
   should show the "not found yet" error state, not a crash.
4. Publish once from the Android app's Publish tab, then hit Refresh on
   the site — matrix and stats should populate.
5. Try the gear icon: change owner/repo to something invalid, confirm the
   error state and Retry/Settings buttons work; Reset to defaults to get
   back.
6. Resize the browser window (or check on mobile) — matrix should redraw
   with a different week count rather than overflowing or staying tiny.

## Next session (Session 7 — Polish & Merge Edge Cases)

Handle Syncthing `*.sync-conflict-*.jsonl` files, empty states and error
handling across all three surfaces (some of this site's states are already
built — Session 7 should sanity-check them rather than rebuild), then a
final combined zip + short usage doc covering all three surfaces
end-to-end.
