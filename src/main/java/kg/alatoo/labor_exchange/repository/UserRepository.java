package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u from User u WHERE u.username = ?1")
    Optional<Object> findByUsername(String username);

    @Query("SELECT u from User u WHERE u.verificationCode = ?1")
    Optional<User> findByEmailToken(String token);

    @Query("SELECT u from User u WHERE u.email = ?1")
    Optional<User> findByEmail(String email);
}
