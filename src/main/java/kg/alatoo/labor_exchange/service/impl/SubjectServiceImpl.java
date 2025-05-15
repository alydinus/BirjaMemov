package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.repository.SubjectRepository;
import kg.alatoo.labor_exchange.service.SubjectService;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;


    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }
}
