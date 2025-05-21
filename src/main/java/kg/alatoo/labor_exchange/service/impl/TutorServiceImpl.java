package kg.alatoo.labor_exchange.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.exception.exceptions.UserNotFoundException;
import kg.alatoo.labor_exchange.payload.request.TutorCreateRequest;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TutorServiceImpl implements TutorService {

  private final TutorRepository tutorRepository;
  private final FileSystemStorageService fileSystemStorageService;


  private Tutor createTutor(TutorCreateRequest tutorCreateRequest) {
    Tutor tutor = new Tutor();
    tutor.setFirstName(tutorCreateRequest.firstName());
    tutor.setLastName(tutorCreateRequest.lastName());
    tutor.setUsername(tutorCreateRequest.username());
    tutor.setEmail(tutorCreateRequest.email());
    tutor.setPassword(tutorCreateRequest.password());
    tutor.setDescription(tutorCreateRequest.description());
    tutor.setExperienceYears(tutorCreateRequest.experienceYears());
    tutor.setRole(Role.TUTOR);
    tutor.setCreatedAt(LocalDateTime.now());
    return tutor;
  }

  private void storeProfilePictureAndAddToDatabase(Tutor tutor, MultipartFile profilePicture) {
    fileSystemStorageService.storeProfilePicture(tutor.getId().toString(), profilePicture);
    String extension = Objects.requireNonNull(profilePicture.getOriginalFilename()).substring(
        profilePicture.getOriginalFilename().lastIndexOf("."));
    tutor.setProfileImageUrl(tutor.getId().toString() + extension);
    tutorRepository.save(tutor);
  }


  public void createTutor(TutorCreateRequest tutorCreateRequest, MultipartFile profilePicture) {
    Tutor tutor = createTutor(tutorCreateRequest);
    tutorRepository.save(tutor);

    if (!profilePicture.isEmpty()) {
      storeProfilePictureAndAddToDatabase(tutor, profilePicture);
    }

  }

  public void updateTutor(UUID id, TutorCreateRequest tutorCreateRequest,
      MultipartFile profilePicture) {

    Tutor tutor = tutorRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    tutor.setFirstName(tutorCreateRequest.firstName());
    tutor.setLastName(tutorCreateRequest.lastName());
    tutor.setUsername(tutorCreateRequest.username());
    tutor.setEmail(tutorCreateRequest.email());
    tutor.setPassword(tutorCreateRequest.password());
    tutor.setDescription(tutorCreateRequest.description());
    tutor.setExperienceYears(tutorCreateRequest.experienceYears());
    tutor.setRole(Role.TUTOR);
    tutorRepository.save(tutor);

    if (!profilePicture.isEmpty()) {
      storeProfilePictureAndAddToDatabase(tutor, profilePicture);
    }

  }

  public void deleteTutor(UUID id) {
    if (!tutorRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    tutorRepository.deleteById(id);
  }

  public Tutor getTutorByUsername(String username) {
    return tutorRepository.findByUsername(username).orElseThrow(
        () -> new UserNotFoundException("User not found with username: " + username));
  }

  public Tutor getTutorById(UUID id) {
    return tutorRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
        "User not found with id: " + id));
  }

  public List<Tutor> getAllTutors() {
    return tutorRepository.findAll();
  }

  public List<Tutor> searchTutors(String query) {
    return List.of();
  }
}
