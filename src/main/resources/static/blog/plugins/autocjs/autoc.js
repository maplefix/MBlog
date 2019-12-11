/*!
 * autoc.js v1.3.0 - Automatically create directory navigation for your article.
 * Copyright (c) 2017 Robert Yao. All rights reserved.
 * Licensed under MIT License.
 */
! function (a, b) {
    "use strict";
    "function" == typeof define && define.amd ? define("autocjs", ["jquery"], b(a, $)) : "function" == typeof define &&
    define.cmd ? define("autocjs", function (c, d, e) {
        e.exports = b(a, c("jquery"))
    }) : "object" == typeof exports ? module.exports = b(a, require("jquery")) : b(a, jQuery)
}("undefined" != typeof window ? window : this, function (a, b) {
    "use strict";

    function c(a) {
        return a.replace(new RegExp(n, "img"), "")
    }

    function d(a) {
        return a.replace(/[\r\t\n]/g, " ").replace(/[&<>"'\/`]/g, function (a) {
            return m[a]
        })
    }

    function e(a) {
        return a.replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&amp;/g, "&").replace(/&quot;/g, '"').replace(
            /&#x27;/g, "'").replace(/&#x2F;/g, "/").replace(/&#x60;/g, "`")
    }

    function f(a) {
        return e(d(c(a)))
    }

    function g(a) {
        var c = a.data,
            d = a.html,
            e = a.startTag || "{",
            g = a.endTag || "}";
        return d += "", b.each(c, function (a, b) {
            d = d.replace(new RegExp(e + a + g, "img"), f(b))
        }), f(d)
    }

    function h(a) {
        return o += 1, a ? a + "-" + o : o
    }

    function i(a, b, c) {
        var d;
        switch (b) {
            case 1:
                d = a[a[c - 1].pid].pid;
                break;
            case 2:
                d = a[a[a[c - 1].pid].pid].pid;
                break;
            case 3:
                d = a[a[a[a[c - 1].pid].pid].pid].pid;
                break;
            case 4:
                d = a[a[a[a[a[c - 1].pid].pid].pid].pid].pid;
                break;
            case 5:
                d = a[a[a[a[a[a[c - 1].pid].pid].pid].pid].pid].pid;
                break;
            default:
                d = a[a[c - 1].pid].pid
        }
        return d
    }

    function j(a) {
        var c = [],
            d = 1,
            e = 0;
        return b(a).each(function (a, f) {
            var g = b(f),
                h = g.text(),
                j = g.attr("rel") ? g.attr("rel") : "",
                k = parseInt(g[0].tagName.toUpperCase().replace(/[H]/gi, ""), 10),
                l = -1;
            k > d ? (e += 1, l = 1 === e ? -1 : a - 1) : k === d || k < d && k > e ? 1 === k ? (e = 1, l = -
                1) : l = c[a - 1].pid : k <= e && (1 === k ? e = 1 : e -= d - k, l = 1 === e ? -1 : i(c,
                d - k, a)), d = k, c.push({
                id: a,
                level: e,
                text: h,
                tag: f.tagName,
                pid: l,
                rel: j
            })
        }), c
    }

    function k(a, c) {
        var d = [];
        return b(a).each(function (a, e) {
            var f = e.id,
                g = b(c).attr({
                    id: r + "-" + f,
                    href: "#" + q + "-" + f,
                    "aria-label": e.text
                }).addClass(r).addClass(A);
            d.push(g)
        }), d
    }

    function l(a) {
        var c = {},
            d = [];
        return b(a).each(function (a, b) {
            var d = -1 === b.pid ? "H1" : b.pid.toString();
            c[d] || (c[d] = [])
        }), b.each(c, function (e) {
            b(a).each(function (a, b) {
                var d = -1 === b.pid ? "H1" : b.pid.toString();
                e === d && c[e].push(b)
            }), d.push(c[e])
        }), d
    }
    var m = {
            "&": "&amp;",
            "<": "&lt;",
            ">": "&gt;",
            '"': "&quot;",
            "'": "&#x27;",
            "/": "&#x2F;",
            "`": "&#x60;"
        },
        n = "<script[^>]*>([\\S\\s]*?)</script\\s*>",
        o = -1,
        p = "article-",
        q = "autocjs-heading",
        r = "autocjs-anchor",
        s = "autocjs",
        t = "autocjs-chapters",
        u = p + t,
        v = "autocjs-subjects",
        w = "autocjs-chapter",
        x = "autocjs-text",
        y = "autocjs-code",
        z = "autocjs-show",
        A = "autocjs-hide",
        B = '<a class="' + r + '" aria-hidden="true"></a>',
        C = '<div id="{id}" class="' + s + " " + A + '" aria-hidden="true"></div>',
        D = '<ol class="' + t + '" aria-hidden="true"></ol>',
        E = '<ol class="' + v + '" aria-hidden="true"></ol>',
        F = '<li class="' + w + '" aria-hidden="true"></li>',
        G = '<a class="' + x + '" aria-hidden="true"></a>',
        H = '<em class="' + y + '" aria-hidden="true"></em>',
        I = '<div class="autocjs-overlay ' + A + '" aria-hidden="true"></div>',
        J = function (a) {
            return this.attributes = {}, this.elements = {
                article: null,
                headings: null,
                chapters: null,
                wrap: null,
                header: null,
                body: null,
                list: null,
                footer: null,
                switcher: null,
                top: null,
                overlay: null
            }, this.data = {
                chapters: [],
                anchors: [],
                list: []
            }, this.set(J.defaults).init(a), this
        };
    return J.defaults = {
        article: "",
        selector: "h1,h2,h3,h4,h5,h6",
        title: "Table of Contents",
        isAnchorsOnly: !1,
        isAnimateScroll: !0,
        hasDirectoryInArticle: !1,
        hasChapterCodeAtHeadings: !1,
        hasChapterCodeInDirectory: !0,
        ANCHOR: B,
        WRAP: C,
        HEADER: '<h2 class="autocjs-hd" aria-hidden="true">{title}</h2>',
        BODY: '<nav class="autocjs-bd" aria-hidden="true"></nav>',
        FOOTER: '<div class="autocjs-ft" aria-hidden="true"></div>',
        SWITCHER: '<h2 class="autocjs-switcher" title="目录" aria-hidden="true">&#926;</h2>',
        /*TOP: '<a class="autocjs-top" href="#top" aria-hidden="true">TOP</a>',*/
        CHAPTERS: D,
        SUBJECTS: E,
        CHAPTER: F,
        TEXT: G,
        CODE: H,
        OVERLAY: I
    }, J.prototype = {
        version: "1.2.0",
        constructor: J,
        init: function (a) {
            return b.isPlainObject(a) && this.set(a), this.initElements().initData().render().addListeners(),
                this
        },
        initElements: function () {
            var a = this,
                c = this.dom();
            return c.article = b(this.get("article")), c.headings = c.article.find(this.get("selector")), c
                .chapters = b(this.get("CHAPTERS")).addClass(u), c.wrap = b(g({
                data: {
                    id: h(s)
                },
                html: a.get("WRAP")
            })), c.header = b(g({
                data: {
                    title: a.get("title")
                },
                html: a.get("HEADER")
            })), c.body = b(this.get("BODY")), c.list = b(this.get("CHAPTERS")), c.footer = b(this.get(
                "FOOTER")), c.switcher = b(this.get("SWITCHER")), c.top = b(this.get("TOP")), c.overlay =
                b(this.get("OVERLAY")), this
        },
        initData: function () {
            var a = this.data;
            return a.chapters = j(this.headings()), a.anchors = k(this.chapters(), this.get("ANCHOR")), a.list =
                l(this.chapters()), this
        },
        reload: function (a) {
            return this.destroy().init(a), this
        },
        set: function (a) {
            return b.isPlainObject(a) && b.extend(this.attributes, a), this
        },
        get: function (a) {
            return this.attributes[a]
        },
        dom: function () {
            return this.elements
        },
        article: function () {
            return this.dom().article
        },
        headings: function () {
            return this.dom().headings
        },
        chapters: function (a, b) {
            return b = !1 !== b, a ? (this.data.chapters = j(a), b || (this.get("hasDirectoryInArticle") &&
                this.renderArticleChapters(), this.get("isAnchorsOnly") || this.renderSidebarChapters()
            ), this) : this.data.chapters
        },
        anchors: function () {
            return this.data.anchors
        },
        list: function () {
            return this.data.list
        },
        index: function (a) {
            var c = -1;
            return b(this.list()).each(function (d, e) {
                b(e).each(function (b, d) {
                    if (d === a) return c = b, !1
                })
            }), c
        },
        render: function () {
            return this.renderArticleDirectory().renderAnchors().renderSidebarDirectory(), this
        },
        renderArticleDirectory: function () {
            var a = b(this.article()[0].firstChild);
            return this.get("hasDirectoryInArticle") ? (this.dom().chapters.insertBefore(a), this.renderArticleChapters(),
                this) : this
        },
        renderArticleChapters: function () {
            return this.get("hasDirectoryInArticle") && this.renderChapters(this.dom().chapters), this
        },
        renderAnchors: function () {
            var a = this,
                c = this.headings(),
                d = this.anchors();
            return b(this.chapters()).each(function (e, f) {
                var g = q + "-" + f.id,
                    h = b(c[e]),
                    i = h.find("#" + g),
                    j = b(d[e]);
                i[0] && i.remove(), h.attr("id", g).addClass(q).append(j), a.renderHeadingChapterCode(
                    f)
            }), this
        },
        renderHeadingChapterCode: function (a) {
            var c, d, e, f = p + y,
                g = a.pid,
                h = a.id,
                i = a.tag,
                j = b("#" + q + "-" + h),
                k = j.find("#" + f + "-" + h);
            return k[0] && k.remove(), this.get("hasChapterCodeAtHeadings") && "H1" !== i ? (c = b(this.get(
                "CODE")).attr("id", f + "-" + h), e = this.index(a) + 1, d = -1 === g && "H2" ===
            i ? e : b("#" + f + "-" + g).html() + "." + e, c.attr("data-chapter", d).html(d), c.insertBefore(
                j[0].firstChild), this) : this
        },
        renderSidebarDirectory: function () {
            return this.get("isAnchorsOnly") ? this : (this.renderSidebarOutline().renderSidebarChapters(),
                this.dom().wrap.removeClass(A), this.resize(), this)
        },
        renderSidebarOutline: function () {
            var a = this.dom(),
                c = a.wrap,
                d = a.footer,
                e = b(document.body);
            return this.get("isAnchorsOnly") ? this : (d.append(a.switcher).append(a.top), a.body.append(a.list),
                c.empty().append(a.header).append(a.body).append(d), e.append(c).append(a.overlay),
            this.get("isAnimateScroll") || e.attr("id", "top"), this)
        },
        renderSidebarChapters: function () {
            return this.get("isAnchorsOnly") || this.renderChapters(this.dom().list), this
        },
        renderChapters: function (a) {
            var c = this,
                d = b(a),
                e = this.chapters();
            return d.empty(), b(e).each(function (a, e) {
                var f, g, h, i, j, k, l, m, n = e.pid,
                    o = e.id,
                    r = q + "-" + o,
                    s = e.rel ? e.rel : "#" + r,
                    t = b(c.get("CHAPTER")),
                    z = b(c.get("CODE")),
                    A = b(c.get("TEXT"));
                d.hasClass(u) ? (j = p + x + "-" + o, k = p + w + "-" + o, l = p + v + "-" + n, m =
                    p + w + "-" + n) : (j = x + "-" + o, k = w + "-" + o, l = v + "-" + n, m =
                    w + "-" + n), f = b("#" + l), A.attr({
                    id: j,
                    href: s,
                    rel: r
                }).html(e.text), t.attr({
                    id: k,
                    title: e.text
                }).append(A), -1 === e.pid ? (d.append(t), i = t.index() + 1, h = i) : (g = b(
                    "#" + m), f[0] || (f = b(c.get("SUBJECTS")).attr("id", l), g.append(f)),
                    f.append(t), i = t.index() + 1, h = g.find("." + y).html() + "." + i), c.get(
                    "hasChapterCodeInDirectory") && (z.attr("data-chapter", h).html(h), z.insertBefore(
                    A))
            }), this
        },
        show: function () {
            var a = this.dom(),
                b = a.wrap;
            return a.overlay.removeClass(A), b.animate({
                left: 0
            }, 150, function () {
                b.addClass(z)
            }), this
        },
        hide: function () {
            var a = this.dom(),
                b = a.wrap;
            return b.animate({
                left: -300
            }, 150, function () {
                a.overlay.addClass(A), b.removeClass(z)
            }), this
        },
        toggle: function () {
            return this.dom().wrap.hasClass(z) ? this.hide() : this.show(), this
        },
        resize: function () {
            var a = this.dom(),
                b = a.wrap[0].offsetHeight,
                c = a.header[0].offsetHeight;
            return a.body.height(b - c), this
        },
        scrollTo: function (a) {
            var c = this;
            return b("html,body").animate({
                scrollTop: a
            }, 500, "linear", function () {
                c.hide()
            }), this
        },
        destroy: function () {
            return this.removeListeners().remove(), this
        },
        remove: function () {
            var a = this.headings(),
                c = this.dom(),
                d = c.chapters,
                e = c.wrap,
                f = c.overlay;
            return d.remove(), b(a).each(function (a, c) {
                var d = b(c),
                    e = d.find("." + r),
                    f = d.find("." + y);
                d.removeClass(q).removeAttr("id"), f.remove(), e.remove()
            }), e.remove(), f.remove(), this
        },
        removeListeners: function () {
            var c = this.dom(),
                d = this.article(),
                e = c.wrap,
                f = c.overlay;
            return d.off(), e.off(), f.off(), b(a).off("resize", this.onWindowResize), this
        },
        addListeners: function () {
            var c = this,
                d = this.dom(),
                e = this.article(),
                f = {
                    context: c
                };
            return e.delegate("." + q, "mouseenter", f, this.onHeadingMouseEnter), e.delegate("." + q,
                "mouseleave", f, this.onHeadingMouseLeave), this.get("hasDirectoryInArticle") && e.delegate(
                "." + x, "click", f, this.onArticleChapterClick), this.get("isAnchorsOnly") || (d.switcher
                .on("click", f, this.onSwitcherClick), d.top.on("click", f, this.onTopClick), d.list.delegate(
                "." + x, "click", f, this.onSidebarChapterClick), d.overlay.on("click", f, this.onOverlayClick),
                b(a).on("resize", f, this.onWindowResize)), this
        },
        onHeadingMouseEnter: function (a) {
            var c = a.data.context;
            return b(this).find("." + r).removeClass(A), c
        },
        onHeadingMouseLeave: function (a) {
            var c = a.data.context;
            return b(this).find("." + r).addClass(A), c
        },
        onArticleChapterClick: function (a) {
            var c = a.data.context,
                d = c.get("isAnimateScroll"),
                e = b("#" + b(this).attr("rel"));
            return d && (c.scrollTo(e[0].offsetTop), a.stopPropagation(), a.preventDefault()), c
        },
        onSwitcherClick: function (a) {
            var b = a.data.context;
            return b.toggle(), a.stopPropagation(), a.preventDefault(), b
        },
        onTopClick: function (a) {
            var b = a.data.context;
            return b.get("isAnimateScroll") ? (b.scrollTo(0), a.stopPropagation(), a.preventDefault()) : b.hide(),
                b
        },
        onSidebarChapterClick: function (a) {
            var c = a.data.context,
                d = c.get("isAnimateScroll"),
                e = b("#" + b(this).attr("rel"));
            return d ? (c.scrollTo(e[0].offsetTop), a.stopPropagation(), a.preventDefault()) : c.hide(), c
        },
        onOverlayClick: function (a) {
            var b = a.data.context;
            return b.hide(), a.stopPropagation(), a.preventDefault(), b
        },
        onWindowResize: function (a) {
            var b = a.data.context;
            return b.resize(), b
        }
    }, b.extend(b.fn, {
        autoc: function (a) {
            var c = b(this),
                d = {};
            return b.extend(d, a, {
                article: c
            }), new J(d)
        }
    }), a.AutocJS = J, J
});