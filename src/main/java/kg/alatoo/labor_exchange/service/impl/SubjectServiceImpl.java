package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;
import kg.alatoo.labor_exchange.repository.SubjectRepository;
import kg.alatoo.labor_exchange.service.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;


    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Optional<Subject> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Subject> findAll() {
        return List.of();
    }

    @Override
    public Boolean save(SubjectRequest request) {
        return null;
    }

    @Override
    public void update(UUID id, SubjectRequest request) {

    }

}
