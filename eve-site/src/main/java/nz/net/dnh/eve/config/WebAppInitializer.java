package nz.net.dnh.eve.config;

import java.util.EnumSet;
import java.util.Set;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer extends AbstractSecurityWebApplicationInitializer {

	@Override
	public void afterSpringSecurityFilterChain(final ServletContext servletContext) {
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("nz.net.dnh.eve.config");
		
		final FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"));
		securityFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		
		final FilterRegistration.Dynamic characterEncodingFilter = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter());
		characterEncodingFilter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", "true");
		
		servletContext.addListener(new ContextLoaderListener(context));
		servletContext.setInitParameter("defaultHtmlEscape", "true");
		
		final DispatcherServlet servlet = new DispatcherServlet();
		// no explicit configuration reference here: everything is configured in the root container for simplicity
		servlet.setContextConfigLocation("");
		
		final ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", servlet);
		appServlet.setLoadOnStartup(1);
		appServlet.setAsyncSupported(true);
		
		final Set<String> mappingConflicts = appServlet.addMapping("/");
		if (!mappingConflicts.isEmpty())
			throw new IllegalStateException("'appServlet' cannot be mapped to '/' under Tomcat versions <= 7.0.14");
	}
}