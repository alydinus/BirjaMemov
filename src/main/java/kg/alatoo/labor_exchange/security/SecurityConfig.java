package kg.alatoo.labor_exchange.security;


import kg.alatoo.labor_exchange.security.filter.AuthenticationFilter;
import kg.alatoo.labor_exchange.security.filter.AuthorizationFilter;
import kg.alatoo.labor_exchange.security.utils.CustomLoginFailureHandler;
import kg.alatoo.labor_exchange.security.utils.CustomOauthSuccesshandler;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtUtil jwtUtil;
  private final AuthenticationConfiguration authenticationConfiguration;
  private final CustomLoginFailureHandler customLoginFailureHandler;
  private final CustomOauthSuccesshandler customOauthSuccesshandler;

  @Bean
  public UserDetailsService userDetailsService(DataSource dataSource) {
    return new JdbcUserDetailsManager(dataSource);
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http, DataSource dataSource)
      throws Exception {

    AuthenticationFilter customAuthenticationFilter = new AuthenticationFilter(jwtUtil,
        authenticationManager(authenticationConfiguration,
            daoAuthenticationProvider(userDetailsService(dataSource), bCryptPasswordEncoder())));
    AuthorizationFilter customAuthorizationFilter = new AuthorizationFilter(jwtUtil);

    http.cors((cors -> cors.configurationSource(corsConfigurationSource())));

    http.csrf(csrf -> csrf
        .ignoringRequestMatchers("/oauth2/**","/api/**", "/login","/login/**", "/verify/2fa", "/api/auth/**")
        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    http.headers(headers -> headers.frameOptions().disable());

    http.authorizeHttpRequests(request -> request

            .requestMatchers("/", "/registration/**", "/css/**", "/images/**",
                "/api/auth/**", "/login/**", "/oauth2/**", "/verify/**", "/auth/**", "/favicon/**")
            .permitAll()

            .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

            .requestMatchers(HttpMethod.GET, "/api/ads/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/api/subjects/**").permitAll()

            .anyRequest().authenticated()
    );

    http.oauth2Login(oauth2 -> oauth2

            .authorizationEndpoint(authorization -> authorization
                    .baseUri("/oauth2/authorization"))

            .redirectionEndpoint(redirection -> redirection
                    .baseUri("/login/oauth2/code/*")
            )
            .successHandler(customOauthSuccesshandler)

//          .defaultSuccessUrl("http://localhost:8080/oauth-success", true)
        .failureHandler(customLoginFailureHandler));


    http.formLogin(formLogin -> formLogin
            .loginPage("/login")
            .defaultSuccessUrl("/")
            .failureHandler(customLoginFailureHandler)
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
