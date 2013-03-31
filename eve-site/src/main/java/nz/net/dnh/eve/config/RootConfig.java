package nz.net.dnh.eve.config;

import nz.net.dnh.eve.web.ImageURILocater;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@Configuration
@ComponentScan(basePackages = { "nz.net.dnh.eve" })
public class RootConfig {
	private static final Resource EMPTY_RESOURCE = new ByteArrayResource(new byte[0]);

	@Bean
	public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
		final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setLocations(new Resource[] {
				new ClassPathResource("/persistence.properties"),
				new ClassPathResource("/static.properties"),
				getOverrideResource() });
		return ppc;
	}

	@Bean
	public static ImageURILocater imageURILocator() {
		return new ImageURILocater();
	}

	private static Resource getOverrideResource() {
		final Resource overrideResource = new ClassPathResource("/local.properties");
		if (overrideResource.exists())
			return overrideResource;
		return EMPTY_RESOURCE;
	}

}