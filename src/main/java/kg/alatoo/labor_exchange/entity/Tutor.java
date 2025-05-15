package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

  @OneToMany(mappedBy = "tutor")
  private List<Image> images;

}
