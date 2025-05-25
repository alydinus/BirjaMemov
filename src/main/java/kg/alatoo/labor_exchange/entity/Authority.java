package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
@Data
public class Authority {
    @Id
    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Id
    @Column(name = "authority", length = 50, nullable = false)
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", insertable = false, updatable = false)
    private User user;

    public Authority() {
    }

}