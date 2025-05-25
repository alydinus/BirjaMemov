package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.payload.request.StudentRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface StudentService {
    Student getStudentById(String id);
    void createStudent(StudentRequest studentRequest, MultipartFile profilePicture);
}
