package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;

import java.util.List;
import java.util.UUID;

public interface SubjectService {

  void createSubject(SubjectRequest request);

  void updateSubject(UUID id, SubjectRequest request);

  void deleteSubject(UUID id);

  List<Subject> getAllSubjects();

  Subject getSubjectById(UUID id);

  Subject getSubjectByName(String name);

}
