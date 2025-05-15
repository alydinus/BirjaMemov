package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.payload.request.ReviewRequest;
import kg.alatoo.labor_exchange.payload.request.ReviewUpdateRequest;
import kg.alatoo.labor_exchange.repository.ReviewRepository;
import kg.alatoo.labor_exchange.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Optional<Review> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Review> findAll() {
        return List.of();
    }

    @Override
    public Boolean save(ReviewRequest request) {
        return null;
    }

    @Override
    public void update(UUID id,ReviewUpdateRequest request) {

    }
}
