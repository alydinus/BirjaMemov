package kg.alatoo.labor_exchange.controller;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.mapper.SubjectMapper;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;
import kg.alatoo.labor_exchange.payload.response.SubjectResponse;
import kg.alatoo.labor_exchange.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;
  private final SubjectMapper subjectMapper;


  @GetMapping
  public ResponseEntity<List<SubjectResponse>> getSubjects() {
    return new ResponseEntity<>(
        subjectMapper.toResponses(subjectService.getAllSubjects()),
        HttpStatus.OK
    );
  }

  @GetMapping("/{id}")
  public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable UUID id) {
    return new ResponseEntity<>(
        subjectMapper.toResponse(subjectService.getSubjectById(id)),
        HttpStatus.OK
    );
  }

  @PostMapping
  public ResponseEntity<String> createSubject(SubjectRequest request) {
    subjectService.createSubject(request);
    return new ResponseEntity<>("Subject created successfully", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<String> updateSubject(SubjectRequest request, @PathVariable UUID id) {
    subjectService.updateSubject(id, request);
    return new ResponseEntity<>("Subject updated successfully", HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSubject(@PathVariable UUID id) {
    subjectService.deleteSubject(id);
    return new ResponseEntity<>("Subject deleted successfully", HttpStatus.OK);
  }
}
