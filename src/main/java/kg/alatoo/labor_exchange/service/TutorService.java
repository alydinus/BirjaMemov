package kg.alatoo.labor_exchange.service;

import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.TutorCreateRequest;
import org.springframework.web.multipart.MultipartFile;

public interface TutorService {

  void createTutor(TutorCreateRequest tutorCreateRequest, MultipartFile profilePicture);

  void updateTutor(UUID id, TutorCreateRequest tutorCreateRequest, MultipartFile profilePicture);

  void deleteTutor(UUID id);

  Tutor getTutorByUsername(String username);

  Tutor getTutorById(UUID id);

  List<Tutor> getAllTutors();

  List<Tutor> searchTutors(String query);

}
