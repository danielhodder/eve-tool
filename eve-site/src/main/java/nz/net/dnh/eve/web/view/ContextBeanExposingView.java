package nz.net.dnh.eve.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.ContextExposingHttpServletRequest;
import org.springframework.web.servlet.view.tiles3.TilesView;

public class ContextBeanExposingView extends TilesView {
	protected HttpServletRequest getRequestToExpose(
			final HttpServletRequest originalRequest) {
		return new ContextExposingHttpServletRequest(originalRequest,
				getWebApplicationContext(), null);
	}

	@Override
	protected void renderMergedOutputModel(final Map<String, Object> model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		final HttpServletRequest requestToExpose = getRequestToExpose(request);
		exposeModelAsRequestAttributes(model, requestToExpose);
		super.renderMergedOutputModel(model, requestToExpose, response);
	}
}
