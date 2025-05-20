package kg.alatoo.labor_exchange.repository;

import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {

}
