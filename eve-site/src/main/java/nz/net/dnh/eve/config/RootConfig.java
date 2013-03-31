package nz.net.dnh.eve.config;

import org.springframework.context.annotation.*;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
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
				getOverrideResource() });
		return ppc;
	}
	
	private static Resource getOverrideResource() {
		final Resource overrideResource = new ClassPathResource("/local.properties");
		if (overrideResource.exists()) {
			return overrideResource;
		}
		return EMPTY_RESOURCE;
	}
	
}