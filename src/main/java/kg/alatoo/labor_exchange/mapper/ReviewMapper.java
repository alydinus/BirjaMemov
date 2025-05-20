package kg.alatoo.labor_exchange.mapper;

import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.payload.response.ReviewResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReviewMapper {

    public ReviewResponse toResponse(Review review) {
      return new ReviewResponse(
              review.getId().toString(),
              review.getTutor().getId().toString(),
              review.getStudent().getId().toString(),
              review.getRating(),
              review.getComment(),
              review.getCreatedAt());
    }

    public List<ReviewResponse> toResponses(List<Review> reviews) {
        List<ReviewResponse> responses = new ArrayList<>();
        for(Review review : reviews) {
            responses.add(toResponse(review));
        }
        return responses;
    }
}
