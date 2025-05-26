package kg.alatoo.labor_exchange.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class AuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthorizationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println(request.getRequestURI());
        if (request.getServletPath().startsWith("/login") ||
            request.getServletPath().startsWith("/h2-console") ||
            request.getServletPath().startsWith("/auth") ||
            request.getServletPath().startsWith("/oauth2") ||
            (request.getServletPath().startsWith("/api/ads") && request.getMethod().equals("GET")) ||
            (request.getServletPath().startsWith("/api/subjects") && request.getMethod().equals("GET")) ||
            request.getServletPath().startsWith("/api/auth")
        ) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            try {
                if (authorizationHeader == null && request.getServletPath().startsWith("/api")) {
                    throw new ServletException("Authorization header is null");
                } else if(authorizationHeader == null){
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    String token = authorizationHeader.substring("Bearer ".length());

                    if (token != null && jwtUtil.validateToken(token)) {
                        String username = jwtUtil.getUsernameFromToken(token);
                        List<String> roles = jwtUtil.getRolesFromToken(token);
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                roles.stream().map(SimpleGrantedAuthority::new).toList()
                        );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        filterChain.doFilter(request, response);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.setHeader("error", e.getMessage());
                response.setStatus(HttpStatus.FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
    }
}
