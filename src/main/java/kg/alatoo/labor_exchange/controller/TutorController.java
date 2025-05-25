package kg.alatoo.labor_exchange.controller;

import kg.alatoo.labor_exchange.mapper.TutorMapper;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import kg.alatoo.labor_exchange.payload.response.TutorResponse;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tutors")
@RequiredArgsConstructor
@Validated
public class TutorController {

  private final TutorService tutorService;
  private final TutorMapper tutorMapper;

  @GetMapping
  public ResponseEntity<List<TutorResponse>> getTutors() {
    return new ResponseEntity<>(tutorMapper.toResponseList(tutorService.getAllTutors()),
        HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TutorResponse> getTutorById(@PathVariable String id) {
    return new ResponseEntity<>(tutorMapper.toResponse(tutorService.getTutorById(id)),
        HttpStatus.OK);

  }

  @PostMapping
  public ResponseEntity<String> createTutor(@RequestBody TutorRequest tutorRequest,
      @RequestParam(required = false) MultipartFile profilePicture) {
    tutorService.createTutor(tutorRequest, profilePicture);
    return new ResponseEntity<>("Репетитор успешно создан!", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TutorResponse> updateTutor(@PathVariable String id,
      @RequestParam(required = false) MultipartFile profilePicture,
      @RequestBody TutorRequest tutorRequest) {
    return new ResponseEntity<>(tutorMapper.toResponse(
        tutorService.updateTutor(id, tutorRequest, profilePicture)), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTutor(@PathVariable String id) {
    tutorService.deleteTutor(id);
    return new ResponseEntity<>("Репетитор успешно удален!", HttpStatus.NO_CONTENT);
  }


}
