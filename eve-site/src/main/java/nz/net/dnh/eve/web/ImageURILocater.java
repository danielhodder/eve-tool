package nz.net.dnh.eve.web;

import nz.net.dnh.eve.model.domain.Blueprint;
import nz.net.dnh.eve.model.domain.Type;

import org.springframework.beans.factory.annotation.Value;

public final class ImageURILocater {
	@Value("${eve.imageBaseURI}")
	private String baseLocatorURI;

	public String getUriForType(final Type type, final int size) {
		return getUriForTypeID(type.getTypeID(), Size.fromSize(size));
	}

	public String getUriForType(final Type type, final Size size) {
		return getUriForTypeID(type.getTypeID(), size);
	}

	public String getUriForType(final Blueprint blueprint, final int size) {
		return getUriForTypeID(blueprint.getBlueprintTypeID(), Size.fromSize(size));
	}

	public String getUriForType(final Blueprint blueprint, final Size size) {
		return getUriForTypeID(blueprint.getBlueprintTypeID(), size);
	}

	public String getUriForTypeID(final long id, final int size) {
		return this.baseLocatorURI + "/" + id + "_" + Size.fromSize(size).SizeString + ".png";
	}

	public String getUriForTypeID(final long id, final Size size) {
		return this.baseLocatorURI + "/" + id + "_" + size.SizeString + ".png";
	}

	public static enum Size {
		_32x32("32"),
		_64x64("64"),
		_128x128("128"),
		_256x256("256");

		final String SizeString;

		private Size(final String sizeString) {
			this.SizeString = sizeString;
		}

		public static Size fromSize(final int size) {
			switch (size) {
				case 32:
					return _32x32;

				case 64:
					return _64x64;

				case 128:
					return _128x128;

				case 256:
					return _256x256;

				default:
					throw new IllegalArgumentException(size + " was not a recognized size");
			}
		}
	}
}
