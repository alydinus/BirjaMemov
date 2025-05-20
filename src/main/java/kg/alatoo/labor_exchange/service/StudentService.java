package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Student;

import java.util.UUID;

public interface StudentService {
    Student getStudentById(UUID id);
}
