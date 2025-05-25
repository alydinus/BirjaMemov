package kg.alatoo.labor_exchange.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kg.alatoo.labor_exchange.entity.User;
import kg.alatoo.labor_exchange.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserService userService;

    @Value("${JWT_SECRET}")
    private String jwtSecret;

    @Value("${JWT_EXPIRATION}")
    private long jwtExp;

    @Value("${JWT_REF_EXPIRATION}")
    private long jwtRefreshExp;



    public String generateToken(Collection<GrantedAuthority> authorities, String username) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = userService.getUserByUsername(username);

        claims.put("roles", roles);
        claims.put("type", user.getRole().toString());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExp))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(Collection<GrantedAuthority> authorities, String username) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        User user = userService.getUserByUsername(username);

        claims.put("roles", roles);
        claims.put("type", user.getRole().toString());

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExp))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

        userService.updateRefresh(Map.of("username", username, "refresh_token", refreshToken));
        return refreshToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("roles", List.class);
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Map<String,String> refresh(String token){
        if(validateToken(token)){
            String username = getUsernameFromToken(token);
            List<String> roles = getRolesFromToken(token);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            for(String role : roles){
                authorities.add(new SimpleGrantedAuthority(role));
            }

            String accessToken = generateToken(authorities,username);
            String refreshToken = generateRefreshToken(authorities,username);
            Map<String, String > map = Map.of(
                    "access_token", accessToken,
                    "refresh_token", refreshToken,
                    "username", username
            );

            userService.updateRefresh(map);

            return(map);
        } else{
            throw new RuntimeException("Refresh token is invalid");
        }
    }
}