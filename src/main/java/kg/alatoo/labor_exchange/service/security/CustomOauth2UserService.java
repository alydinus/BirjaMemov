package kg.alatoo.labor_exchange.service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import kg.alatoo.labor_exchange.entity.Authority;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.repository.UserRepository;
import kg.alatoo.labor_exchange.security.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UserRepository userRepository;
    private final Logger log = LoggerFactory.getLogger(CustomOauth2UserService.class);
    private final JwtUtil jwtUtil;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

        if ("google".equals(registrationId)) {

            Optional<User> userOptional = userRepository.findByEmail(attributes.get("email").toString());
            User user;

            if(userOptional.isEmpty()){
                throw new OAuth2AuthenticationException("Invalid email address");
            } else{
                user = userOptional.get();
            }

            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            Map<String, Object> responseData = new HashMap<>();
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            Set<Authority> authoritiesuser = user.getAuthorities();
            for (Authority authority : authoritiesuser) {
                authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
            }

            String accessToken = jwtUtil.generateToken(authorities,user.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(authorities,user.getUsername());

            responseData.put("access_token", accessToken);
            responseData.put("refresh_token", refreshToken);
            responseData.put("username", user.getUsername());

            try{
                String jsonResponse = new ObjectMapper().writeValueAsString(responseData);
                response.getWriter().write(jsonResponse);
            } catch (Exception e){
                responseData.put("error", e.getMessage());
            }


        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                "email"
        );
    }

}


