package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

  Subject findSubjectByName(String name);

}
