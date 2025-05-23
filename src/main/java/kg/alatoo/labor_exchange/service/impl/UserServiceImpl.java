package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.repository.StudentRepository;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final TutorRepository tutorRepository;
    private final StudentRepository studentRepository;

    public UserServiceImpl(TutorRepository tutorRepository, StudentRepository studentRepository) {
        this.tutorRepository = tutorRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void updateRefresh(Map<String, String> map) {
        Optional<Tutor> tutorOpt = tutorRepository.findByUsername(map.get("username"));
        Optional<Student> studentOpt = studentRepository.findByUsername(map.get("username"));
        if(tutorOpt.isPresent()) {
            Tutor tutor = tutorOpt.get();
            tutor.setRefreshToken(map.get("refresh_token"));
            tutorRepository.save(tutor);
        } else if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setRefreshToken(map.get("refresh_token"));
            studentRepository.save(student);
        } else{
            throw new RuntimeException("Refresh token was damaged");
        }


    }
}
