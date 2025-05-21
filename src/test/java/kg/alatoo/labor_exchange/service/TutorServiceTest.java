package kg.alatoo.labor_exchange.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.exception.exceptions.UserNotFoundException;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.impl.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@RequiredArgsConstructor
public class TutorServiceTest {

  @Autowired
  private TutorService tutorService;

  @Autowired
  private TutorRepository tutorRepository;

  @Autowired
  private FileSystemStorageService fileSystemStorageService;

  @Value("${storage.location}")
  private String rootLocation;

  @Test
  public void createTutor_shouldReturnCreatedTutor() throws IOException {

    fileSystemStorageService.init();

    TutorRequest tutor = new TutorRequest(
        "threenimak",
        "marsel@daun.com",
        "123",
        "sanjar",
        "minetov",
        "I am a tutor",
        5
    );

    byte[] profilePictureBytes = "fake profile picture".getBytes();

    MultipartFile profilePicture = new MockMultipartFile(
        "profilePicture",
        "profile.jpg",
        "image/jpeg",
        profilePictureBytes
    );

    tutorService.createTutor(tutor, profilePicture);

    Tutor createdTutor = tutorRepository.findByUsername(tutor.username()).orElseThrow(
        () -> new UserNotFoundException("User not found with username: " + tutor.username()));

    Path profilePicturePath = Paths.get(rootLocation).resolve(
        Paths.get("profile_pictures").resolve(
            createdTutor.getId() + ".jpg")).normalize().toAbsolutePath();
    byte[] savedProfilePicture = Files.readAllBytes(
        fileSystemStorageService.load(profilePicturePath.toString()));

    assert (createdTutor.getUsername().equals(tutor.username()));
    assertArrayEquals(profilePictureBytes, savedProfilePicture);

  }


}
