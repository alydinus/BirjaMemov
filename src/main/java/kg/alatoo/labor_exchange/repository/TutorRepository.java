package kg.alatoo.labor_exchange.repository;

import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, UUID> {

}
