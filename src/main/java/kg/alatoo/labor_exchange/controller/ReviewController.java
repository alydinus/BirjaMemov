package kg.alatoo.labor_exchange.controller;

import jakarta.validation.Valid;
import kg.alatoo.labor_exchange.mapper.ReviewMapper;
import kg.alatoo.labor_exchange.payload.request.ReviewRequest;
import kg.alatoo.labor_exchange.payload.request.ReviewUpdateRequest;
import kg.alatoo.labor_exchange.payload.response.ReviewResponse;
import kg.alatoo.labor_exchange.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
@Validated
public class ReviewController {
    
    private static Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.mapper = reviewMapper;
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponse>> getReviews() {
        return new ResponseEntity<>(mapper.toResponses(reviewService.findAll()), HttpStatus.OK);
    }

    @RequestMapping("/all")
    @GetMapping
    public ResponseEntity<ReviewResponse> getReviews(@RequestParam UUID id) {
        return new ResponseEntity<>(mapper.toResponse(reviewService.findById(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestBody @Valid ReviewRequest request) {
        reviewService.save(request);
        return new ResponseEntity<>("review created", HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> updateReview(
            @RequestBody @Valid ReviewUpdateRequest request,
            @RequestParam UUID id) {
        reviewService.update(id, request);
        return new ResponseEntity<>("review updated", HttpStatus.OK);
    }
}
