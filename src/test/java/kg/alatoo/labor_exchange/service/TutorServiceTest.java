package kg.alatoo.labor_exchange.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.request.TutorCreateRequest;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.impl.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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


  @Test
  public void createTutor_shouldReturnCreatedTutor() throws IOException {

    fileSystemStorageService.init();

    TutorCreateRequest tutor = new TutorCreateRequest(
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

    byte[] certificate1Bytes = "fake certificate 1".getBytes();

    MultipartFile certificate1 = new MockMultipartFile(
        "certificate1",
        "certificate1.jpg",
        "image/jpeg",
        certificate1Bytes
    );

    byte[] certificate2Bytes = "fake certificate 2".getBytes();

    MultipartFile certificate2 = new MockMultipartFile(
        "certificate2",
        "certificate2.jpg",
        "image/jpeg",
        certificate2Bytes
    );


    byte[] savedProfilePicture = Files.readAllBytes(
        fileSystemStorageService.load(profilePicture.getOriginalFilename()));
    byte[] savedCertificate1 = Files.readAllBytes(
        fileSystemStorageService.load(certificate1.getOriginalFilename()));
    byte[] savedCertificate2 = Files.readAllBytes(
        fileSystemStorageService.load(certificate2.getOriginalFilename()));


    tutorService.createTutor(tutor, profilePicture, List.of(certificate1, certificate2));

    Optional<Tutor> createdTutor = tutorRepository.findByUsername(tutor.username());

    assert (createdTutor.isPresent());
    assert (createdTutor.get().getUsername().equals(tutor.username()));
    assertArrayEquals(profilePictureBytes, savedProfilePicture);
    assertArrayEquals(certificate1Bytes, savedCertificate1);
    assertArrayEquals(certificate2Bytes, savedCertificate2);



  }

}
