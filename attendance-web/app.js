(function () {
  "use strict";

  var LOCAL_STORAGE_KEY = "attendance-dashboard-config";

  var MONTH_NAMES = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

  // Matrix layout constants. Wider range than the Session 4 Android widget
  // (MIN 4 / MAX 20 weeks) since a browser tab has far more horizontal room
  // than a homescreen widget — this leans closer to GitHub's own contribution
  // graph (roughly a year at full width).
  var ROWS = 7; // Sun..Sat, same convention as the widget
  var MIN_WEEKS = 8;
  var MAX_WEEKS = 53;
  var DEFAULT_WEEKS = 26;
  var CELL = 11;
  var GAP = 3;
  var PAD = 4;
  var MONTH_LABEL_HEIGHT = 16;

  var els = {};

  document.addEventListener("DOMContentLoaded", function () {
    cacheEls();
    wireEvents();
    var cfg = resolveConfig();
    renderSettingsInputs(cfg);
    els.sourceLabel.textContent = cfg.owner + "/" + cfg.repo + " \u00B7 " + cfg.path;
    loadData(true);

    var minutes = Number(cfg.refreshIntervalMinutes) || 15;
    setInterval(function () {
      loadData(false);
    }, Math.max(minutes, 1) * 60 * 1000);
  });

  function cacheEls() {
    [
      "sourceLabel", "lastUpdated", "refreshBtn", "settingsBtn",
      "settingsPanel", "cfgOwner", "cfgRepo", "cfgPath", "cfgBranch",
      "cfgSaveBtn", "cfgResetBtn", "settingsError",
      "loadingState", "errorState", "errorMessage", "errorRetryBtn", "errorSettingsBtn",
      "emptyState", "dataState", "statsRow", "matrixWrap", "matrixSvg", "tooltip",
      "subjectsList"
    ].forEach(function (id) { els[id] = document.getElementById(id); });
  }

  function wireEvents() {
    els.refreshBtn.addEventListener("click", function () { loadData(false); });
    els.settingsBtn.addEventListener("click", toggleSettings);
    els.errorSettingsBtn.addEventListener("click", toggleSettings);
    els.errorRetryBtn.addEventListener("click", function () { loadData(true); });
    els.cfgSaveBtn.addEventListener("click", saveSettings);
    els.cfgResetBtn.addEventListener("click", resetSettings);
    window.addEventListener("resize", debounce(function () {
      if (window.__lastPayload) drawMatrix(window.__lastPayload);
    }, 200));
  }

  function toggleSettings() {
    els.settingsPanel.classList.toggle("hidden");
  }

  function resolveConfig() {
    var defaults = window.ATTENDANCE_CONFIG || {};
    var override = {};
    try {
      var raw = localStorage.getItem(LOCAL_STORAGE_KEY);
      if (raw) override = JSON.parse(raw);
    } catch (e) {
      // corrupt localStorage value — ignore and fall back to defaults
    }
    return {
      owner: override.owner || defaults.owner || "",
      repo: override.repo || defaults.repo || "",
      path: override.path || defaults.path || "attendance.json",
      branch: override.branch || defaults.branch || "main",
      refreshIntervalMinutes: override.refreshIntervalMinutes || defaults.refreshIntervalMinutes || 15
    };
  }

  function renderSettingsInputs(cfg) {
    els.cfgOwner.value = cfg.owner;
    els.cfgRepo.value = cfg.repo;
    els.cfgPath.value = cfg.path;
    els.cfgBranch.value = cfg.branch;
  }

  function saveSettings() {
    var owner = els.cfgOwner.value.trim();
    var repo = els.cfgRepo.value.trim();
    // Session 7: this used to save+reload unconditionally, so a blank
    // owner/repo silently landed you back on "not configured yet" with no
    // explanation of why. Catch it here instead, before it round-trips
    // through a reload.
    if (!owner || !repo) {
      els.settingsError.textContent = "Owner and Repo can't be blank.";
      els.settingsError.classList.remove("hidden");
      return;
    }
    els.settingsError.classList.add("hidden");
    var override = {
      owner: owner,
      repo: repo,
      path: els.cfgPath.value.trim() || "attendance.json",
      branch: els.cfgBranch.value.trim() || "main"
    };
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(override));
    location.reload();
  }

  function resetSettings() {
    localStorage.removeItem(LOCAL_STORAGE_KEY);
    location.reload();
  }

  function buildRawUrl(cfg) {
    var base = "https://raw.githubusercontent.com/" + encodeURIComponent(cfg.owner) +
      "/" + encodeURIComponent(cfg.repo) + "/" + encodeURIComponent(cfg.branch) + "/" + cfg.path;
    // Cache-bust: raw.githubusercontent.com sits behind a CDN that caches by
    // full URL including query string, so a changing param forces a fresh
    // fetch instead of serving a stale copy for its ~5min cache window.
    return base + "?t=" + Date.now();
  }

  function loadData(isInitial) {
    var cfg = resolveConfig();
    if (!cfg.owner || !cfg.repo) {
      showError("Owner/repo not set yet — open Settings and fill them in.");
      return;
    }
    if (isInitial) showLoading();

    fetch(buildRawUrl(cfg), { cache: "no-store" })
      .then(function (res) {
        if (res.status === 404) {
          // Session 7: this 404 is genuinely ambiguous — raw.githubusercontent.com
          // returns it both for "file doesn't exist yet" AND for "repo is
          // private" (it never distinguishes, so an unauthenticated site like
          // this one can't tell which). Say both rather than send someone
          // down the wrong troubleshooting path.
          throw new Error("attendance.json not found at that path/branch, OR the repo is private (raw.githubusercontent.com 404s for both). Publish once from the Android app, and double check the repo is public.");
        }
        if (!res.ok) {
          throw new Error("GitHub returned an error (HTTP " + res.status + ").");
        }
        return res.json();
      })
      .then(function (payload) {
        validatePayload(payload);
        window.__lastPayload = payload;
        renderAll(payload);
      })
      .catch(function (err) {
        if (isInitial) {
          showError(err.message || "Couldn't load attendance.json.");
        } else {
          // Background refresh failure: don't nuke a working dashboard,
          // just flag it quietly next to the timestamp.
          els.lastUpdated.textContent = "Refresh failed \u2014 showing last known data";
        }
      });
  }

  function validatePayload(payload) {
    if (!payload || typeof payload !== "object") throw new Error("attendance.json is malformed.");
    if (!Array.isArray(payload.days) || !Array.isArray(payload.subjects) || !payload.overall) {
      throw new Error("attendance.json doesn't match the expected schema.");
    }
  }

  function showLoading() {
    els.loadingState.classList.remove("hidden");
    els.errorState.classList.add("hidden");
    els.emptyState.classList.add("hidden");
    els.dataState.classList.add("hidden");
  }

  function showError(message) {
    els.loadingState.classList.add("hidden");
    els.errorState.classList.remove("hidden");
    els.emptyState.classList.add("hidden");
    els.dataState.classList.add("hidden");
    els.errorMessage.textContent = message;
  }

  function showEmpty() {
    els.loadingState.classList.add("hidden");
    els.errorState.classList.add("hidden");
    els.emptyState.classList.remove("hidden");
    els.dataState.classList.add("hidden");
  }

  function showData() {
    els.loadingState.classList.add("hidden");
    els.errorState.classList.add("hidden");
    els.emptyState.classList.add("hidden");
    els.dataState.classList.remove("hidden");
  }

  function renderAll(payload) {
    if ((!payload.days || payload.days.length === 0) && (!payload.subjects || payload.subjects.length === 0)) {
      showEmpty();
      return;
    }
    showData();
    els.lastUpdated.textContent = "Updated " + formatRelative(payload.generatedAt);
    renderStats(payload.overall);
    drawMatrix(payload);
    renderSubjects(payload.subjects);
  }

  function renderStats(overall) {
    var pct = overall.percentage === null || overall.percentage === undefined ? "\u2014" : overall.percentage + "%";
    var items = [
      { label: "Attendance", value: pct, cls: "" },
      { label: "Present", value: overall.present, cls: "stat-present" },
      { label: "Absent", value: overall.absent, cls: "stat-absent" },
      { label: "Cancelled", value: overall.cancelled, cls: "stat-cancelled" }
    ];
    els.statsRow.innerHTML = items.map(function (item) {
      return '<div class="stat-card ' + item.cls + '">' +
        '<div class="stat-value">' + item.value + '</div>' +
        '<div class="stat-label">' + item.label + '</div>' +
        '</div>';
    }).join("");
  }

  function pad2(n) { return n < 10 ? "0" + n : "" + n; }

  function toISODate(d) {
    return d.getFullYear() + "-" + pad2(d.getMonth() + 1) + "-" + pad2(d.getDate());
  }

  // Hardcoded (not CSS var()) because SVG presentation attributes resolve
  // custom properties inconsistently on older WebViews/browsers — these are
  // the same hex values as the Android app's colors.xml, kept in sync by hand.
  var STATUS_COLORS = {
    present: "#2E9E5B",
    absent: "#D64545",
    cancelled: "#8A8F98",
    empty: "#E4E6EA"
  };

  function statusColorVar(status) {
    return STATUS_COLORS[status] || STATUS_COLORS.empty;
  }

  function drawMatrix(payload) {
    var statusByDate = {};
    payload.days.forEach(function (d) { statusByDate[d.date] = d.status; });

    var containerWidth = els.matrixWrap.clientWidth || 600;
    var stepPx = CELL + GAP;
    var usable = containerWidth - PAD * 2;
    var weeks = Math.floor(usable / stepPx);
    if (!weeks || isNaN(weeks)) weeks = DEFAULT_WEEKS;
    weeks = Math.max(MIN_WEEKS, Math.min(MAX_WEEKS, weeks));

    var today = new Date();
    today.setHours(0, 0, 0, 0);
    var totalDays = weeks * ROWS;
    var startDate = new Date(today);
    startDate.setDate(startDate.getDate() - (totalDays - 1));
    // Align start to a Sunday so columns line up cleanly with week boundaries.
    startDate.setDate(startDate.getDate() - startDate.getDay());

    var width = PAD * 2 + weeks * stepPx - GAP;
    var height = MONTH_LABEL_HEIGHT + PAD * 2 + ROWS * stepPx - GAP;

    var svgNS = "http://www.w3.org/2000/svg";
    var svg = els.matrixSvg;
    svg.setAttribute("viewBox", "0 0 " + width + " " + height);
    svg.setAttribute("width", width);
    svg.setAttribute("height", height);
    while (svg.firstChild) svg.removeChild(svg.firstChild);

    var cursor = new Date(startDate);
    var lastLabelMonth = -1;
    var col = 0;

    while (cursor <= today) {
      var daysFromStart = Math.round((cursor - startDate) / 86400000);
      col = Math.floor(daysFromStart / ROWS);
      var row = cursor.getDay(); // 0=Sun .. 6=Sat

      if (cursor.getDate() <= 7 && cursor.getMonth() !== lastLabelMonth && cursor >= startDate) {
        lastLabelMonth = cursor.getMonth();
        var label = document.createElementNS(svgNS, "text");
        label.setAttribute("x", PAD + col * stepPx);
        label.setAttribute("y", MONTH_LABEL_HEIGHT - 5);
        label.setAttribute("fill", "#6B7078");
        label.setAttribute("font-size", "10");
        label.textContent = MONTH_NAMES[cursor.getMonth()];
        svg.appendChild(label);
      }

      if (cursor >= startDate) {
        var iso = toISODate(cursor);
        var status = statusByDate[iso] || null;
        var rect = document.createElementNS(svgNS, "rect");
        rect.setAttribute("class", "matrix-cell");
        rect.setAttribute("x", PAD + col * stepPx);
        rect.setAttribute("y", MONTH_LABEL_HEIGHT + PAD + row * stepPx);
        rect.setAttribute("width", CELL);
        rect.setAttribute("height", CELL);
        rect.setAttribute("rx", CELL * 0.3);
        rect.setAttribute("fill", statusColorVar(status));
        rect.setAttribute("data-date", iso);
        rect.setAttribute("data-status", status || "no data");
        rect.addEventListener("mouseenter", onCellHover);
        rect.addEventListener("mouseleave", onCellLeave);
        svg.appendChild(rect);
      }

      cursor.setDate(cursor.getDate() + 1);
    }
  }

  function onCellHover(evt) {
    var rect = evt.currentTarget;
    var date = rect.getAttribute("data-date");
    var status = rect.getAttribute("data-status");
    els.tooltip.textContent = date + " \u2014 " + status;
    els.tooltip.classList.remove("hidden");
    var bbox = rect.getBoundingClientRect();
    els.tooltip.style.left = (bbox.left + bbox.width / 2) + "px";
    els.tooltip.style.top = bbox.top + "px";
  }

  function onCellLeave() {
    els.tooltip.classList.add("hidden");
  }

  function renderSubjects(subjects) {
    if (!subjects || subjects.length === 0) {
      els.subjectsList.innerHTML = '<p class="muted small">No subjects yet.</p>';
      return;
    }
    els.subjectsList.innerHTML = subjects.map(function (s) {
      var total = s.present + s.absent + s.cancelled;
      var presentPct = total ? (s.present / total * 100) : 0;
      var absentPct = total ? (s.absent / total * 100) : 0;
      var cancelledPct = total ? (s.cancelled / total * 100) : 0;
      var pctLabel = s.percentage === null || s.percentage === undefined ? "\u2014" : s.percentage + "%";
      var codeLabel = s.code ? '<span class="subject-code">' + escapeHtml(s.code) + '</span>' : "";
      return '<div class="subject-row">' +
        '<div class="subject-name">' + escapeHtml(s.name) + " " + codeLabel + '</div>' +
        '<div class="subject-pct">' + pctLabel + '</div>' +
        '<div class="subject-bar-track">' +
          '<div class="subject-bar-segment present" style="width:' + presentPct + '%"></div>' +
          '<div class="subject-bar-segment absent" style="width:' + absentPct + '%"></div>' +
          '<div class="subject-bar-segment cancelled" style="width:' + cancelledPct + '%"></div>' +
        '</div>' +
        '<div class="subject-counts">' + s.present + ' present \u00B7 ' + s.absent + ' absent \u00B7 ' + s.cancelled + ' cancelled</div>' +
        '</div>';
    }).join("");
  }

  function escapeHtml(str) {
    return String(str).replace(/[&<>"']/g, function (c) {
      return { "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c];
    });
  }

  function formatRelative(isoTimestamp) {
    try {
      var then = new Date(isoTimestamp);
      var diffMs = Date.now() - then.getTime();
      var mins = Math.round(diffMs / 60000);
      if (mins < 1) return "just now";
      if (mins < 60) return mins + "m ago";
      var hours = Math.round(mins / 60);
      if (hours < 24) return hours + "h ago";
      var days = Math.round(hours / 24);
      return days + "d ago";
    } catch (e) {
      return isoTimestamp;
    }
  }

  function debounce(fn, wait) {
    var t;
    return function () {
      clearTimeout(t);
      var args = arguments;
      t = setTimeout(function () { fn.apply(null, args); }, wait);
    };
  }
})();
