package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "tutor_id", nullable = false)
  private Tutor tutor;

  @ManyToOne
  @JoinColumn(name = "student_id", nullable = false)
  private Student student;

  @Column(name = "rating")
  private Double rating;

  @Column(name = "comment")
  private String comment;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "created_at")
  private Timestamp createdAt;

}


