package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, String> {

  @Query("SELECT t from Tutor t WHERE t.username = ?1")
  Optional<Tutor> findByUsername(String username);

}
