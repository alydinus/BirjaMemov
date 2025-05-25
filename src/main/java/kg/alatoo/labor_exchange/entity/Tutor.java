package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tutors")
@Data
public class Tutor extends User {

  @Column(name = "description", length = 500)
  private String description;

  @Column(name = "rating")
  private Double rating;

  @Column(name = "experience_years")
  private Integer experienceYears;

  @ManyToMany
  @JoinTable(
      name = "tutor_subjects",
      joinColumns = @JoinColumn(name = "tutor_id"),
      inverseJoinColumns = @JoinColumn(name = "subject_id")
  )
  private List<Subject> subjects;

}
