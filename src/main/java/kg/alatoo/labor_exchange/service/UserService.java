package kg.alatoo.labor_exchange.service;

import java.util.Map;
import kg.alatoo.labor_exchange.entity.User;

public interface UserService {
    void updateRefresh(Map<String, String> map);
    User getUserByUsername(String username);
}
