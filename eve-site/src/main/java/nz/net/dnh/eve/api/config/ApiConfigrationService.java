/**
 * 
 */
package nz.net.dnh.eve.api.config;

import org.springframework.stereotype.Component;

/**
 * Contains the information required to
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
@Component
public interface ApiConfigrationService {
	/**
	 * Returns the representation of the corporation API Key for the current context
	 * 
	 * @return The current corporation
	 */
	ApiKey getCorporationApiKey();
}
