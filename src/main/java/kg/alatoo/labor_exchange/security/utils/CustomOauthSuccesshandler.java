package kg.alatoo.labor_exchange.security.utils;

import io.jsonwebtoken.Jwt;
import kg.alatoo.labor_exchange.entity.Authority;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomOauthSuccesshandler implements AuthenticationSuccessHandler {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
         OAuth2User ouser = (OAuth2User)authentication.getPrincipal();
//        Map<String, Object> attrs = ouser.getAttributes();
//
//        User user = userRepository.findByEmail(attrs.get("email").toString()).get();
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        for(Authority role : user.getAuthorities()) {
//            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
//        }

//        String accessToken = jwtUtil.generateToken(authorities,user.getUsername());

        response.sendRedirect("http://localhost:8080/oauth-success?token=" + ouser.getAttributes().get("email"));
    }
}