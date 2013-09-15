package nz.net.dnh.eve.api.config;

import java.util.Objects;

/**
 * Holds the information required to use the EVE Online API. This consists of a keyID and a vcode (Verification Code)
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public class ApiKey {
	private final long keyID;
	private final String vCode;

	/**
	 * Gets the ID of the key to use
	 * 
	 * @return The ID of the key to use
	 */
	public long getKeyID() {
		return this.keyID;
	}

	/**
	 * Gets the Verification Code for the API key we are using
	 * 
	 * @return The VCode of the key
	 */
	public String getvCode() {
		return this.vCode;
	}

	/**
	 * @param keyID
	 *            The ID of the key to use
	 * @param vCode
	 *            The verification code for the key.
	 */
	public ApiKey(final long keyID, final String vCode) {
		super();
		this.keyID = keyID;
		this.vCode = vCode;
	}

	@Override
	public String toString() {
		return "ApiKey [keyID=" + this.keyID + ", vCode=" + this.vCode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (this.keyID ^ (this.keyID >>> 32));
		result = prime * result + ((this.vCode == null) ? 0 : this.vCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof ApiKey))
			return false;

		return this.keyID == ((ApiKey) obj).keyID && Objects.equals(this.vCode, ((ApiKey) obj).vCode);
	}
}
