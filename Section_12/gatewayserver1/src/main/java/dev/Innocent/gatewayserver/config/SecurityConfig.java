package dev.Innocent.gatewayserver.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity){
        serverHttpSecurity.authorizeExchange(exchange -> exchange.pathMatchers(HttpMethod.GET).permitAll()
                .pathMatchers("/udobank/accounts/**").hasRole("ACCOUNTS")
                .pathMatchers("/udobank/card/**").hasRole("CARD")
                .pathMatchers("/udobank/loan/**").hasRole("LOAN"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(Customizer.withDefaults()));
        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());
        return serverHttpSecurity.build();
    }
}
