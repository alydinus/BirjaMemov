package kg.alatoo.labor_exchange.service.impl;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.exception.exceptions.NotFoundException;
import kg.alatoo.labor_exchange.payload.request.SubjectCreateRequest;
import kg.alatoo.labor_exchange.payload.request.SubjectUpdateRequest;
import kg.alatoo.labor_exchange.repository.SubjectRepository;
import kg.alatoo.labor_exchange.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

  private final SubjectRepository subjectRepository;

  public void createSubject(SubjectCreateRequest request) {
    Subject subject = new Subject();
    subject.setName(request.name());
    subjectRepository.save(subject);
  }

  public void updateSubject(UUID id, SubjectUpdateRequest request) {
    Subject subject = subjectRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Subject not found with id: " + id));
    subject.setName(request.name());
    subjectRepository.save(subject);
  }

  public void deleteSubject(UUID id) {
    if (!subjectRepository.existsById(id)) {
      throw new NotFoundException("Subject not found with id: " + id);
    }
    subjectRepository.deleteById(id);
  }

  public List<Subject> getAllSubjects() {
    return subjectRepository.findAll();
  }

  public Subject getSubjectById(UUID id) {
    return subjectRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Subject not found with id: " + id));
  }
}
