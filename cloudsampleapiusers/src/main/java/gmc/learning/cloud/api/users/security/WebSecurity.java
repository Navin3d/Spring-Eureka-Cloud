package gmc.learning.cloud.api.users.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import gmc.learning.cloud.api.users.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	
	private final Environment environment;
	private final UsersService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(Environment environment, UsersService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.environment = environment;
		this.userService = userService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/users/**").permitAll()
//				.antMatchers("/**").hasIpAddress(environment.getProperty("gateway.ip"))
			.and()
				.headers().frameOptions().disable()
			.and()
				.csrf().disable();
		
			http.addFilter(getAuthenticationFilter());
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authFilter = new AuthenticationFilter(userService, environment, authenticationManager());
//		authFilter.setAuthenticationManager(authenticationManager());
		authFilter.setFilterProcessesUrl(environment.getProperty("login.url.path"));
		return authFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
	}

}
