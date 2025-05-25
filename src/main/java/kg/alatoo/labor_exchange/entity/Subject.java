package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "subjects")
@Data
public class Subject {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  private Tutor tutor;

  @Column(nullable = false, unique = true)
  private String name;

}
