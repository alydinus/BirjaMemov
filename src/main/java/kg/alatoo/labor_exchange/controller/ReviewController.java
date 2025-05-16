package kg.alatoo.labor_exchange.controller;

import jakarta.validation.Valid;
import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.mapper.ReviewMapper;
import kg.alatoo.labor_exchange.payload.request.ReviewRequest;
import kg.alatoo.labor_exchange.payload.request.ReviewUpdateRequest;
import kg.alatoo.labor_exchange.payload.response.ReviewResponse;
import kg.alatoo.labor_exchange.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    
    private static Logger log = LoggerFactory.getLogger(ReviewController.class);
    private final ReviewService reviewService;
    private final ReviewMapper mapper;

    public ReviewController(ReviewService reviewService, ReviewMapper reviewMapper) {
        this.reviewService = reviewService;
        this.mapper = reviewMapper;
    }
    
    


//    @GetMapping
//    public ResponseEntity<List<ReviewResponse>> getReview(
//            @RequestParam(required = false) UUID id) {
//        try {
//            List<ReviewResponse> responses = new ArrayList<ReviewResponse>();
//
//            if (id == null)
//                responses.addAll(mapper.toResponses(reviewService.findAll()));
//            else{
//                Optional<Review> review = reviewService.findById(id);
//                if(review.isPresent()){
//                    responses.add(mapper.toResponse(review.get()));
//                }
//            }
//            if (responses.isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//            return new ResponseEntity<>(responses, HttpStatus.OK);
//        } catch (Exception e) {
//            log.error("getReview|ReviewControllerAPI: " + e.getMessage());
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping
    public ResponseEntity<String> createReview(
            @RequestBody @Valid ReviewRequest request
    ) {
        try{
            if(reviewService.save(request)){
                return new ResponseEntity<>("review created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create review", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createReview|ReviewControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create review, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateReview(
            @RequestBody @Valid ReviewUpdateRequest request,
            @RequestParam UUID id) {
        try{
            Optional<Review> review = reviewService.findById(id);

            if(review.isPresent()) {

                reviewService.update(id, request);
                return new ResponseEntity<>("review was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find review", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateReview|ReviewControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot update review.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteReview(
//            @RequestParam UUID id) {
//        try {
//            if (reviewService.deleteById(id)) {
//                return new ResponseEntity<>("review was deleted successfully.", HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>("Cannot find review", HttpStatus.BAD_REQUEST);
//
//        } catch (Exception e) {
//            log.error("deleteReview|ReviewControllerAPI: " + e.getMessage());
//            return new ResponseEntity<>("Cannot delete review.", HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }
}
