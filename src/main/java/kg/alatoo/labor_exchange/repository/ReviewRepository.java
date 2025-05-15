package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}
