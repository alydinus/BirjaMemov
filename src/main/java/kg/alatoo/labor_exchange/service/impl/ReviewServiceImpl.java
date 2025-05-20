package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.ReviewRequest;
import kg.alatoo.labor_exchange.payload.request.ReviewUpdateRequest;
import kg.alatoo.labor_exchange.repository.ReviewRepository;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.ReviewService;
import kg.alatoo.labor_exchange.service.StudentService;
import kg.alatoo.labor_exchange.service.TutorService;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final TutorService tutorService;
    private final StudentService studentService;

    public ReviewServiceImpl(ReviewRepository reviewRepository, TutorService tutorService, StudentService studentService) {
        this.reviewRepository = reviewRepository;

        this.tutorService = tutorService;
        this.studentService = studentService;
    }

    @Override
    public Optional<Review> findById(UUID id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Boolean save(ReviewRequest request) {
        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));
        Student student = studentService.getStudentById(UUID.fromString(request.studentId()));

        Review review = new Review();
        review.setId(UUID.randomUUID());
        review.setTutor(tutor);
        review.setStudent(student);
        review.setRating(request.rating());
        review.setComment(request.comment());
        review.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        review.setIsActive(true);
        reviewRepository.save(review);
        return true;
    }

    @Override
    public void update(UUID id,ReviewUpdateRequest request) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);
        if(reviewOpt.isEmpty()) {
            throw new RuntimeException("No such Review found");
        }

        Review review = reviewOpt.get();
        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));
        Student student = studentService.getStudentById(UUID.fromString(request.studentId()));

        review.setStudent(student);
        review.setTutor(tutor);
        review.setRating(request.rating());
        review.setComment(request.comment());
        review.setIsActive(request.isActive());
        reviewRepository.save(review);
    }
}
