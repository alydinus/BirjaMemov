package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.repository.ReviewRepository;
import kg.alatoo.labor_exchange.service.ReviewService;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
}
