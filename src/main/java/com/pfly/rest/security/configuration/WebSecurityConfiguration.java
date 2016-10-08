package com.pfly.rest.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelDecisionManagerImpl;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pfly.rest.security.AuthenticationTokenFilter;
import com.pfly.rest.security.EntryPointUnauthorizedHandler;
import com.pfly.rest.services.AccountService;
import com.pfly.rest.services.SecurityService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${security.auth_urls}")
    private String[] authUrls;
    @Value("${security.secured_urls}")
    private String[] securedUrls;
	
	@Autowired
	private EntryPointUnauthorizedHandler unauthorizedHandler;

	@Autowired
	private AccountService accountService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	public void configureAuthentication(
			AuthenticationManagerBuilder authenticationManagerBuilder)
			throws Exception {
		authenticationManagerBuilder.userDetailsService(accountService)
				.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean()
			throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter
				.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	@Bean
	public SecurityService securityService() {
		return this.securityService;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
			.csrf()
				.disable()
			.exceptionHandling()
			.authenticationEntryPoint(this.unauthorizedHandler)
				.and()
			.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				.antMatchers("/auth/**").permitAll()
				.anyRequest().authenticated()
				.and()
            .requiresChannel()
//                .antMatchers("/**").requires(ChannelDecisionManagerImpl.ANY_CHANNEL)
//                .antMatchers(securedUrls).requiresSecure()
                .anyRequest().requiresSecure()
                .and()
             .portMapper()			//maps the port 8080(http) to 8443(https)
				.http(8080).mapsTo(8443)
				.and()
            .addFilterBefore(authenticationTokenFilterBean(),
				UsernamePasswordAuthenticationFilter.class);
	}

}
