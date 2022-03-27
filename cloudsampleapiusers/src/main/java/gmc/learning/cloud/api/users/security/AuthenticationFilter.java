package gmc.learning.cloud.api.users.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import gmc.learning.cloud.api.users.service.UsersService;
import gmc.learning.cloud.api.users.shared.UserDto;
import gmc.learning.cloud.api.users.ui.models.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	String signingKeyB64 = Base64.getEncoder().encodeToString("signingKey".getBytes(StandardCharsets.UTF_8));

	private final UsersService usersService;
	private final Environment environment;

	public AuthenticationFilter(UsersService usersService, Environment environment, AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
		this.usersService = usersService;
		this.environment = environment;
	}
	

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		log.debug(environment.getProperty("token.expiration_time"));
		log.debug(signingKeyB64);
		
		try {
			
			LoginRequestModel creds = new ObjectMapper()
					.readValue(request.getInputStream(), LoginRequestModel.class);
			
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getEmail(),
							creds.getPassword(),
							new ArrayList<>())
			);
			
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	
	}
	
	@Override 
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		
		String userName = ((User) auth.getPrincipal()).getUsername();
		
		UserDto userDetails = usersService.getUserDetailsByEmail(userName);
		
		log.debug(environment.getProperty("token.expiration_time"));
		log.debug(signingKeyB64);

		String token = Jwts.builder()
				.setSubject(userDetails.getUserId())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(environment.getProperty("token.expiration_time"))))
//				.signWith(SignatureAlgorithm.HS512, environment.getProperty("token.secret"))
				.signWith(SignatureAlgorithm.HS512, signingKeyB64)
				.compact();
		
		res.addHeader("userId", userDetails.getUserId());
		res.addHeader("token", token);
	
	}

}
