package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    //tutor

    @Column(name = "rating")
    private Double rating;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private Timestamp createdAt;

}

