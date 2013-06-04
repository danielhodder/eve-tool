package nz.net.dnh.eve.web.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class VersionReader {
	private final String version;

	public VersionReader() {
		final InputStream versionResource = getClass().getResourceAsStream(
				"version.properties");

		if (versionResource == null) {
			this.version = "DEVELOPMENT";
		} else {
			try {
				final Properties properties = new Properties();
				properties.load(versionResource);
				this.version = properties.getProperty("version");
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public String getVersion() {
		return this.version;
	}
}
