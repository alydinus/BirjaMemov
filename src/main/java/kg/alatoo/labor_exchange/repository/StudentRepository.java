package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {
}
