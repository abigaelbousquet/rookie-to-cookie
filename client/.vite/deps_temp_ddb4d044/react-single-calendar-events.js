import {
  require_react
} from "./chunk-E6IAQ5KI.js";
import {
  __commonJS
} from "./chunk-UXIASGQL.js";

// node_modules/react-single-calendar-events/lib/SingleCalendarEvents.js
var require_SingleCalendarEvents = __commonJS({
  "node_modules/react-single-calendar-events/lib/SingleCalendarEvents.js"(exports, module) {
    module.exports = function(e) {
      var t = {};
      function n(a) {
        if (t[a])
          return t[a].exports;
        var r = t[a] = { i: a, l: false, exports: {} };
        return e[a].call(r.exports, r, r.exports, n), r.l = true, r.exports;
      }
      return n.m = e, n.c = t, n.d = function(e2, t2, a) {
        n.o(e2, t2) || Object.defineProperty(e2, t2, { enumerable: true, get: a });
      }, n.r = function(e2) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(e2, Symbol.toStringTag, { value: "Module" }), Object.defineProperty(e2, "__esModule", { value: true });
      }, n.t = function(e2, t2) {
        if (1 & t2 && (e2 = n(e2)), 8 & t2)
          return e2;
        if (4 & t2 && "object" == typeof e2 && e2 && e2.__esModule)
          return e2;
        var a = /* @__PURE__ */ Object.create(null);
        if (n.r(a), Object.defineProperty(a, "default", { enumerable: true, value: e2 }), 2 & t2 && "string" != typeof e2)
          for (var r in e2)
            n.d(a, r, (function(t3) {
              return e2[t3];
            }).bind(null, r));
        return a;
      }, n.n = function(e2) {
        var t2 = e2 && e2.__esModule ? function() {
          return e2.default;
        } : function() {
          return e2;
        };
        return n.d(t2, "a", t2), t2;
      }, n.o = function(e2, t2) {
        return Object.prototype.hasOwnProperty.call(e2, t2);
      }, n.p = "", n(n.s = 0);
    }([function(e, t, n) {
      "use strict";
      Object.defineProperty(t, "__esModule", { value: true });
      var a, r = function(e2, t2) {
        if (Array.isArray(e2))
          return e2;
        if (Symbol.iterator in Object(e2))
          return function(e3, t3) {
            var n2 = [], a2 = true, r2 = false, o2 = void 0;
            try {
              for (var i2, l = e3[Symbol.iterator](); !(a2 = (i2 = l.next()).done) && (n2.push(i2.value), !t3 || n2.length !== t3); a2 = true)
                ;
            } catch (e4) {
              r2 = true, o2 = e4;
            } finally {
              try {
                !a2 && l.return && l.return();
              } finally {
                if (r2)
                  throw o2;
              }
            }
            return n2;
          }(e2, t2);
        throw new TypeError("Invalid attempt to destructure non-iterable instance");
      }, o = n(1), i = (a = o) && a.__esModule ? a : { default: a };
      n(2);
      t.default = function(e2) {
        var t2 = (0, o.useState)(0), n2 = r(t2, 2), a2 = n2[0], l = n2[1], d = (0, o.useState)(e2.options), c = r(d, 2), s = c[0], u = (c[1], (0, o.useState)([])), p = r(u, 2), f = p[0], v = p[1], g = (0, o.useState)(""), h = r(g, 2), b = h[0], y = h[1], m = (0, o.useState)(""), x = r(m, 2), E = x[0], S = x[1], w = (0, o.useState)({ prevDays: [], currentDays: [], nextDays: [] }), C = r(w, 2), z = C[0], k = C[1], M = (0, o.useState)(/* @__PURE__ */ new Date()), _ = r(M, 2), D = _[0], N = _[1], O = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"], F = (0, o.useState)(""), Y = r(F, 2), j = Y[0], T = Y[1], H = (0, o.useState)(""), P = r(H, 2), R = P[0], A = P[1], B = (0, o.useState)(""), I = r(B, 2), q = I[0], W = I[1], X = (0, o.useState)(e2.options.tooltipPosition), J = r(X, 2), L = J[0], U = J[1], G = (0, o.useState)(false), K = r(G, 2), Q = K[0], V = K[1], Z = (0, o.useState)(0), $ = r(Z, 2), ee = $[0], te = $[1], ne = (0, o.useState)(0), ae = r(ne, 2), re = ae[0], oe = ae[1], ie = (0, o.useState)({}), le = r(ie, 2), de = le[0], ce = le[1], se = (0, o.useState)(""), ue = r(se, 2), pe = (ue[0], ue[1]);
        console.log("props", e2), (0, o.useEffect)(function() {
          v(e2.events.list), y(e2.events.month), S(e2.events.year), N({ month: e2.events.month, year: e2.events.year });
        }, [e2.events]), N = function(e3) {
          "next" === e3 ? D.setMonth(D.getMonth() + 1) : "prev" === e3 ? D.setMonth(D.getMonth() - 1) : (D.setMonth(O.map(function(e4) {
            return e4;
          }).indexOf(e3.month)), D.setFullYear(e3.year));
        }, (k = function(e3) {
          var t3 = {};
          1 === e3 && D.setDate(1);
          for (var n3 = D.getDay(), a3 = new Date(D.getFullYear(), D.getMonth(), 0).getDate(), r2 = [], o2 = new Date(D.getFullYear(), D.getMonth() + 1, 0).getDate(), i2 = 1; i2 <= o2; i2++)
            r2.push(i2);
          t3.currentDays = r2;
          for (var l2 = [], d2 = n3; d2 > 0; d2--)
            l2.push(a3 - d2 + 1);
          t3.prevDays = l2;
          for (var c2 = [], s2 = 42 - (t3.currentDays.length + n3), u2 = 1; u2 <= s2; u2++)
            c2.push(u2);
          t3.nextDays = c2, z = t3;
        })(1), pe = function() {
          var t3 = O[D.getMonth()] + "," + D.getFullYear();
          e2.eventMonth && e2.eventMonth(t3);
        };
        return i.default.createElement(i.default.Fragment, null, i.default.createElement("div", { className: "dateEcContainer", id: "rsce" }, i.default.createElement("div", { className: "header" }, i.default.createElement("h1", null, i.default.createElement("button", { type: "button", className: "material-icons prev event-arrows", onClick: function() {
          N("prev"), k(), pe(), l(a2 - 1);
        } }, "keyboard_arrow_left"), O[D.getMonth()], " ", D.getFullYear(), i.default.createElement("button", { type: "button", className: "material-icons next event-arrows", onClick: function() {
          N("next"), k(), pe(), l(a2 + 1);
        } }, "keyboard_arrow_right"))), i.default.createElement("div", { className: "insideWrapper" }, i.default.createElement("div", { className: "insideContainer" }, i.default.createElement("div", { className: "weekdays" }, i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Sunday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Monday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Tuesday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Wednesday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Thursday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Friday")), i.default.createElement("div", { style: { fontSize: s.fontSize + 3 + "px", lineHeight: 2 * (s.fontSize + 3) + "px" }, className: " " + (s.border ? "bordered" : "") }, i.default.createElement("strong", null, "Saturday"))), i.default.createElement("div", { className: "days" }, z.prevDays.map(function(e3) {
          return i.default.createElement("div", { key: e3, className: "prev_days " + (s.border ? "bordered" : "") + " " + (s.pattern ? s.pattern : "") }, !s.presentOnly && i.default.createElement("span", { style: { width: 2 * s.fontSize + "px", height: 2 * s.fontSize + "px", fontSize: s.fontSize, lineHeight: 2 * s.fontSize + "px" }, className: "dayvalue \n                                    " + (s.positionX ? s.positionX : "") + " \n                                    " + (s.positionY ? s.positionY : "") + " \n                                    " + (s.badge ? "ec-badge-" + s.badge : "") + " \n                                    " }, e3));
        }), z.currentDays.map(function(t3, n3) {
          return i.default.createElement(i.default.Fragment, { key: t3 }, i.default.createElement("div", { onClick: function() {
            s.accessibility && (T(j = t3), A(R = D.getMonth()), W(q = D.getFullYear()));
          }, className: "\n                                        " + (t3 === (/* @__PURE__ */ new Date()).getDate() && D.getMonth() === (/* @__PURE__ */ new Date()).getMonth() && D.getFullYear() === (/* @__PURE__ */ new Date()).getFullYear() ? "today" : "") + " \n                                        " + (t3 === j && R === D.getMonth() && q === D.getFullYear() ? "selected_day" : "") + "\n                                        " + (Date.parse(D.getMonth() + " " + t3 + ", " + D.getFullYear() + " ") > Date.parse((/* @__PURE__ */ new Date()).getMonth() + " " + (/* @__PURE__ */ new Date()).getDate() + ", " + (/* @__PURE__ */ new Date()).getFullYear()) || D.getFullYear() > (/* @__PURE__ */ new Date()).getFullYear() ? "coming_days" : "") + "\n                                        " + (s.border ? "bordered" : "") + " \n                                        " + (s.pattern ? s.pattern : "") + " \n                                        " + (f.map(function(e3) {
            return e3.day;
          }).indexOf(t3) > -1 && f[f.map(function(e3) {
            return e3.day;
          }).indexOf(t3)].day === t3 && b === O[D.getMonth()] && Number(E) === D.getFullYear() ? "hasEvent" : "") + "\n                                        " }, i.default.createElement("span", { style: { width: 2 * s.fontSize + "px", height: 2 * s.fontSize + "px", fontSize: s.fontSize, lineHeight: 2 * s.fontSize + "px" }, className: "dayvalue \n                                    " + (s.positionX ? s.positionX : "") + " \n                                    " + (s.positionY ? s.positionY : "") + " \n                                    " + (s.badge ? "ec-badge-" + s.badge : "") + " \n                                    \n                                    " }, t3), f.length > 0 && f.map(function(e3) {
            return e3.day;
          }).indexOf(t3) > -1 && i.default.createElement(i.default.Fragment, null, f[f.map(function(e3) {
            return e3.day;
          }).indexOf(t3)].day === t3 && b === O[D.getMonth()] && Number(E) === D.getFullYear() && i.default.createElement("ul", { className: "events", style: { height: "calc(100% - " + (2 * s.fontSize + 12) + "px)", marginTop: 2 * s.fontSize + 12 + "px" } }, f[f.map(function(e3) {
            return e3.day;
          }).indexOf(t3)].events.map(function(a3, r2) {
            return i.default.createElement("li", { key: "event" + t3 + r2, style: { fontSize: s.fontSize - 3 + "px" }, onMouseOver: function(t4) {
              return e2.options.tooltip ? function(t5, n4, a4) {
                var r3 = e2.options.tooltipPosition;
                n4 && ce(n4);
                document.getElementById("rsce").getBoundingClientRect();
                var o2 = t5.target.getBoundingClientRect();
                V(true);
                var i2 = document.getElementById("rsce-tooltip").getBoundingClientRect();
                "top" !== r3 && "bottom" !== r3 && "" !== r3 && void 0 !== r3 || ((z.prevDays.length + a4 + 1) % 7 != 0 && oe(o2.x - (i2.width - o2.width) / 2), (z.prevDays.length + a4 + 1) % 7 == 0 && oe(o2.x - (i2.width - o2.width)), (z.prevDays.length + a4) % 7 == 0 && oe(o2.x), "top" !== r3 && "" !== r3 && void 0 !== r3 || te(o2.y - i2.height - 20), "bottom" === r3 && te(o2.y + o2.height + 20)), "left" !== r3 && "right" !== r3 || (te(o2.y), o2.x < i2.width + 20 && (U("right"), r3 = "right"), (z.prevDays.length + a4 + 1) % 7 == 0 && (U("left"), r3 = "left"), "left" === r3 && oe(o2.x - i2.width - 20), "right" === r3 && oe(o2.x + o2.width + 20));
              }(t4, a3, n3) : void 0;
            }, onMouseOut: function(t4) {
              return e2.options.tooltip ? (V(false), ce({}), void U(e2.options.tooltipPosition)) : void 0;
            }, onClick: function() {
              return e2.eventClick ? (t4 = a3, void e2.eventClick(t4)) : void 0;
              var t4;
            } }, a3.title);
          })))));
        }), z.nextDays.map(function(e3) {
          return i.default.createElement("div", { key: e3, className: "next_days " + (s.border ? "bordered" : "") + " " + (s.pattern ? s.pattern : "") + " " }, !s.presentOnly && i.default.createElement("span", { style: { width: 2 * s.fontSize + "px", height: 2 * s.fontSize + "px", fontSize: s.fontSize, lineHeight: 2 * s.fontSize + "px" }, className: "dayvalue \n                                    " + (s.positionX ? s.positionX : "") + " \n                                    " + (s.positionY ? s.positionY : "") + " \n                                    " + (s.badge ? "ec-badge-" + s.badge : "") + " \n                                    " }, e3));
        })), e2.options.tooltip && i.default.createElement("div", { id: "rsce-tooltip", className: "ec-tooltip " + ("" !== L && L ? L : "top"), style: { top: ee + "px", left: re + "px", visibility: Q ? "visible" : "hidden", zIndex: Q ? "unset" : "-1", opacity: Q ? "1" : "0" } }, Q && i.default.createElement(i.default.Fragment, null, e2.options.tooltipTitle && i.default.createElement("h2", null, de.title), de.details && i.default.createElement(i.default.Fragment, null, Object.keys(de.details).map(function(e3, t3) {
          return i.default.createElement("p", { key: t3 }, i.default.createElement("span", { className: "title" }, e3, ":"), "  ", de.details[e3]);
        }))))))));
      };
    }, function(e, t) {
      e.exports = require_react();
    }, function(e, t, n) {
      var a = n(3), r = n(4);
      "string" == typeof (r = r.__esModule ? r.default : r) && (r = [[e.i, r, ""]]);
      var o = { insert: "head", singleton: false };
      a(r, o);
      e.exports = r.locals || {};
    }, function(e, t, n) {
      "use strict";
      var a, r = function() {
        return void 0 === a && (a = Boolean(window && document && document.all && !window.atob)), a;
      }, o = function() {
        var e2 = {};
        return function(t2) {
          if (void 0 === e2[t2]) {
            var n2 = document.querySelector(t2);
            if (window.HTMLIFrameElement && n2 instanceof window.HTMLIFrameElement)
              try {
                n2 = n2.contentDocument.head;
              } catch (e3) {
                n2 = null;
              }
            e2[t2] = n2;
          }
          return e2[t2];
        };
      }(), i = [];
      function l(e2) {
        for (var t2 = -1, n2 = 0; n2 < i.length; n2++)
          if (i[n2].identifier === e2) {
            t2 = n2;
            break;
          }
        return t2;
      }
      function d(e2, t2) {
        for (var n2 = {}, a2 = [], r2 = 0; r2 < e2.length; r2++) {
          var o2 = e2[r2], d2 = t2.base ? o2[0] + t2.base : o2[0], c2 = n2[d2] || 0, s2 = "".concat(d2, " ").concat(c2);
          n2[d2] = c2 + 1;
          var u2 = l(s2), p2 = { css: o2[1], media: o2[2], sourceMap: o2[3] };
          -1 !== u2 ? (i[u2].references++, i[u2].updater(p2)) : i.push({ identifier: s2, updater: h(p2, t2), references: 1 }), a2.push(s2);
        }
        return a2;
      }
      function c(e2) {
        var t2 = document.createElement("style"), a2 = e2.attributes || {};
        if (void 0 === a2.nonce) {
          var r2 = n.nc;
          r2 && (a2.nonce = r2);
        }
        if (Object.keys(a2).forEach(function(e3) {
          t2.setAttribute(e3, a2[e3]);
        }), "function" == typeof e2.insert)
          e2.insert(t2);
        else {
          var i2 = o(e2.insert || "head");
          if (!i2)
            throw new Error("Couldn't find a style target. This probably means that the value for the 'insert' parameter is invalid.");
          i2.appendChild(t2);
        }
        return t2;
      }
      var s, u = (s = [], function(e2, t2) {
        return s[e2] = t2, s.filter(Boolean).join("\n");
      });
      function p(e2, t2, n2, a2) {
        var r2 = n2 ? "" : a2.media ? "@media ".concat(a2.media, " {").concat(a2.css, "}") : a2.css;
        if (e2.styleSheet)
          e2.styleSheet.cssText = u(t2, r2);
        else {
          var o2 = document.createTextNode(r2), i2 = e2.childNodes;
          i2[t2] && e2.removeChild(i2[t2]), i2.length ? e2.insertBefore(o2, i2[t2]) : e2.appendChild(o2);
        }
      }
      function f(e2, t2, n2) {
        var a2 = n2.css, r2 = n2.media, o2 = n2.sourceMap;
        if (r2 ? e2.setAttribute("media", r2) : e2.removeAttribute("media"), o2 && btoa && (a2 += "\n/*# sourceMappingURL=data:application/json;base64,".concat(btoa(unescape(encodeURIComponent(JSON.stringify(o2)))), " */")), e2.styleSheet)
          e2.styleSheet.cssText = a2;
        else {
          for (; e2.firstChild; )
            e2.removeChild(e2.firstChild);
          e2.appendChild(document.createTextNode(a2));
        }
      }
      var v = null, g = 0;
      function h(e2, t2) {
        var n2, a2, r2;
        if (t2.singleton) {
          var o2 = g++;
          n2 = v || (v = c(t2)), a2 = p.bind(null, n2, o2, false), r2 = p.bind(null, n2, o2, true);
        } else
          n2 = c(t2), a2 = f.bind(null, n2, t2), r2 = function() {
            !function(e3) {
              if (null === e3.parentNode)
                return false;
              e3.parentNode.removeChild(e3);
            }(n2);
          };
        return a2(e2), function(t3) {
          if (t3) {
            if (t3.css === e2.css && t3.media === e2.media && t3.sourceMap === e2.sourceMap)
              return;
            a2(e2 = t3);
          } else
            r2();
        };
      }
      e.exports = function(e2, t2) {
        (t2 = t2 || {}).singleton || "boolean" == typeof t2.singleton || (t2.singleton = r());
        var n2 = d(e2 = e2 || [], t2);
        return function(e3) {
          if (e3 = e3 || [], "[object Array]" === Object.prototype.toString.call(e3)) {
            for (var a2 = 0; a2 < n2.length; a2++) {
              var r2 = l(n2[a2]);
              i[r2].references--;
            }
            for (var o2 = d(e3, t2), c2 = 0; c2 < n2.length; c2++) {
              var s2 = l(n2[c2]);
              0 === i[s2].references && (i[s2].updater(), i.splice(s2, 1));
            }
            n2 = o2;
          }
        };
      };
    }, function(e, t, n) {
      (t = n(5)(false)).push([e.i, '/* Main Container */\n\n.dateEcContainer {\n    position: relative;\n    width: var(--ec-mainWidth);\n    height: auto;\n    background-color: var(--ec-light);\n    border-radius: 8px;\n    overflow: visible;\n    box-shadow: 0px 2px 8px #e2e4e6;\n    box-sizing: border-box;\n}\n.insideWrapper {\n    width: 100%;\n    overflow: auto;\n}\n.insideContainer {\n    width: var(--ec-calendarWidth);\n}\n/* \n    Header\n    default: h1, p, next, prev\n */\n.dateEcContainer .header {\n    width: inherit;\n    height: 70px;\n    background-color: var(--ec-header);\n    position: relative;\n}\n\n.dateEcContainer .header h1 {\n    text-align: center;\n    font-size: 1.4rem;\n    color: var(--ec-title);\n    line-height: 3.2rem;\n    margin: 0;\n}\n/* next prev common, hover */\n.dateEcContainer .header .next,\n.dateEcContainer .header .prev {\n    color: var(--ec-title);\n    cursor: pointer;\n    box-shadow: 0px 2px 8px var(--dark);\n    line-height: 2rem;\n    font-size: 2.8rem;\n    position: relative;\n    top: 15px;\n}\n.dateEcContainer .header .next:hover,\n.dateEcContainer .header .prev:hover {\n    background-color: var(--ec-light);\n    color: var(--ec-primary);\n}\n/* .dateEcContainer .header .next {\n    left: auto;\n    right: 0;\n    text-align: center;\n}\n.dateEcContainer .header .prev {\n    left: 0;\n    right: auto;\n    text-align: center;\n} */\n/* this class is for date -range only, when from date has a value, this class got implemented there */\n.dateEcContainer .header .prev.disabled_action {\n    cursor: default;\n    box-shadow: 0px 0px 3px var(--dark);\n    opacity: 0.7;\n}\n.dateEcContainer .header .prev.disabled_action:hover {\n    background-color: var(--ec-primary);\n    color: var(--ec-light);\n}\n/* week names class */\n.dateEcContainer .weekdays {\n    width: 100%;\n    height: 40px;\n    padding: 0;\n    align-items: center;\n    border-bottom: 1px solid #e4e5e6;\n}\n.dateEcContainer .weekdays div {\n    font-size: var(--ec-badgeText);\n    width: calc(100% / 7);\n    display: block;\n    float: left;\n    text-align: center;\n    height: 100%;\n    line-height: 1.5rem;\n    color: var(--ec-primary);\n    background-color: var(--ec-title-bg-light);\n    box-sizing: border-box;\n    border-bottom-width: 0px ;\n}\n.dateEcContainer .weekdays div:nth-child(odd) {\n    background-color: var(--ec-title-bg);\n}\n\n/* calendar days class */\n.dateEcContainer .days {\n    width: 100%;\n    height: calc(100% - 95px);\n    padding: 0;\n    align-items: center;\n}\n\n.bordered {\n    border:1px solid var(--ec-border)\n}\n.dateEcContainer .days div {\n    font-size: 0.8rem;\n    width: calc(100% / 7);\n    display: block;\n    float: left;\n    text-align: center;\n    /* height: calc(100% / 6); */\n    height: var(--ec-height);\n    /* line-height: 2rem; */\n    color: var(--ec-primary);\n    cursor: pointer;\n    box-sizing: border-box;\n    position: relative;\n    background-color: var(--ec-bg-main);\n}\n.dateEcContainer .days div:hover {\n    border: 1px solid var(--ec-dark);\n}\n.dateEcContainer .days div.alternate:nth-child(odd) {\n    background-color: var(--ec-alternate-bg-light);\n}\n.dateEcContainer .days div.alternate:nth-child(even) {\n    background-color: var(--ec-alternate-bg);\n}\n.dateEcContainer .days div.alternate.today:nth-child(odd) {\n    background-color: var(--ec-today);\n}\n.dateEcContainer .days div.prev_days,\n.dateEcContainer .days div.next_days {\n    color: rgba(123, 124, 125, 0.4);\n}\n.dateEcContainer .days div:hover.prev_days,\n.dateEcContainer .days div:hover.next_days {\n    border: 1px solid var(--ec-dark);\n}\n.dateEcContainer .days div.today {\n    background-color: var(--ec-today);\n    color: var(--ec-light);\n}\n.dateEcContainer .days div.selected_day.hasEvent, \n.dateEcContainer .days div.selected_day {\n    background-color: var(--ec-selected);\n    color: var(--ec-selected-text);\n}\n\n.dateEcContainer .days div.selected_day.today {\n    background-color: var(--ec-selected);\n    color: var(--ec-selected-text);\n    border: 1px solid var(--ec-today);\n}\n\n.dayvalue {\n    position: absolute;\n    top:0;\n    left:0;\n    right: 0;\n    bottom: 0;\n    margin: 6px;\n    text-align: center;\n\n}\n.dayvalue.right {left:auto;}\n.dayvalue.left {right:auto;}\n.dayvalue.top { bottom: auto;}\n.dayvalue.bottom { top: auto;}\n\n.dayvalue.ec-badge-circle {\n    background-color: var(--ec-primary);\n    color: var(--ec-light);\n    border-radius: 100%;\n}\n\n.dayvalue.ec-badge-square {\n    background-color: var(--ec-primary);\n    color: var(--ec-light);\n    border-radius: 2px;\n}\n\n.prev_days .dayvalue.ec-badge-circle, \n.prev_days .dayvalue.ec-badge-square, \n.next_days .dayvalue.ec-badge-circle,\n.next_days .dayvalue.ec-badge-square\n{\n    background-color:rgba(123, 124, 125, 0.4);;\n    color: #fff;\n}\n.today .dayvalue.ec-badge-circle, \n.today .dayvalue.ec-badge-square\n{\n    background-color:#fff;\n    color:var(--ec-today);\n}\n.selected_day .dayvalue.ec-badge-circle, \n.selected_day .dayvalue.ec-badge-square\n{\n    background-color:#fff;\n    color:var(--ec-primary);\n}\n\n.dateEcContainer .days div.hasEvent {\n    background-color: var(--ec-event);\n}\n.events {\n    list-style: none;\n    overflow-y: auto;\n    overflow-x: hidden;\n    margin-left:0;\n    padding: 0;\n    \n}\n.events li {\n    text-align: left;\n    display: block;\n    font-weight: 700;\n    color: var(--ec-primary);\n    white-space: normal;\n    border-bottom: 1px dashed #dddddd;\n    padding: 5px 5px 5px 8px;\n    margin: 0 0;\n    cursor: pointer !important;\n}\n.ec-tooltip {\n    position: fixed;\n    width: var(--ec-tooltipWidth);\n    height: var(--ec-tooltipHeight);;\n    top:0;\n    left:0;\n    background-color:var(--ec-tooltip-bg);\n    color:var(--ec-tooltip-fg);\n    transition: all ease 0.4s;\n    padding: 20px 10px 10px;\n    border-radius: var(--ec-tooltip-border-radius);\n    box-shadow: 0px 0px 3px rgba(0,0,0,0.4);\n}\n.ec-tooltip > h2 {\n    font-size: var(--ec-tooltip-title);\n    text-align: center;\n}\n.ec-tooltip > p {\n    text-align: left;\n    font-size: var(--ec-tooltip-list);\n    margin:4px 0 0;\n    padding: 0 10px;\n}\n.ec-tooltip > p > span.title {\n    font-weight: bold;\n    text-transform: capitalize;\n}\n.ec-tooltip.top::after {\n    content: "";\n    width: 0;\n    height: 0;\n    border-left: 12px solid transparent;\n    border-right: 12px solid transparent;\n    border-top: 12px solid var(--ec-tooltip-bg);\n    position: absolute;\n    top: auto;\n    bottom: -12px;\n    left: 0;\n    right: 0;\n    margin: auto;\n}\n.ec-tooltip.bottom::after {\n    content: "";\n    width: 0;\n    height: 0;\n    border-left: 12px solid transparent;\n    border-right: 12px solid transparent;\n    border-bottom: 12px solid var(--ec-tooltip-bg);\n    position: absolute;\n    top: -12px;\n    bottom: auto;\n    left: 0;\n    right: 0;\n    margin: auto;\n}\n.ec-tooltip.left::after {\n    content: "";\n    width: 0;\n    height: 0;\n    border-bottom: 12px solid transparent;\n    border-top: 12px solid transparent;\n    border-left: 12px solid var(--ec-tooltip-bg);\n    position: absolute;\n    top: 12px;\n    bottom: auto;\n    left: auto;\n    right: -12px;\n    margin: auto;\n}\n.ec-tooltip.right::after {\n    content: "";\n    width: 0;\n    height: 0;\n    border-bottom: 12px solid transparent;\n    border-top: 12px solid transparent;\n    border-right: 12px solid var(--ec-tooltip-bg);\n    position: absolute;\n    top: 12px;\n    bottom: auto;\n    right: auto;\n    left: -12px;\n    margin: auto;\n}\n.events li:hover {\n    color: var(--ec-light);\n    background-color: var(--ec-secondary);\n}\n\n.event-arrows {\n    border:none;\n    background-color: transparent;\n}\n\n.event-arrows:hover {\n    opacity: 0.8;\n}\n\n', ""]), e.exports = t;
    }, function(e, t, n) {
      "use strict";
      e.exports = function(e2) {
        var t2 = [];
        return t2.toString = function() {
          return this.map(function(t3) {
            var n2 = function(e3, t4) {
              var n3 = e3[1] || "", a = e3[3];
              if (!a)
                return n3;
              if (t4 && "function" == typeof btoa) {
                var r = (i = a, l = btoa(unescape(encodeURIComponent(JSON.stringify(i)))), d = "sourceMappingURL=data:application/json;charset=utf-8;base64,".concat(l), "/*# ".concat(d, " */")), o = a.sources.map(function(e4) {
                  return "/*# sourceURL=".concat(a.sourceRoot || "").concat(e4, " */");
                });
                return [n3].concat(o).concat([r]).join("\n");
              }
              var i, l, d;
              return [n3].join("\n");
            }(t3, e2);
            return t3[2] ? "@media ".concat(t3[2], " {").concat(n2, "}") : n2;
          }).join("");
        }, t2.i = function(e3, n2, a) {
          "string" == typeof e3 && (e3 = [[null, e3, ""]]);
          var r = {};
          if (a)
            for (var o = 0; o < this.length; o++) {
              var i = this[o][0];
              null != i && (r[i] = true);
            }
          for (var l = 0; l < e3.length; l++) {
            var d = [].concat(e3[l]);
            a && r[d[0]] || (n2 && (d[2] ? d[2] = "".concat(n2, " and ").concat(d[2]) : d[2] = n2), t2.push(d));
          }
        }, t2;
      };
    }]);
  }
});
export default require_SingleCalendarEvents();
//# sourceMappingURL=react-single-calendar-events.js.map
