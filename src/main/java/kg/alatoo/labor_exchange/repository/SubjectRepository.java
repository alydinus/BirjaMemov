package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
}
