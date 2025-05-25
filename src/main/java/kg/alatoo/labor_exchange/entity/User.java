package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import kg.alatoo.labor_exchange.enumeration.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class User {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "username", nullable = false, unique = true, length = 50)
  private String username;

  @Column(name = "email", nullable = false, length = 60)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "role", nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Ad> ads;

  @Column(name = "refresh_token")
  private String refreshToken;

  @Column(name = "first_name", nullable = false, length = 50)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 50)
  private String lastName;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Column(name = "profile_image_url")
  private String profileImageUrl;


  @Column(name = "enabled")
  private boolean isEnabled;

}
