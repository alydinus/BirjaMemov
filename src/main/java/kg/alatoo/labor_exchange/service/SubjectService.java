package kg.alatoo.labor_exchange.service;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.request.SubjectCreateRequest;
import kg.alatoo.labor_exchange.payload.request.SubjectUpdateRequest;

public interface SubjectService {

    void createSubject(SubjectCreateRequest request);

    void updateSubject(UUID id, SubjectUpdateRequest request);

    void deleteSubject(UUID id);

    List<Subject> getAllSubjects();

    Subject getSubjectById(UUID id);

}
