package kg.alatoo.labor_exchange.controller;

import jakarta.validation.Valid;
import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.mapper.SubjectMapper;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;
import kg.alatoo.labor_exchange.payload.response.SubjectResponse;
import kg.alatoo.labor_exchange.service.SubjectService;
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
@RequestMapping("/api/subject")
public class SubjectController {
    private static Logger log = LoggerFactory.getLogger(SubjectController.class);
    private final SubjectService subjectService;
    private final SubjectMapper mapper;

    public SubjectController(SubjectService subjectService, SubjectMapper subjectMapper) {
        this.subjectService = subjectService;
        this.mapper = subjectMapper;
    }




    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getSubject(
            @RequestParam(required = false) UUID id) {
        try {
            List<SubjectResponse> responses = new ArrayList<SubjectResponse>();

            if (id == null)
                responses.addAll(mapper.toResponses(subjectService.findAll()));
            else{
                Optional<Subject> subject = subjectService.findById(id);
                if(subject.isPresent()){
                    responses.add(mapper.toResponse(subject.get()));
                }
            }
            if (responses.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(responses, HttpStatus.OK);
        } catch (Exception e) {
            log.error("getSubject|SubjectControllerAPI: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createSubject(
            @RequestBody @Valid SubjectRequest request
    ) {
        try{
            if(subjectService.save(request)){
                return new ResponseEntity<>("subject created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create subject", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error("createSubject|SubjectControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Failed to create subject, Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateSubject(
            @RequestBody @Valid SubjectRequest request,
            @RequestParam UUID id) {
        try{
            Optional<Subject> subject = subjectService.findById(id);

            if(subject.isPresent()) {

                subjectService.update(id, request);
                return new ResponseEntity<>("subject was updated successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cannot find subject", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("updateSubject|SubjectControllerAPI: " + e.getMessage());
            return new ResponseEntity<>("Cannot update subject.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

//    @DeleteMapping
//    public ResponseEntity<String> deleteSubject(
//            @RequestParam UUID id) {
//        try {
//            if (subjectService.deleteById(id)) {
//                return new ResponseEntity<>("subject was deleted successfully.", HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>("Cannot find subject", HttpStatus.BAD_REQUEST);
//
//        } catch (Exception e) {
//            log.error("deleteSubject|SubjectControllerAPI: " + e.getMessage());
//            return new ResponseEntity<>("Cannot delete subject.", HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//    }
}
