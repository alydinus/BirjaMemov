package kg.alatoo.labor_exchange.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kg.alatoo.labor_exchange.config.StorageProperties;
import kg.alatoo.labor_exchange.entity.Image;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.payload.request.TutorCreateRequest;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class TutorServiceImpl implements TutorService {
  private final TutorRepository tutorRepository;
  private final FileSystemStorageService fileSystemStorageService;


  public void createTutor(TutorCreateRequest tutorCreateRequest, MultipartFile profilePicture, List<MultipartFile> certificates) {
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

    try {
      fileSystemStorageService.store(profilePicture);
      String filename = StringUtils.cleanPath(
          Objects.requireNonNull(profilePicture.getOriginalFilename()));
      System.out.println(filename);
      Path filePath = fileSystemStorageService.load(filename);
      System.out.println("file  = " + filePath);
      URL fileUrl = filePath.toUri().toURL();
      tutor.setProfileImageUrl(fileUrl.toExternalForm());
    } catch (MalformedURLException e) {
      throw new RuntimeException("Failed to get file URL", e);
    }

    for (MultipartFile certificate : certificates) {
      fileSystemStorageService.store(certificate);
      try {
        Image image = new Image();
        String imageURL = StringUtils.cleanPath(
            Objects.requireNonNull(certificate.getOriginalFilename()));
        Path imagePath = fileSystemStorageService.load(imageURL).normalize();
        String imageUrl = imagePath.toUri().toURL().toExternalForm();
        image.setImageUrl(imageURL);
        image.setTutor(tutor);
        tutor.getCertificates().add(image);
      } catch (IOException e) {
        throw new RuntimeException("Failed to get certificate URL", e);
      }
    }

    tutorRepository.save(tutor);

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
