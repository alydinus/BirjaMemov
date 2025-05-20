package kg.alatoo.labor_exchange.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Image;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.payload.request.TutorCreateRequest;
import kg.alatoo.labor_exchange.repository.ImageRepository;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TutorServiceImpl implements TutorService {

  private final TutorRepository tutorRepository;
  private final ImageRepository imageRepository;
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


  public void createTutor(TutorCreateRequest tutorCreateRequest, MultipartFile profilePicture,
      List<MultipartFile> certificates) {
    Tutor tutor = createTutor(tutorCreateRequest);
    tutorRepository.save(tutor);

    if (!profilePicture.isEmpty()) {
      fileSystemStorageService.storeProfilePicture(tutor.getId().toString(), profilePicture);
      tutor.setProfileImageUrl(tutor.getId().toString());
      tutorRepository.save(tutor);
    }

    if (!certificates.isEmpty()) {
      List<String> certificatesURLs = fileSystemStorageService.storeCertificates(
          tutor.getId().toString(),
          certificates);
      List<Image> images = new ArrayList<>();

      for (String url : certificatesURLs) {
        Image image = new Image();
        image.setImageUrl(url);
        image.setTutor(tutor);
        images.add(image);
        imageRepository.save(image);
      }
    }



  }

  public void updateTutor(UUID id, TutorCreateRequest tutorCreateRequest) {

  }

  public void deleteTutor(UUID id) {

  }

  public Tutor getTutorByUsername(String username) {
    return null;
  }

  public Tutor getTutorById(UUID id) {
    return null;
  }

  public List<Tutor> getAllTutors() {
    return List.of();
  }

  public List<Tutor> searchTutors(String query) {
    return List.of();
  }
}
