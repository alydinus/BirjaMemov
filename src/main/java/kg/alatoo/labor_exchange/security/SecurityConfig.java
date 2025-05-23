package kg.alatoo.labor_exchange.security;


import kg.alatoo.labor_exchange.security.filter.AuthenticationFilter;
import kg.alatoo.labor_exchange.security.filter.AuthorizationFilter;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(JwtUtil jwtUtil, AuthenticationConfiguration authenticationConfiguration) {
        this.jwtUtil = jwtUtil;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager provider = new JdbcUserDetailsManager(dataSource);

        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource) throws Exception {

        AuthenticationFilter customAuthenticationFilter = new AuthenticationFilter(jwtUtil,authenticationManager(authenticationConfiguration,daoAuthenticationProvider(userDetailsService(dataSource),bCryptPasswordEncoder())));
        AuthorizationFilter customAuthorizationFilter = new AuthorizationFilter(jwtUtil);

        http.cors((cors -> cors.configurationSource(corsConfigurationSource())));

        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/**","/login","/verify/2fa")
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.headers(headers -> headers.frameOptions().disable());// не фикси е ба нат просто не трогай

        http.authorizeHttpRequests(request -> request


                .requestMatchers("/","/registration","/css/**", "/images/**",
                        "/api/auth/**", "/login/**", "/oauth2/**", "/verify/**").permitAll()

                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                .anyRequest().authenticated()
//                .anyRequest().permitAll()
        );

//        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/login")
                .authorizationEndpoint(authorization -> authorization
                        .baseUri("/oauth2/authorization"))
                .redirectionEndpoint(redirection -> redirection
                        .baseUri("/login/oauth2/code/*")
                )
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error"));

        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
        );

        http.logout(logout -> logout
                .permitAll()
        );

        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilter(customAuthenticationFilter);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,
                                                               BCryptPasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig,
            DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        return new ProviderManager(List.of(daoAuthenticationProvider));
    }
}
