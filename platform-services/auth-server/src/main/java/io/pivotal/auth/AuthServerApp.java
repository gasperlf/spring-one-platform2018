package io.pivotal.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@EnableAuthorizationServer
@SpringBootApplication
public class AuthServerApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApp.class, args);
    }

    @Bean
    AuthorizationServerConfigurer authServerConfig() {
        return new AuthorizationServerConfigurerAdapter() {
            @Override
            public void configure(AuthorizationServerSecurityConfigurer security) {
                security.checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')");
            }

            @Override
            public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                clients.inMemory()
                        .withClient("registration-server")
                        .secret("secret")
                        .authorizedGrantTypes("client_credentials")
                        .authorities("ROLE_TRUSTED_CLIENT")
                        .and()
                        .withClient("timesheets-server")
                        .secret("secret")
                        .authorizedGrantTypes("client_credentials")
                        .scopes("project.read");
            }

        };
    }
}
