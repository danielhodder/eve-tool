package nz.net.dnh.eve.api.cache;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.joda.time.DateTime;

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
	private final SortedMap<ApiRequestCacheKey, ApiResponse> inMemoryRequestCache = new TreeMap<>();
	private final ApiConnector backingConnector;

	public CachingApiConnector(final ApiConnector backingConnector) {
		this.backingConnector = backingConnector;
	}

	/**
	 * Checks if the request is in the cache. If the request is cached and has not expired then returns that response.
	 * Otherwise calls the {@link #backingConnector} and then caches it's response.
	 * 
	 * @param request
	 *            The request being made of the API
	 * @param contentHandler
	 *            The SAX handler that will be used to decode the response XML
	 * @param clazz
	 *            The type that the response should be.
	 * @param <ResponseType>
	 *            The type of {@link ApiResponse} that will be returned.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public synchronized <ResponseType extends ApiResponse> ResponseType execute(final ApiRequest request, final AbstractContentHandler contentHandler,
			final Class<ResponseType> clazz) throws ApiException {
		removeExpiredItemsFromCache();
		
		ResponseType response;
		final ApiRequestCacheKey cacheKey = new ApiRequestCacheKey(request);
		if ((response = (ResponseType) this.inMemoryRequestCache.get(cacheKey)) != null)
			return response;

		response = this.backingConnector.execute(request, contentHandler, clazz);
		this.inMemoryRequestCache.put(new ApiRequestCacheKey(request, response), response);

		return response;
	}

	@Override
	public ApiConnector getInstance() {
		return this;
	}

	/**
	 * Removes all the expired elements from a cache. Because the
	 * {@link ApiRequestCacheKey} is ordered by the {@code cacheUntil}
	 */
	private synchronized void removeExpiredItemsFromCache() {
		final Iterator<Entry<ApiRequestCacheKey, ApiResponse>> iterator = this.inMemoryRequestCache.entrySet().iterator();
		
		while (iterator.hasNext()) {
			final Entry<ApiRequestCacheKey, ApiResponse> cacheEntry = iterator.next();
			
			// Remove any expired entries
			if (DateTime.now().isAfter(cacheEntry.getKey().cacheUntil)) {
				iterator.remove();
			} else {
				// Because the map is ordered by expiry when we don't find any more expired entries we can stop looking.
				break;
			}
		}
	}

	/**
	 * A custom wrapper around the ApiRequest since it doesn't have a sensible equality/hashcode implementation or allow
	 * us to sort our keys by version.
	 * <p>
	 * An ApiRequestKey must be ordered by the time the response expires.
	 * <p>
	 * Two {@code ApiRequestCacheKey} instances are equal if their requests are equal but the implementation of
	 * comparable also takes into account the time when the request is cached until.
	 * 
	 * @author Daniel Hodder (danielh)
	 * 
	 */
	protected static class ApiRequestCacheKey implements Comparable<ApiRequestCacheKey>, Serializable {
		private final ApiRequest request;
		private final DateTime cacheUntil;

		protected ApiRequestCacheKey(final ApiRequest request, final ApiResponse response) {
			this.request = request;
			this.cacheUntil = new DateTime(response.getCachedUntil());
		}

		/**
		 * A convenience constructor that passes null as the cached until value.
		 * 
		 * @param request
		 *            The request information to cache.
		 */
		protected ApiRequestCacheKey(final ApiRequest request) {
			this.request = request;
			this.cacheUntil = null;
		}

		@Override
		public int hashCode() {
			return this.request.hashCode();
		}

		@Override
		public boolean equals(final Object obj) {
			if (!(obj instanceof ApiRequestCacheKey))
				return false;

			return this.request.equals(((ApiRequestCacheKey) obj).request);
		}

		@Override
		public int compareTo(final ApiRequestCacheKey o) {
			if (this.cacheUntil != null && o.cacheUntil != null) {
				final int cachedUntilComparason = this.cacheUntil.compareTo(o.cacheUntil);
				if (cachedUntilComparason != 0)
					return cachedUntilComparason;
			}

			return this.request.compareTo(o.request);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "ApiRequestKey [request=" + this.request + ", cacheUntil=" + this.cacheUntil + "]";
		}
	}
}
