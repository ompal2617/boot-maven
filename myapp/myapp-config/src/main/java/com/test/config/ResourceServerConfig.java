package com.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
 
	@Value(value = "${security.oauth2.resource-id}")
	private String RESOURCE_ID;
	
	@Value("${security.master.flag}")
	private boolean masterFlag;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		System.out.println("masterFlag: "+masterFlag);
		if(masterFlag) {
        	http.authorizeRequests() 
	            .antMatchers("/role/**").permitAll()
	            .antMatchers("/user/**").permitAll();
        } else {
        	http.anonymous().disable()
	            .authorizeRequests()
	            .antMatchers(HttpMethod.OPTIONS, "/oauth/token").permitAll()
	            .antMatchers("/role/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
	            .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN")
	            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
        }
	}

}