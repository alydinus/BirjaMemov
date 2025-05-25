package kg.alatoo.labor_exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

//@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
@Data
public class Student extends User{

    @OneToMany(mappedBy = "tutor")
    private List<Review> reviews;

}
