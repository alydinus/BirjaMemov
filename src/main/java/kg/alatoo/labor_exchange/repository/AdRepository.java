package kg.alatoo.labor_exchange.repository;

import kg.alatoo.labor_exchange.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdRepository extends JpaRepository<Ad, UUID> {
}
