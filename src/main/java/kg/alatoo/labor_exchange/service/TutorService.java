package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TutorService {

  void createTutor(TutorRequest tutorRequest, MultipartFile profilePicture);

  Tutor updateTutor(String id, TutorRequest tutorRequest, MultipartFile profilePicture);

  void deleteTutor(String id);

  Tutor getTutorByUsername(String username);

  Tutor getTutorById(String id);

  List<Tutor> getAllTutors();

  List<Tutor> searchTutors(String query);

  Tutor updateTutorSubject(String id, String subjectId);

}
