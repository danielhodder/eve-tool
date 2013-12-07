/**
 * 
 */
package nz.net.dnh.eve.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 
 * @author Daniel Hodder (danielh)
 *
 */
@Configuration
public class BeforeAfterMethodInterceptorConfiguration {
	@Bean
	public BeforeAfterRequestExecutionIntercepter beforeAfterRequestExecutionIntercepter() {
		return new BeforeAfterRequestExecutionIntercepter();
	}
}
