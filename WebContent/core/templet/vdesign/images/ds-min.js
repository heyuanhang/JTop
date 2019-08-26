
KISSY.app("TShop", function () {
	var b = KISSY, c = b.DOM, a = (-1 === window.location.toString().indexOf("__debug")) ? false : true;
	return {version:"1.0", util:{formatURL:function (d) {
		return d + (-1 === d.indexOf("?") ? "?" : "&") + "_input_charset=utf-8";
	}, pageReload:function (d) {
		d = (d || window.location.toString()).replace(/t=(\d)+/g, "").replace(/([&|?])+$/, "");
		d = d + (-1 === d.indexOf("?") ? "?" : "&") + "t=" + KISSY.now();
		return window.location = d;
	}, parseJSON:function (f) {
		try {
			var d = new Function("return" + f.replace(/[\n|\t|\r]/g, ""))();
		}
		catch (g) {
			TShop.log("parse JSON error");
		}
		return d;
	}, ajaxFailure:function () {
		alert("\u5bf9\u4e0d\u8d77\uff0c\u7531\u4e8e\u7f51\u7edc\u5f02\u5e38\uff0c\u8bf7\u5237\u65b0\u540e\u91cd\u8bd5\uff01");
		TShop.util.Msg.hide();
	}, onIframeLoad:function (f, e) {
		var d = function () {
			try {
				e.call(this);
			}
			catch (g) {
				TShop.log(g);
			}
		};
		if (KISSY.UA.ie) {
			f.onreadystatechange = function () {
				if (f.readyState == "complete") {
					setTimeout(d, 0);
					f.onreadystatechange = null;
				}
			};
		} else {
			f.onload = function () {
				setTimeout(d, 0);
				f.onload = null;
			};
		}
	}, serialize:function () {
		var e = /select|textarea/i, d = /hidden|password|text/i;
		return function (h) {
			var g = h.elements, f = {};
			b.each(g, function (i) {
				if (i.name && !i.disabled && (i.checked || e.test(i.nodeName) || d.test(i.type))) {
					f[i.name] = c.val(i);
				}
			});
			return f;
		};
	}(), toggleFlower:function (d) {
		c.toggleClass([d, c.prev(d)], "hidden");
	}}, widget:{}, log:function (f, d, e) {
		if (a) {
			if (e) {
				f = e + ": " + f;
			}
			if (window.console !== undefined && console.log) {
				console[d && console[d] ? d : "log"](f);
			}
		}
		return this;
	}};
});
TShop.add("widget~Dialog", function (m) {
	var b = KISSY, o = b.DOM, q = b.Event, r = document, g = b.UA.ie, h = c.prototype, e = 0, n = {ID:null, head:"Title", body:"<div class=\"ui-dialog-loading\">\u6b63\u5728\u52a0\u8f7d\uff0c\u8bf7\u7a0d\u5019...</div>", foot:"<a href=\"javascript:;\" class=\"close\">close</a>", center:true, width:"580px", keypress:true, mask:false, drag:false, maskClassName:"ui-dialog-mask", close:true, className:"ui-dialog", classNameHd:"ui-dialog-hd", classNameBd:"ui-dialog-bd", classNameFt:"ui-dialog-ft"}, i = "changeHeader", j = "changeBody", f = "changeFooter", p = "center", d = "beforeShow", a = "show", l = "beforeHide", k = "hide";
	function c(v) {
		this.config = b.merge(n, v || {});
		var u = this, s = u.config, t;
		u._createHTML();
		if (true === s.keypress) {
			q.on(r, "keypress", function (w) {
				if (27 === w.keyCode && 200 === u._status) {
					u.hide();
				}
			});
		}
	}
	b.mix(h, b.EventTarget);
	b.mix(h, {_status:400, center:function () {
		var w = this, z = this.elem, s, B, v = z.offsetWidth, u = z.offsetHeight, t = o.viewportWidth(), A = o.viewportHeight();
		if (v < t) {
			s = (t / 2) - (v / 2) + o.scrollLeft();
		} else {
			s = o.scrollLeft();
		}
		if (u < A) {
			B = (A / 2) - (u / 2) + o.scrollTop();
		} else {
			B = o.scrollTop();
		}
		o.css(z, {left:s, top:B});
		o.css(w.mask, "height", o.docHeight() + "px");
		w.fire(p);
		return w;
	}, setHeader:function (t) {
		var s = this;
		t = t + "";
		s.elemHead.innerHTML = t;
		s.fire(i);
		return s;
	}, setBody:function (t) {
		var s = this;
		if (t.nodeType) {
			s.elemBody.innerHTML = "";
			s.elemBody.appendChild(t);
		} else {
			t = t + "";
			s.elemBody.innerHTML = t;
		}
		s.fire(j);
		return s;
	}, setFooter:function (t) {
		var s = this;
		t = t + "";
		s.elemFoot.innerHTML = t;
		s.fire(f);
		return s;
	}, show:function () {
		var t = this, s = this.config;
		t.fire(d);
		if (true === s.center) {
			t.center();
		}
		o.css(t.elem, "visibility", "");
		o.css(t.mask, "visibility", "");
		if (g && 6 === g) {
			o.addClass(r.body, "fix-select");
		}
		t._status = 200;
		t.fire(a);
		return t;
	}, hide:function () {
		var t = this, s = t.config;
		if (400 === t._status) {
			return;
		}
		t.fire(l);
		o.css(t.elem, "top", 0);
		o.css(t.elem, "visibility", "hidden");
		o.css(t.mask, "visibility", "hidden");
		o.css(t.mask, "height", 0);
		if (g && 6 === g) {
			o.removeClass(r.body, "fix-select");
		}
		t._status = 400;
		t.fire(k);
		return t;
	}, _createHTML:function () {
		var t = this, s = t.config;
		t.elem = r.createElement("dialog");
		t.elem.id = s.ID || "ui-dialog-" + e++;
		t.elem.className = s.className;
		o.css(t.elem, "width", s.width);
		o.css(t.elem, "visibility", "hidden");
		t.elemHead = r.createElement("hd");
		t.elemHead.className = s.classNameHd;
		t.elemHead.innerHTML = s.head;
		t.elemBody = r.createElement("bd");
		t.elemBody.className = s.classNameBd;
		t.setBody(s.body);
		if (true === s.close) {
			t.elemFoot = r.createElement("ft");
			t.elemFoot.className = s.classNameFt;
			t.elemFoot.innerHTML = s.foot;
			q.on(o.query("a.close", t.elemFoot), "click", function (u) {
				u.preventDefault();
				t.hide();
			});
		}
		t.elem.appendChild(t.elemHead);
		t.elem.appendChild(t.elemBody);
		t.elem.appendChild(t.elemFoot);
		r.body.appendChild(t.elem);
		if (true === s.mask) {
			t.mask = r.createElement("mask");
			t.mask.id = t.elem.id + "_" + s.maskClassName;
			t.mask.className = s.maskClassName;
			o.css(t.mask, "height", o.docHeight() + "px");
			o.css(t.mask, "visibility", "hidden");
			r.body.appendChild(t.mask);
		}
		if (true === s.drag) {
			o.addClass(t.elem, "ui-dialog-dd");
			t.DD = new YAHOO.util.DD(t.elem);
			t.DD.setHandleElId(t.elemHead);
		}
	}});
	TShop.widget.Dialog = c;
	TShop.widget.DialogMgr = {list:{}, get:function (u, s) {
		if (!u || !this.list[u]) {
			var t = new m.widget.Dialog(s);
			u = !u ? t.elem.id : u;
			this.list[u] = t;
		}
		return this.list[u];
	}};
});
TShop.add("widget~msg", function (f) {
	var c = KISSY, e = c.DOM, a = c.Event, b = "hidden";
	function d() {
		this.elem = e.get("#J_DSMsg");
	}
	c.mix(d.prototype, {val:function (j, i, g) {
		if (c.isString(j)) {
			g = g === undefined ? true : !!g;
			this.elem.innerHTML = j;
			this.show(i);
			if (g) {
				this.hide(g);
			}
		}
	}, loading:function () {
		return this.val("\u6b63\u5728\u5904\u7406\uff0c\u8bf7\u7a0d\u540e...", 1, false);
	}, show:function (g) {
		var h = this.elem;
		e.css(h, "opacity", 1);
		e.removeClass(h, "error");
		e.removeClass(h, b);
		return g + "" - 0 === 0 ? e.addClass(h, "error") : "";
	}, hide:function (g) {
		var h = this;
		if (true === g) {
			if (h.timer) {
				h.timer.cancel();
			}
			h.timer = c.later(function () {
				var j = {opacity:{form:1, to:0}}, i = new YAHOO.util.Anim(h.elem, j, 0.3);
				i.onComplete.subscribe(function () {
					e.addClass(h.elem, b);
					h.timer = null;
				});
				i.animate();
			}, 3000);
			return;
		}
		e.addClass(h.elem, b);
	}});
	if (!TShop.util.Msg) {
		TShop.util.Msg = new d();
	}
});
TShop.add("mod~toolbar", function (b) {
	var e = KISSY, l = e.DOM, k = e.Event, j = document, h = YAHOO.util.Connect, g = e.UA.ie;
	var f = function () {
		return {init:function () {
			k.add("#J_TRevert", "click", function (n) {
				n.preventDefault();
				var q = b.widget.DialogMgr.get(this.id, {head:b.lang.revert.title, mask:true, drag:true});
				if (!l.attr(p, "data-inited")) {
					q.setBody(l.get("#J_TRevertForm").value);
					var p = q.elem.getElementsByTagName("form")[0], o = p.getElementsByTagName("button");
					k.add(p, "submit", function (r) {
						r.preventDefault();
						if (l.hasClass(p, "ing")) {
							return;
						}
						if (!i(p)) {
							return alert("\u8bf7\u9009\u62e9\u64a4\u56de\u9875\u9762!");
						}
						l.addClass(p, "ing");
						b.util.Msg.loading();
						var s = b.util.formatURL(l.attr(p, "data-action"));
						h.setForm(p);
						h.asyncRequest(p.method, s, {success:function (u) {
							l.removeClass(p, "ing");
							var t = b.util.parseJSON(u.responseText);
							if ("1" === t.state) {
								b.util.Msg.val(t.message[0], t.message[1], false);
								return window.location = t.redirect;
							}
							b.util.Msg.val(t.message[0], t.message[1]);
							q.hide();
						}, failure:b.util.ajaxFailure, cache:false});
					});
					k.add(o[1], "click", function (r) {
						q.hide();
					});
					k.add(l.get("#J_TRevertSelectAll"), "click", function (r) {
						c(this, q.elem);
					});
					l.attr(p, "data-inited", 1);
				}
				q.show();
			});
			k.add("#J_TRelease", "click", function (n) {
				n.preventDefault();
				var q = b.widget.DialogMgr.get(this.id, {head:b.lang.release.title, body:l.get("#J_TReleaseForm").value, mask:true, drag:true});
				if (!l.attr(p, "data-inited")) {
					q.setBody(l.get("#J_TReleaseForm").value);
					var p = q.elem.getElementsByTagName("form")[0], o = q.elem.getElementsByTagName("button");
					k.add(p, "submit", function (r) {
						r.preventDefault();
						if (l.hasClass(p, "ing")) {
							return;
						}
						l.addClass(p, "ing");
						if (b.Interface) {
							b.Interface.autoSave(0, function () {
								d(p, q);
							});
						} else {
							d(p, q);
						}
					});
					e.each(o, function (r) {
						if (!l.hasClass(r, "submit")) {
							k.add(r, "click", function () {
								q.hide();
							});
						}
						if (l.hasClass(r, "J_Timer")) {
							var s = l.query(".J_Cal", q.elem);
							k.add(r, "click", function (u) {
								if (l.hasClass(this, "ing")) {
									return;
								}
								l.addClass(this, "ing");
								e.each(s, function (v) {
									v.disabled = "disabled";
								});
								d(p, q);
							});
							var t = TB.widget.SimpleCalendar.init(s[0], null, null, {mindate:"today"});
							q.on("show", function () {
								e.each(s, function (u) {
									u.disabled = "";
								});
							});
						}
					});
					q.on("hide", function () {
						l.removeClass(q.elem, "dialog-result");
						l.removeClass(q.elem, "dialog-result-failure");
						l.removeClass(p, "submited");
					});
					l.attr(p, "data-inited", 1);
				}
				q.show();
			});
			k.add("#J_Return", "click", function () {
				var p = b.widget.DialogMgr.get(this.id, {head:b.lang.verison.title, body:l.get("#J_ReturnShow").value, mask:true, drag:true});
				var o = l.get("button", p.elem), n = l.get(".return-tip", p.elem);
				if (!l.attr(n, "data-inited")) {
					k.add(o, "click", function () {
						p.hide();
					});
					l.attr(n, "data-inited", 1);
				}
				p.show();
			});
			if (g && 6 === g) {
				var m = l.query("#ds-toolbar .menu-select");
				e.each(m, function (n) {
					k.add(n, "mouseenter", function (o) {
						l.addClass(this, "hover");
					});
					k.add(n, "mouseleave", function (o) {
						l.removeClass(this, "hover");
					});
				});
				k.add(window, "resize", function () {
					a();
				});
				a();
			}
		}};
	}();
	function a() {
		var m = l.viewportWidth(), n = l.get("#ds-toolbar");
		if (m < 950) {
			l.css(n, "width", "950px");
		} else {
			l.css(n, "width", "");
		}
	}
	function d(n, o) {
		b.util.Msg.loading();
		var m = b.util.formatURL(l.attr(n, "data-action"));
		h.setForm(n);
		h.asyncRequest(n.method, m, {success:function (s) {
			l.removeClass(n, "ing");
			var q = b.util.parseJSON(s.responseText), t;
			t = l.query(".J_Text", o.elem)[0];
			t.innerHTML = q.message[0];
			l.removeClass(t.parentNode, "release-msg-error");
			if ("0" === q.state) {
				l.addClass(o.elem, "dialog-result-failure");
				l.addClass(t.parentNode, "release-msg-error");
			}
			var r = l.get(".J_Cal", o.elemBody);
			if (r && !r.disabled) {
				l.addClass(o.elem, "dialog-result-failure");
			}
			l.addClass(o.elem, "dialog-result");
			b.util.Msg.val(q.message[0], q.message[1]);
		}, failure:b.util.ajaxFailure, cache:false});
	}
	function c(o, p) {
		var n = o.checked, m = p.getElementsByTagName("input");
		e.each(m, function (q) {
			if ("checkbox" == q.type) {
				q.checked = !!n;
			}
		});
	}
	function i(o) {
		var m = o.getElementsByTagName("input"), n = 0;
		e.each(m, function (p) {
			if ("checkbox" == p.type) {
				return p.checked ? ++n : 0;
			}
		});
		return n;
	}
	if (!b.Toolbar) {
		b.Toolbar = f;
	}
});
TShop.add("mod~dd-module", function (b) {
	var f = KISSY, n = f.DOM, l = f.Event, a = YAHOO.util, i = a.Dom, j = a.DragDropMgr, k = document, h = f.UA.ie, g = "startDrag", e = "endDrag";
	b.DDModule = {filterCfg:{}, dragStartFilter:function (o) {
		this.filterCfg = {type:o};
	}, filter:function (r, q) {
		var o = n.attr(r, "data-width"), p = n.attr(q, "data-context");
		if (p && -1 === p.indexOf(o)) {
			b.log("\u4e0d\u7b26\u5408\u89c4\u5219" + p + " ==== " + o);
			return false;
		}
		return true;
	}, initDD:function (p, o) {
		return new d(p, o);
	}, initDDTarget:function (o) {
		return new YAHOO.util.DDTarget(o);
	}};
	f.mix(b.DDModule, f.EventTarget);
	f.extend(d, a.DDProxy, {startDrag:function (p, r) {
		var o = this.getDragEl(), q = this.getEl();
		n.addClass(q, "dd-temp");
		n.css(o, "border", "2px solid gray");
		b.DDModule.fire(g, {elem:q});
	}, endDrag:function (q) {
		var o = this.getDragEl(), r = this.getEl(), p, s = n.offset(r);
		n.removeClass(r, "dd-temp");
		n.css(o, "visibility", "");
		p = new YAHOO.util.Motion(o, {points:{to:[s.left, s.top]}}, 0.1);
		p.onComplete.subscribe(function () {
			n.css(o, "visibility", "hidden");
			n.css(o, "width", 0);
			b.DDModule.fire(e, {elem:r});
		});
		p.animate();
	}, onDrag:function (p) {
		var q = m(p), o = this;
		if (q < o.lastY) {
			o.goingUp = true;
		} else {
			if (q > o.lastY) {
				o.goingUp = false;
			}
		}
		o.lastY = q;
	}, onDragOver:function (t, v) {
		var u = this.getEl(), o = n.get("#" + v), s = n.hasClass(o, "J_TRegion"), r, q;
		if (!s) {
			r = o.parentNode;
			b.log(r);
			if (!n.hasClass(r, "J_TRegion")) {
				return;
			}
			if (!b.DDModule.filter(r, u)) {
				return;
			}
			if (this.goingUp) {
				r.insertBefore(u, o);
			} else {
				r.insertBefore(u, o.nextSibling);
			}
		} else {
			if (s) {
				if (!b.DDModule.filter(o, u)) {
					return;
				}
				q = n.children(o);
				if (q.length <= 1) {
					o.insertBefore(u, n.children(o)[0]);
				}
			}
		}
		j.refreshCache();
	}});
	function d(r, q, o, p) {
		d.superclass.constructor.call(this, r, o, p);
		if (q) {
			this.setHandleElId(q);
		}
		this.goingUp = false;
		this.lastY = 0;
		n.addClass(r, "ui-dd");
	}
	function m(o) {
		var p = o.pageY;
		if (!p && 0 !== p) {
			p = o.clientY || 0;
			if (h) {
				p += c()[0];
			}
		}
		return p;
	}
	function c() {
		var o = k.documentElement, p = k.body;
		if (o && (o.scrollTop || o.scrollLeft)) {
			return [o.scrollTop, o.scrollLeft];
		} else {
			if (p) {
				return [p.scrollTop, p.scrollLeft];
			} else {
				return [0, 0];
			}
		}
	}
});
TShop.add("mod~interface", function (t) {
	var h = KISSY, w = h.DOM, C = h.Event, f = window, D = document, m = YAHOO.util.Connect, k = h.UA.ie, d = "data-isedit", r = "data-isdel", g = "data-ismove", a = "data-istarget", v = "data-prototypeid", s = "data-id", l = "data-uri", o = "data-render", q = "data-modules", c = "data-width", u = "ds-bar-moveup", e = "ds-bar-movedown", B = "no-move-up", n = "no-move-down";
	window.g_ds_del_list = [];
	window.g_ds_TYPE = 0 === window.g_ds_TYPE ? 0 : 1;
	var i = {_saveState:1, _currentMod:null, _add_mod_index:0, updateCfg:null, resetIframeHeight:function (G) {
		var F = this, E = F.updateCfg, H;
		if (null === E) {
			return;
		}
		H = w.get("iframe.ifr", E.Dialog.elemBody);
		G = G === undefined ? x(H) : G;
		w.css(H, "height", G + "px");
		E.Dialog.center();
	}, editMod:function (J) {
		var L = this, G = w.attr(J, s), F = w.attr(J, l), M = "data-inited", I = "data-loaded", H, E, K;
		E = t.widget.DialogMgr.get(G, {mask:true, drag:true, head:"\u7f16\u8f91\u5185\u5bb9", width:w.attr(J, c) || "540px"});
		if (!w.attr(E.elem, M)) {
			H = b(F);
			E.elemBody.appendChild(H);
			t.util.onIframeLoad(H, function () {
				var Q = H.contentDocument ? H.contentDocument : H.contentWindow.document;
				var N = w.get("#J_ModWidth", Q);
				var P = w.get("#PicWidth", Q);
				if (N) {
					N.innerHTML = J.offsetWidth;
					P.setAttribute("value", J.offsetWidth);
				}
				var O = E.elemBody;
				O.removeChild(w.children(O)[0]);
				w.removeClass(H, "hidden");
				w.css(H, "height", x(H) + "px");
				E.center();
			});
			E.on("show", function () {
				L.updateCfg = {elem:J, Dialog:this};
				var Q = w.get(".ifr", this.elemBody);
				if (0 < k) {
					w.css(Q, "display", "");
					if (w.attr(E.elem, I)) {
						w.css(Q, "height", x(Q) + "px");
					}
					E.center();
				}
				var P = Q.contentDocument ? Q.contentDocument : Q.contentWindow.document;
				var N = w.get("#J_ModWidth", P);
				var O = w.get("#PicWidth", P);
				if (N) {
					N.innerHTML = J.offsetWidth;
					O.setAttribute("value", J.offsetWidth);
				}
			});
			E.on("hide", function () {
				var N = L.updateCfg, O;
				if (0 < k) {
					var P = w.get(".ifr", this.elemBody);
					w.css(P, "display", "none");
				}
				O = w.attr(N.elem, o);
				if (O) {
					L.renderMod(O, true);
				}
			});
			w.attr(E.elem, M, 1);
		}
		E.show();
		w.attr(E.elem, I, 1);
	}, renderMod:function (H, F, E) {
		var G = this;
		H = t.util.formatURL(H);
		t.util.Msg.loading();
		m.asyncRequest("GET", H, {success:function (N) {
			var K = N.responseText, M = G.updateCfg.elem, I = w.children(M), L = I[I.length - 1], O, J;
			h.each(I, function (P) {
				if (P !== L) {
					M.removeChild(P);
				}
			});
			O = D.createElement("div");
			O.innerHTML = K;
			J = w.children(w.children(O)[0]);
			h.each(J, function (P) {
				w.insertBefore(P, L);
			});
			O = null;
			if (E) {
				E();
			}
			G.reflowbar(M, L);
			if (!F) {
				G.updateCfg.Dialog.hide();
			}
			t.util.Msg.hide();
		}, failure:t.util.ajaxFailure, cache:false});
	}, delMod:function (H) {
		var F = this, J, G = w.attr(H, s), E, I = {width:{to:0}, height:{to:0}, opacity:{to:0}};
		E = new YAHOO.util.Anim(H, I, 0.3);
		E.onComplete.subscribe(function () {
			J = H.parentNode;
			H.innerHTML = "";
			H.parentNode.removeChild(H);
			g_ds_del_list.push(G);
			F.autoSave();
			F.resetState(J);
		});
		E.animate();
	}, resetState:function (F) {
		var G = w.query("a.ds-bar-moveup", F), E = w.query("a.ds-bar-movedown", F);
		w.removeClass(G, B);
		w.removeClass(E, n);
		w.addClass(G[0], B);
		w.addClass(E[E.length - 1], n);
	}, addMod:function (H) {
		var F = this, J = t.widget.DialogMgr.get("addmods", {head:"\u6dfb\u52a0\u6a21\u5757", mask:true, drag:true, width:"620px"});
		F.addbarElem = H;
		if (!w.attr(J.elem, "data-inited")) {
			J.setBody(w.get("#J_TAddMods").value);
			var I = w.query("div.mods-list", J.elem)[0], G = new h.Tabs(I, {activeTriggerCls:"selected", triggerType:"click"}), E = w.query(".btn-ok", J.elem);
			C.add(E, "click", function (L) {
				if (w.hasClass(this, "ing")) {
					return;
				}
				w.addClass(this, "ing");
				var N = this, M = t.util.formatURL(w.attr(this, l)) + "&m=" + w.attr(F.addbarElem.parentNode, "data-modules") + "&n=" + F._add_mod_index;
				if (0 === window.g_ds_TYPE) {
					var K = F.addbarElem.parentNode;
					while (!w.hasClass(K, "layout")) {
						K = K.parentNode;
					}
					M += "&l=" + (w.attr(K, s));
				}
				t.util.Msg.val("\u6b63\u5728\u83b7\u53d6\u6a21\u5757\u4fe1\u606f, \u8bf7\u7a0d\u5019...", true, false);
				m.asyncRequest("GET", M, {success:function (Q) {
					if (/^({")/i.test(Q.responseText)) {
						var W = t.util.parseJSON(Q.responseText);
						t.util.Msg.val(W.message[0], W.message[1]);
					} else {
						var R = D.createElement("div"), U, T, V, O = /<script([^>]*)>([\s\S]*?)<\/script>/ig, Q = Q.responseText, S = Q.replace(O, ""), P = new RegExp(O);
						R.innerHTML = S;
						U = w.children(R)[0];
						R = null;
						w.insertBefore(U, F.addbarElem);
						while (T = P.exec(Q)) {
							if ((V = T[2]) && V.length > 0) {
								h.globalEval(V);
							}
						}
						F._initMod(U);
						if ("32" === w.attr(U, v) && TB) {
							TB.ww.lightAll(U);
						}
						F.mods = p();
						t.util.Msg.val("\u6a21\u5757\u6dfb\u52a0\u5b8c\u6210.");
					}
					J.hide();
					w.removeClass(N, "ing");
				}, failure:t.util.ajaxFailure, cache:false});
			});
			J.on("show", function () {
				var K = w.attr(F.addbarElem.parentNode, c), L = w.query("#J_TAddModsPanel ul");
				if (undefined === K) {
					w.removeClass(w.query("#J_TAddModsPanel li"), "hidden");
					w.addClass(w.query("#J_TAddModsPanel li.no-mods"), "hidden");
					return;
				}
				h.each(L, function (N) {
					var M = w.query("li", N), O;
					h.each(M, function (P) {
						var Q = w.attr(P, "data-context");
						if (undefined === Q) {
							Q = "";
						}
						-1 === Q.indexOf(K) ? w.addClass(P, "hidden") : w.removeClass(P, "hidden");
					});
					O = w.query(".hidden", N);
					if (M.length === O.length) {
						w.removeClass(w.get(".no-mods", N), "hidden");
					}
				});
			});
			w.attr(J.elem, "data-inited", 1);
		}
		J.show();
	}, moveMod:function (I, H, J) {
		var E = this, L, F = {opacity:{to:0}}, G = {opacity:{to:1}};
		if ("ds-bar-moveup" === H) {
			if (w.hasClass(I, "no-move-up")) {
				return;
			}
			L = w.prev(J);
		} else {
			if ("ds-bar-movedown" === H) {
				if (w.hasClass(I, "no-move-down")) {
					return;
				}
				L = w.next(J);
			}
		}
		if (k) {
			w.addClass(J, "fix-ie-hover");
			w.css(J, "position", "relative");
		}
		var K = new YAHOO.util.Anim(J, F, 0.1);
		K.onComplete.subscribe(function () {
			("ds-bar-moveup" === H) ? w.insertBefore(J, L) : w.insertAfter(J, L);
			var M = new YAHOO.util.Anim(J, G, 0.1);
			if (k) {
				w.css(J, "position", "");
			}
			M.animate();
			E.resetState(J.parentNode);
			E.autoSave();
		});
		K.animate();
	}, autoSave:function (G, E) {
		var F = this, H = p();
		if (F.mods === H) {
			if (h.isFunction(E)) {
				E();
			}
			return t.log("no change");
		}
		F._saveState = 0;
		G = 0 === G ? 0 : 3000;
		if (F.saveTimer) {
			F.saveTimer.cancel();
			t.log("saveing");
		}
		F.saveTimer = h.later(function () {
			F.mods = H;
			F.sendMods(E);
			F.saveTimer = null;
		}, G);
	}, sendMods:function (E) {
		var G = this, J = w.get("#J_TSaveForm"), I = w.get("#J_TMods"), F = w.get("#J_TDelMods"), H = t.util.formatURL(w.attr(J, "data-action"));
		t.util.Msg.loading();
		F.value = g_ds_del_list.join("-");
		I.value = G.mods;
		m.setForm(J);
		m.asyncRequest(J.method, H, {success:function (L) {
			var K = t.util.parseJSON(L.responseText);
			if ("1" === K.state) {
				G._saveState = 1;
				window.g_ds_del_list = [];
				t.log("save success");
			} else {
				G._saveState = 0;
				t.log(K.message[0]);
			}
			t.util.Msg.val(K.message[0], K.message[1]);
			if (h.isFunction(E)) {
				E();
			}
		}, failure:t.util.ajaxFailure, cache:false});
	}, switchMod:function (E) {
		var G = this, F = "tb-minimize", H = w.query("#content .J_TBox");
		h.each(H, function (I) {
			if (!E) {
				G.reflowbar(I, null, 35);
				w.addClass(I, F);
			} else {
				w.removeClass(I, F);
				G.reflowbar(I);
			}
		});
	}, reflowbar:function (H, M, E) {
		if (!H) {
			return;
		}
		var I, K, N, L = "height", O = "tb-minimize", G = "position", J = "relative", F;
		if (w.hasClass(H, O)) {
			E = 35;
		}
		F = w.children(H);
		if (!M) {
			M = F[F.length - 1];
		}
		if (F.length > 0 && F[F.length - 1].tagName.toLowerCase() === "bar") {
			F.pop();
		}
		w.css(H, L, "");
		w.css(F, L, "");
		N = parseFloat(w.css(H, "padding-bottom"));
		I = (E || H.offsetHeight) - N;
		K = I;
		h.each(F, function (Q) {
			var P = Q.offsetHeight;
			K = K - P;
			K >= 0 ? w.css(Q, L, P + "px") : w.css(Q, L, 0);
		});
		w.css(H, L, I);
		w.css(M, {height:I, "margin-top":-I});
		w.css(w.children(M)[0], {height:I - 4, width:H.offsetWidth - 4});
	}, _initMod:function (G) {
		var E = this, I = A(G), H = w.hasClass(G.parentNode, "J_TRegion");
		E.reflowbar(G, I);
		G.appendChild(I);
		if (!H) {
			var F = w.get(".ds-bar-del", I);
			if (null !== F) {
				F.parentNode.removeChild(F);
			}
		}
		if ("1" === w.attr(G, g) && H) {
			t.DDModule.initDD(G);
		}
		C.add(I, "click", function (J) {
			var K = y(J);
			while ("a" !== K.tagName.toLowerCase()) {
				K = K.parentNode;
				if ("div" === K.tagName.toLowerCase()) {
					return;
				}
			}
			if ("_blank" === w.attr(K, "target")) {
				return;
			}
			J.preventDefault();
			if (w.hasClass(K, "ds-bar-edit")) {
				E.editMod(G);
			} else {
				if (w.hasClass(K, "ds-bar-moveup")) {
					E.moveMod(K, "ds-bar-moveup", G);
				} else {
					if (w.hasClass(K, "ds-bar-movedown")) {
						E.moveMod(K, "ds-bar-movedown", G);
					} else {
						if (w.hasClass(K, "ds-bar-del")) {
							E.delMod(G);
						}
					}
				}
			}
		});
		C.add(I, "dblclick", function (J) {
			var K = y(J), L = K.parentNode, K = w.get(".ds-bar-edit", L);
			if (K) {
				if ("_blank" === w.attr(K, "target")) {
					window.open(w.attr(K, "href"));
					return;
				} else {
					E.editMod(G);
				}
			}
		});
		if (k) {
			C.add(G, "mouseenter", function (J) {
				w.removeClass(this, "fix-ie-hover");
			});
		}
		E.resetState(G.parentNode);
		z(I);
	}, init:function () {
		var F = this, E = h.query("#page .J_TBox"), G = h.query("#page .J_TRegion");
		h.each(G, function (L) {
			var N = w.children(L), K = N[N.length - 1], J = j(), I = 0;
			if (K && K.tagName.toLowerCase() === "input" && K.value) {
				var M = K.value, H;
				if (/[,|:]/.test(M)) {
					H = M.split(",");
					h.each(H, function (O) {
						var P = O.split(":");
						w.attr(L, "data-" + P[0], P[1]);
					});
				} else {
					w.attr(L, q, M);
				}
				L.removeChild(K);
				N.pop();
			}
			K = N[N.length - 1];
			while (K && "1" !== w.attr(K, g) && w.attr(K, "data-prototypeid") != 13) {
				K = w.prev(K);
				I--;
			}
			if (N.length === 0) {
				L.appendChild(J);
			} else {
				w.insertAfter(J, K);
			}
			w.attr(J, "data-add-index", I);
			t.DDModule.initDDTarget(L);
			C.add(J, "click", function (O) {
				F.addMod(J);
				F._add_mod_index = w.attr(J, "data-add-index");
			});
		});
		h.each(E, function (H) {
			F._initMod(H);
		});
		t.DDModule.on("startDrag", function (H) {
			_currentMod = H.elem.parentNode;
		});
		t.DDModule.on("endDrag", function (I) {
			var H = I.elem.parentNode;
			if (_currentMod !== H) {
				F.resetState(H);
				F.resetState(_currentMod);
			} else {
				F.resetState(_currentMod);
			}
			F.reflowbar(I.elem);
			F.autoSave();
		});
		YAHOO.util.Event.on(f, "beforeunload", function (H) {
			if (0 === F._saveState) {
				var I = "\u4f60\u6709\u4e00\u4e9b\u66f4\u6539\u8fd8\u672a\u4fdd\u5b58\u3002\u70b9\u51fb\u786e\u5b9a\u79bb\u5f00\u6b64\u9875\uff0c\u70b9\u51fb\u53d6\u6d88\u5c06\u81ea\u52a8\u4fdd\u5b58\u3002";
				F.autoSave(0);
				H.returnValue = I;
				return I;
			}
		});
		F.mods = p();
		z("#J_SpeedTips");
		C.add("#J_TSwitchMod", "click", function (J) {
			J.preventDefault();
			var I = "tb-minimize", H = this, K = w.hasClass(this, I);
			K ? w.removeClass(H, I) : w.addClass(H, I);
			F.switchMod(K);
		});
		t.log("init end.");
	}};
	function z(E) {
		if (k && 6 === k) {
			E = w.get(E);
			if (!E) {
				return;
			}
			C.add(E, "mouseenter", function (F) {
				w.addClass(this, "hover");
			});
			C.add(E, "mouseleave", function (F) {
				w.removeClass(this, "hover");
			});
		}
	}
	function y(E) {
		var F = E.target || E.srcElement;
		try {
			if (F && 3 == F.nodeType) {
				return F.parentNode;
			}
		}
		catch (G) {
		}
		return F;
	}
	function A(G) {
		var K = D.createElement("bar"), I = D.createElement("barbd"), O = D.createElement("baracts"), N = "<a href=\"#\" class=\"ds-bar-%CLASS\" title=\"%TITLE\"><span>%TITLE</span></a>", M = "", L = /%TITLE/g, H = /%CLASS/, P = w.attr(G, d), Q = w.attr(G, r), J = w.attr(G, a), E = w.attr(G, l), F = w.attr(G, g);
		if ("1" === P) {
			M = J ? N.replace("#", E + "\" target=\"_blank\"") : N;
			M = M.replace(L, "\u7f16\u8f91").replace(H, "edit");
		}
		if ("1" === F) {
			M += N.replace(L, "\u4e0a\u79fb").replace(H, "moveup") + N.replace(L, "\u4e0b\u79fb").replace(H, "movedown");
		}
		if ("1" === Q) {
			M += N.replace(L, "\u5220\u9664").replace(H, "del");
		}
		O.innerHTML = M;
		K.appendChild(I);
		K.appendChild(O);
		return K;
	}
	function j() {
		var E = D.createElement("addmodbar");
		E.innerHTML = "<i></i>\u5728\u6b64\u5904\u6dfb\u52a0\u65b0\u6a21\u5757";
		return E;
	}
	function x(F) {
		F = w.get(F);
		if (!F) {
			return 0;
		}
		var E = F.contentDocument ? F.contentDocument : F.contentWindow.document;
		return E.compatMode === "CSS1Compat" ? E.documentElement.scrollHeight : E.body.scrollHeight;
	}
	function p() {
		var I = w.get("#content"), M = [], L, K, J, E, F, H, G = ",";
		h.each(w.children(I), function (O) {
			var N = (0 === window.g_ds_TYPE) ? w.children(O) : [O];
			L = [];
			h.each(N, function (P) {
				F = w.attr(P, s);
				H = w.attr(P, v);
				var Q = w.query(".J_TRegion", P);
				K = [];
				h.each(Q, function (S) {
					E = w.attr(S, q);
					var R = w.query(".J_TBox", S);
					J = [];
					h.each(R, function (T) {
						F = w.attr(T, s);
						H = w.attr(T, v);
						if (undefined !== F && undefined !== H) {
							J[J.length] = "{id:" + F + ",pid:" + H + "}";
						}
					});
					K[K.length] = E + ":[" + J.join(G) + "]";
				});
				F = w.attr(P, s);
				H = w.attr(P, v);
				if (K.length > 0) {
					L[L.length] = "{" + (undefined === F ? "" : ("id:" + F + ",")) + (undefined === H ? "" : ("pid:" + H + ",")) + K.join(G) + "}";
				}
			});
			if (L.length > 0) {
				M[M.length] = O.id + ":[" + L.join(G) + "]";
			}
		});
		return M.length > 0 ? ("{" + M.join(G) + "}") : "";
	}
	function b(F, E) {
		var G = D.createElement("iframe");
		G.src = F;
		G.scrolling = "no";
		G.frameBorder = 0;
		w.addClass(G, "ifr hidden");
		return G;
	}
	if (!t.Interface) {
		t.Interface = i;
	}
});
TShop.add("init", function (a) {
	a.init = function (b) {
		b = (b && b.length > 0) ? b : [];
		KISSY.each(b, function (c) {
			a[c].init();
		});
	};
});

