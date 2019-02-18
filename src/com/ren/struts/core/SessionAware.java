package com.ren.struts.core;

import javax.servlet.http.HttpSession;

public interface SessionAware {
	void sessionAware(HttpSession session);
}
