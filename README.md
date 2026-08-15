# College Attendance Tracker — Final Checkpoint (after Session 7 + a bugfix)

7-session plan, complete, plus one post-session fix found through your own
manual clock-jump testing. Cross-device attendance tracking with no
always-on server. **Start with `USAGE.md`** for setup order and
troubleshooting across all three surfaces — this file is just the
changelog.

## Post-session fix: stale "today" in the Attendance tab

Flagged as a likely gap while answering "how do I test date rollover,"
then confirmed for real when you jumped the emulator's clock forward
twice in a row (15→16, then 16→17) without a restart in between — the
second jump kept marking against the 16th while the UI quietly showed
the 17th.

Root cause: `AttendanceViewModel.today` was a `val` computed once when
the ViewModel was created. A ViewModel survives backgrounding — it's
only destroyed on process death — so it never noticed the day had moved
on unless something recreated it. Your first date jump happened to
trigger a fresh ViewModel (probably the app got killed by the clock
change); the second one didn't, and the bug showed.

Fix: `today` is now a `StateFlow`, rechecked via `refreshToday()` on
every `onResume()` of the Attendance tab — no restart needed, no full
day needing to pass, just navigating back to the tab is enough — and the
row list (`rows`) re-subscribes to the correct date via `flatMapLatest`
instead of being locked to whichever date the ViewModel was born on.

**Not touched, and shouldn't need to be:** the widget already recomputes
`LocalDate.now()` fresh on every refresh, so it was never affected by
this. If the widget didn't reflect your test data, that's more likely
because the underlying attendance record never got written in the first
place (the actual bug above) than a widget-side staleness issue — worth
re-running your test now that marking is fixed, and use the widget's own
refresh icon rather than waiting on the 15-minute timer, since manually
jumping the system clock can disrupt WorkManager's scheduling on an
emulator (a platform quirk, not something in this code).

## Folders

- `attendance-android/`
- `attendance-web/`
- `USAGE.md` — start here.

## Before you trust this

Same caveat as every checkpoint: no build/network access here, so this
fix hasn't been run through an actual build. Re-run your exact test —
mark today, jump the clock forward twice in a row without restarting in
between, confirm each day's mark actually sticks and the header date
tracks correctly on tab return.
