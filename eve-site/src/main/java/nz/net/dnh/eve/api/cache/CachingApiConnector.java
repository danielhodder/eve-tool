package nz.net.dnh.eve.api.cache;

import java.io.Serializable;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.connectors.CachingConnector;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiRequest;
import com.beimin.eveapi.core.ApiResponse;
import com.beimin.eveapi.exception.ApiException;

/**
 * Extends the idea of the {@link CachingConnector} but uses the cache policy
 * from the API. This means that we inspect the {@code cachedUntil} field of the
 * {@link ApiResponse} to determine for how long the request is valid. The cache
 * is keyed by the {@link ApiRequest} that is used to make the request.
 * 
 * @author Daniel Hodder (danielh)
 * 
 */
public class CachingApiConnector extends ApiConnector {
	// NOTE: This is a non-threadsafe collection so the class methods must be
	// synchronized
	private final SortedMap<ApiRequestKey, ApiResponse> inMemoryRequestCache = new TreeMap<>();
	private final ApiConnector backingConnector;

	public CachingApiConnector(final ApiConnector backingConnector) {
		this.backingConnector = backingConnector;
	}

	@Override
	public synchronized <E extends ApiResponse> E execute(final ApiRequest request, final AbstractContentHandler contentHandler,
			final Class<E> clazz) throws ApiException {
		removeExpiredItemsFromCache();

		return this.backingConnector.execute(request, contentHandler, clazz);
	}

	@Override
	public ApiConnector getInstance() {
		return this;
	}

	/**
	 * Removes all the expired elements from a cache. Because the
	 * {@link ApiRequestKey} is ordered by the {@code cacheUntil}
	 */
	private void removeExpiredItemsFromCache() {

	}

	/**
	 * A custom wrapper around the ApiRequest since it doesn't have a sensible
	 * equality/hashcode implementation or allow us to sort our keys by version.
	 * <p>
	 * An ApiRequestKey must be ordered by the time the response expires.
	 * 
	 * @author Daniel Hodder (danielh)
	 * 
	 */
	protected static class ApiRequestKey implements Comparable<ApiRequestKey>, Serializable {
		private final ApiRequest request;
		private final Date cacheUntil;

		protected ApiRequestKey(final ApiRequest request, final ApiResponse response) {
			this.request = request;
			this.cacheUntil = response.getCachedUntil();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(final ApiRequestKey o) {
			return this.request.compareTo(this.request);
		}
	}
}
