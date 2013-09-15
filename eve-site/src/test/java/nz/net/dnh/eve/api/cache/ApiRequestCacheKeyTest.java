package nz.net.dnh.eve.api.cache;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import nz.net.dnh.eve.api.cache.CachingApiConnector.ApiRequestCacheKey;

import org.junit.Test;
import org.mockito.Mockito;

import com.beimin.eveapi.core.ApiRequest;
import com.beimin.eveapi.core.ApiResponse;

/**
 * 
 * @author Daniel Hodder (danielh)
 *
 */
public class ApiRequestCacheKeyTest {
	@Test
	public void equalsAndHashCodeAreNotDependentOnCachedUntil() {
		final ApiRequest request = mock(ApiRequest.class);
		
		final ApiRequestCacheKey key1 = new ApiRequestCacheKey(request, mock(ApiResponse.class, Mockito.RETURNS_DEEP_STUBS));
		final ApiRequestCacheKey key2 = new ApiRequestCacheKey(request, mock(ApiResponse.class, Mockito.RETURNS_DEEP_STUBS));
		
		assertTrue(key1.equals(key2));
		assertTrue(key1.hashCode() == key2.hashCode());
	}

	@Test
	public void compareToDependsOnTheResponseAsWellAsTheRequest() {
		final ApiRequest request = mock(ApiRequest.class);

		final ApiRequestCacheKey key1 = new ApiRequestCacheKey(request, mock(ApiResponse.class, Mockito.RETURNS_DEEP_STUBS));
		final ApiRequestCacheKey key2 = new ApiRequestCacheKey(request, mock(ApiResponse.class, Mockito.RETURNS_DEEP_STUBS));

		assertNotEquals(0, key1.compareTo(key2));
	}

	@Test
	public void compareToWithTheSameRequestAndResponseAreTheSame() {
		final ApiRequest request = mock(ApiRequest.class);
		final ApiResponse response = mock(ApiResponse.class, Mockito.RETURNS_DEEP_STUBS);

		final ApiRequestCacheKey key1 = new ApiRequestCacheKey(request, response);
		final ApiRequestCacheKey key2 = new ApiRequestCacheKey(request, response);

		assertNotEquals(0, key1.compareTo(key2));
	}
}
