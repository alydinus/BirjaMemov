package kg.alatoo.labor_exchange.controller;

import java.util.UUID;
import kg.alatoo.labor_exchange.payload.request.SubjectCreateRequest;
import kg.alatoo.labor_exchange.payload.request.SubjectUpdateRequest;
import kg.alatoo.labor_exchange.payload.response.SubjectResponse;
import kg.alatoo.labor_exchange.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
public class SubjectController {

  private final SubjectService subjectService;

  public ResponseEntity<SubjectResponse> getSubjects() {
    return new ResponseEntity<SubjectResponse>(
        subjectService.getSubjects(), HttpStatus.OK);
  }

  public ResponseEntity<String> createSubject(SubjectCreateRequest request) {
    subjectService.createSubject(request);
    return new ResponseEntity<>("Subject created successfully", HttpStatus.CREATED);
  }

  public ResponseEntity<String> updateSubject(SubjectUpdateRequest request, UUID id) {
    subjectService.updateSubject(id, request);
    return new ResponseEntity<>("Subject updated successfully", HttpStatus.OK);
  }

  public ResponseEntity<String> deleteSubject(UUID id) {
    subjectService.deleteSubject(id);
    return new ResponseEntity<>("Subject deleted successfully", HttpStatus.OK);
  }
}
