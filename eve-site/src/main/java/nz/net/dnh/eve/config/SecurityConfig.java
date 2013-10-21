package nz.net.dnh.eve.config;

import static org.springframework.http.HttpMethod.POST;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// Set to 30 days
	private static final int RememberMeTokenValifityTime = 3600 * 24 * 30;

	@Override
	protected void registerAuthentication(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/resources/**");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
						.antMatchers(POST, "/j_spring_security_check").permitAll()
						.anyRequest().authenticated()
						.and()
				.formLogin()
						.loginPage("/login")
							.defaultSuccessUrl("/")
						.permitAll().and()
				.rememberMe()
						.tokenValiditySeconds(RememberMeTokenValifityTime);
	}
}