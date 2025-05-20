package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Review;
import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.SubjectRequest;
import kg.alatoo.labor_exchange.repository.SubjectRepository;
import kg.alatoo.labor_exchange.service.SubjectService;
import kg.alatoo.labor_exchange.service.TutorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TutorService tutorService;


    public SubjectServiceImpl(SubjectRepository subjectRepository, TutorService tutorService) {
        this.subjectRepository = subjectRepository;
        this.tutorService = tutorService;
    }

    @Override
    public Optional<Subject> findById(UUID id) {
        return subjectRepository.findById(id);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Boolean save(SubjectRequest request) {
        Subject subject = new Subject();
        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));
        subject.setTutor(tutor);
        subject.setTitle(request.title());
        subject.setDescription(request.description());
        subject.setLessonName(request.lessonName());
        subject.setHourlyPay(request.hourlyPay());

    }

    @Override
    public void update(UUID id, SubjectRequest request) {
        Optional<Subject> subjectOpt = subjectRepository.findById(id);
        if(subjectOpt.isEmpty()) {
            throw new RuntimeException("No such Subject found");
        }
        Subject subject = subjectOpt.get();
        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));
        subject.setTutor(tutor);
        subject.setTitle(request.title());
        subject.setDescription(request.description());
        subject.setLessonName(request.lessonName());
        subject.setHourlyPay(request.hourlyPay());
        subjectRepository.save(subject);
    }

}
