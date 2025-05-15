package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;

    @Column(name = "title")
    private String title;

    @Column(name = "lesson_name")
    private String lesson_name;

    @Column(name = "hourly_pay")
    private Integer hourly_pay;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "is_active")
    private Boolean is_active;

}
