package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface TutorService {

  void createTutor(TutorRequest tutorRequest, MultipartFile profilePicture);

  Tutor updateTutor(UUID id, TutorRequest tutorRequest, MultipartFile profilePicture);

  void deleteTutor(UUID id);

  Tutor getTutorByUsername(String username);

  Tutor getTutorById(UUID id);

  List<Tutor> getAllTutors();

  List<Tutor> searchTutors(String query);

}
