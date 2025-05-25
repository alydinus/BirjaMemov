package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s from Student s WHERE s.username = ?1")
    Optional<Student> findByUsername(String username);
}
