package nz.net.dnh.eve.api.cache;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.beimin.eveapi.connectors.ApiConnector;
import com.beimin.eveapi.core.AbstractContentHandler;
import com.beimin.eveapi.core.ApiAuth;
import com.beimin.eveapi.core.ApiPage;
import com.beimin.eveapi.core.ApiPath;
import com.beimin.eveapi.core.ApiRequest;
import com.beimin.eveapi.core.ApiResponse;
import com.beimin.eveapi.exception.ApiException;

public class CachingApiConnectorTest {
	@Test
	@SuppressWarnings("unchecked")
	public void simpleRequestOnlyCallsBackendOnce() throws ApiException {
		final ApiConnector backingConnector = mock(ApiConnector.class);
		final ApiRequest request = new ApiRequest(ApiPath.NONE, ApiPage.CALL_LIST, 0, mock(ApiAuth.class));
		when(backingConnector.execute(eq(request), any(AbstractContentHandler.class), any(Class.class))).thenReturn(mock(ApiResponse.class));

		final CachingApiConnector cachingConnector = new CachingApiConnector(backingConnector);

		cachingConnector.execute(request, null, null);
		cachingConnector.execute(request, null, null);

		verify(backingConnector).execute(eq(request), any(AbstractContentHandler.class), any(Class.class));
	}
}