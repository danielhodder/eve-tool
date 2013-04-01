package nz.net.dnh.eve.config;

import java.text.DecimalFormat;
import java.text.NumberFormat;

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

	@Bean
	public static NumberFormat currencyFormatter() {
		final DecimalFormat formatter = (DecimalFormat) NumberFormat.getCurrencyInstance();

		formatter.setPositivePrefix("");
		formatter.setPositiveSuffix(" ISK");
		formatter.setNegativePrefix("-");
		formatter.setNegativeSuffix(" ISK");

		return formatter;
	}

	@Bean
	public static NumberFormat percentageFormatter() {
		final NumberFormat formatter = NumberFormat.getNumberInstance();

		formatter.setMaximumIntegerDigits(3);
		formatter.setMaximumFractionDigits(2);
		formatter.setGroupingUsed(false);

		return formatter;
	}

	private static Resource getOverrideResource() {
		final Resource overrideResource = new ClassPathResource("/local.properties");
		if (overrideResource.exists())
			return overrideResource;
		return EMPTY_RESOURCE;
	}
}