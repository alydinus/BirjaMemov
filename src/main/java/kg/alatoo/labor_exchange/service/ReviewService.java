package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.payload.request.ReviewRequest;
import kg.alatoo.labor_exchange.payload.request.ReviewUpdateRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewService {
    Review findById(UUID id);
    List<Review> findAll();
    void save(ReviewRequest request);
    void update(UUID id,ReviewUpdateRequest request);
//    Boolean deleteById(UUID id);

}
