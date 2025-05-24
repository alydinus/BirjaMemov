package kg.alatoo.labor_exchange.repository;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, UUID> {

  Subject findSubjectByName(String name);

}
