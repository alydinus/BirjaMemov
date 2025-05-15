package kg.alatoo.labor_exchange.controller;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.payload.response.TutorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tutors")
@RequiredArgsConstructor
public class TutorController {

  @GetMapping
  public ResponseEntity<List<TutorResponse>> getTutors() {
    return ResponseEntity.ok("Tutors");
  }

  @GetMapping("/{id}")
  public ResponseEntity<TutorResponse> getTutorById(@PathVariable UUID id) {
    return ResponseEntity.ok("Tutor");
  }

  @PostMapping
  public ResponseEntity<TutorResponse> createTutor() {
    return ResponseEntity.ok("Tutor");
  }

  @PutMapping("/{id}")
  public ResponseEntity<TutorResponse> updateTutor(@PathVariable UUID id) {
    return ResponseEntity.ok("Tutor");
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTutor(@PathVariable UUID id) {
    return ResponseEntity.ok().build();
  }



}
