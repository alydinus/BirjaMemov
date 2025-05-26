package kg.alatoo.labor_exchange.service;

import java.util.Map;
import kg.alatoo.labor_exchange.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    void updateRefresh(Map<String, String> map);
    User getUserByUsername(String username);
    Map<String, Object> sideAuth(String email);
}
