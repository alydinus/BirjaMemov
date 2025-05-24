package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Student;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.payload.request.StudentRequest;
import kg.alatoo.labor_exchange.repository.StudentRepository;
import kg.alatoo.labor_exchange.service.StorageService;
import kg.alatoo.labor_exchange.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StorageService fileSystemStorageService;

    @Override
    public Student getStudentById(UUID id) {
        return studentRepository.findById(id).get();
    }

    private Student createStudent(StudentRequest studentRequest) {
        Student student = new Student();
        student.setEnabled(true);
        student.setFirstName(studentRequest.firstName());
        student.setLastName(studentRequest.lastName());
        student.setUsername(studentRequest.username());
        student.setEmail(studentRequest.email());
        student.setPassword(studentRequest.password());
        student.setRole(Role.STUDENT);
        student.setReviews(new ArrayList<>());
        student.setCreatedAt(LocalDateTime.now());
        return student;
    }

    private void storeProfilePictureAndAddToDatabase(Student student, MultipartFile profilePicture) {
        fileSystemStorageService.storeProfilePicture(student.getId().toString(), profilePicture);
        String extension = Objects.requireNonNull(profilePicture.getOriginalFilename()).substring(
                profilePicture.getOriginalFilename().lastIndexOf("."));
        student.setProfileImageUrl(student.getId().toString() + extension);
        studentRepository.save(student);
    }


    public void createStudent(StudentRequest studentRequest, MultipartFile profilePicture) {
        Student student = createStudent(studentRequest);
        studentRepository.save(student);

        if (profilePicture != null) {
            storeProfilePictureAndAddToDatabase(student, profilePicture);
    }

    }
}
