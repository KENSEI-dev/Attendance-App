// Edit these before deploying, or override at runtime via the Settings panel
// (which persists to localStorage — handy if you want to point the same
// deployed site at a different repo without rebuilding).
window.ATTENDANCE_CONFIG = {
  owner: "your-github-username",
  repo: "your-repo-name",
  path: "attendance.json",
  branch: "main",
  // How often the page re-fetches attendance.json in the background, in minutes.
  refreshIntervalMinutes: 15,
};
