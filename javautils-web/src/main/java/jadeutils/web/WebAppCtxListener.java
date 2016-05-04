/**
 * 
 */
package jadeutils.web;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * @author morgan
 * 
 */
public class WebAppCtxListener extends ContextLoaderListener {

	public WebAppCtxListener() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		WebAppCtx.appContext = WebApplicationContextUtils
				.getWebApplicationContext(event.getServletContext());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}
