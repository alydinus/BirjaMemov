package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubjectService {
    Optional<Subject> findById(UUID id);
    List<Subject> findAll();
    Boolean save(SubjectRequest request);
    void update(UUID id, SubjectRequest request);
//    Boolean deleteById(UUID id);
}
