package com.test.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value(value = "${security.oauth2.client-id}")
	private String CLIEN_ID;
	@Value(value = "${security.oauth2.client-secret}")
	private String CLIENT_SECRET;
	@Value(value = "${security.oauth2.grant-type-password}")
	private String GRANT_TYPE_PASSWORD;
	@Value(value = "${security.oauth2.authorization-code}")
	private String AUTHORIZATION_CODE;
	@Value(value = "${security.oauth2.refresh-token}")
	private String REFRESH_TOKEN;
	@Value(value = "${security.oauth2.implicit}")
	private String IMPLICIT;
	@Value(value = "${security.oauth2.scope-read}")
	private String SCOPE_READ;
	@Value(value = "${security.oauth2.scope-write}")
	private String SCOPE_WRITE;
	@Value(value = "${security.oauth2.trust}")
	private String TRUST; 
	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 3600; 
	static final int FREFRESH_TOKEN_VALIDITY_SECONDS = 216000;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {

		configurer.inMemory().withClient(CLIEN_ID).secret(CLIENT_SECRET)
				.authorizedGrantTypes(GRANT_TYPE_PASSWORD, AUTHORIZATION_CODE, REFRESH_TOKEN, IMPLICIT)
				.scopes(SCOPE_READ, SCOPE_WRITE, TRUST).accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
				.refreshTokenValiditySeconds(FREFRESH_TOKEN_VALIDITY_SECONDS);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
		endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter).tokenEnhancer(enhancerChain)
				.authenticationManager(authenticationManager);
	}
}