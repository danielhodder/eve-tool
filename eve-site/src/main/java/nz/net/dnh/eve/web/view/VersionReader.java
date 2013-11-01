package nz.net.dnh.eve.web.view;

import java.io.InputStream;
import java.util.Scanner;

import org.springframework.stereotype.Component;

@Component("versionReader")
public class VersionReader {
	private final String version;

	public VersionReader() {
		final InputStream versionResource = getClass().getResourceAsStream(
				"/version");

		if (versionResource == null) {
			this.version = "DEVELOPMENT";
		} else {
			this.version = new Scanner(versionResource).nextLine();
		}
	}

	public String getVersion() {
		return this.version;
	}
}
