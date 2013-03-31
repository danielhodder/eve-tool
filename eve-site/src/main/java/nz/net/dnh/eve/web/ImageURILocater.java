package nz.net.dnh.eve.web;

import org.springframework.beans.factory.annotation.Value;

public final class ImageURILocater {
	@Value("${eve.imageBaseURI}")
	private String baseLocatorURI;

	public String getUriForType(final Object type, final Size size) {
		return this.baseLocatorURI;
	}

	public static enum Size {
		_32x32,
		_64x64,
		_128x128,
		_256x256;
	}
}
