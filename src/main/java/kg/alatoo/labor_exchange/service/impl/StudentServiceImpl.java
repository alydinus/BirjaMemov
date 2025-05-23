package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.repository.StudentRepository;
import kg.alatoo.labor_exchange.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).get();
    }
}
