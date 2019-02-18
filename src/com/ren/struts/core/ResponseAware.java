package com.ren.struts.core;

import javax.servlet.http.HttpServletResponse;

public interface ResponseAware {
	void responseAware(HttpServletResponse res);
}
