# College Attendance Tracker — Checkpoint: weekend holidays

Saturdays and Sundays are now always holidays by default. **Start with
`USAGE.md`** for full setup; this file covers what changed here.

## What changed

New file: `data/HolidayRules.kt` — a single small object that decides
"is this date a holiday," combining two sources: an explicit DB row
(unchanged, still user-toggled via the "Mark/Undo holiday" button) and a
hardcoded rule (`isWeekend`) — every Saturday and Sunday, always, no
database row involved.

This was previously decided independently in two places —
`AttendanceViewModel.todayHoliday` and `CalendarViewModel.
selectedDateHoliday` — each querying the holidays table on its own. Both
now go through `HolidayRules` instead, so there's one definition of
"holiday," not two that could quietly drift apart the next time either
screen changes.

**The holiday toggle button now hides itself on weekends.** Since the
weekend rule doesn't touch the database, tapping "Undo holiday" on a
Saturday would try to delete a row that was never there — the date would
just keep showing as a holiday regardless, which looks like a broken
button. Rather than ship that, the button is hidden entirely on
Saturdays/Sundays on both the Attendance and Calendar tabs; the holiday
message still shows, there's just nothing to tap. `toggleTodayHoliday`/
`toggleSelectedDateHoliday` also guard against this internally now (not
just at the UI layer), in case anything ever calls them without going
through the button.

**Calendar grid:** every Saturday/Sunday cell now renders in the holiday
color automatically, not just explicitly-marked dates.

**Known gap, not built:** no way to override a specific weekend back to
a working day (e.g. a compensatory Saturday class). The rule is
unconditional. If that's needed, it's a real follow-up — a plausible
design is a second kind of DB row meaning "this weekend is NOT a
holiday, despite the rule" — but that's more than what was asked for
here, so it's flagged rather than guessed at and half-built.

## Two type changes if you're reading the diff

`AttendanceViewModel.todayHoliday` (`StateFlow<HolidayEntity?>`) is now
`isTodayHoliday` (`StateFlow<Boolean>`) plus a new
`isTodayHolidayOverridable` (`StateFlow<Boolean>`) for the button's
visibility. Same rename/split for `CalendarViewModel.
selectedDateHoliday` → `isSelectedDateHoliday` +
`isSelectedDateHolidayOverridable`. Neither Fragment ever used anything
from `HolidayEntity` beyond null-checking it, so this is a
straightforward simplification, not a functional change beyond the
weekend rule itself.

## Before you trust this

Same caveat as always — no build access on my end. Specifically worth
checking: open the Attendance tab on an actual Saturday/Sunday (or jump
the emulator's clock there like your earlier testing) and confirm the
holiday message shows with no toggle button, then check a weekday still
shows the toggle normally.
