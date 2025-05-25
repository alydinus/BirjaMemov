package kg.alatoo.labor_exchange.service.impl;

import jakarta.persistence.Column;
import kg.alatoo.labor_exchange.entity.Authority;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.enumeration.Role;
import kg.alatoo.labor_exchange.exception.exceptions.UserNotFoundException;
import kg.alatoo.labor_exchange.payload.request.TutorRequest;
import kg.alatoo.labor_exchange.repository.TutorRepository;
import kg.alatoo.labor_exchange.service.EmailService;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TutorServiceImpl implements TutorService {

  private final TutorRepository tutorRepository;
  private final FileSystemStorageService fileSystemStorageService;
  private final SubjectServiceImpl subjectService;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;


  private Tutor createTutor(TutorRequest tutorRequest) {
    Tutor tutor = new Tutor();

//    if (!tutorRequest.subjects().isEmpty()) {
//      List<Subject> tutorSubjects = tutorRequest.subjects().stream()
//          .map(subjectService::getSubjectByName)
//          .toList();
//      tutor.setSubjects(tutorSubjects);
//    }

    tutor.setEnabled(true);
    tutor.setFirstName(tutorRequest.firstName());
    tutor.setLastName(tutorRequest.lastName());
    tutor.setUsername(tutorRequest.username());
    tutor.setEmail(tutorRequest.email());
    tutor.setPassword(passwordEncoder.encode(tutorRequest.password()));
    tutor.setDescription(tutorRequest.description());
    tutor.setExperienceYears(tutorRequest.experienceYears());
    tutor.setRole(Role.TUTOR);
    tutor.setCreatedAt(LocalDateTime.now());
    tutor.setSubjects(new ArrayList<>());

    Set<Authority> authorities = new HashSet<>();
    Authority authority;
    authority = new Authority();
    authority.setUsername(tutor.getUsername());
    authority.setAuthority("ROLE_TUTOR");
    authority.setUser(tutor);
    authorities.add(authority);
    tutor.setAuthorities(authorities);

    tutor.setIsEmailVerified(false);
    tutor.setIsTwoFactorAuthEnabled(false);
    tutor.setVerificationCode(UUID.randomUUID().toString());
    tutor.setVerificationCodeExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(2)));

    emailService.sendSimpleMail(tutorRequest.email(), "Verification",
            "Verification code: " + tutor.getVerificationCode().toString() +
                    "\n Link Front ... " +
                    "\n Link/Postman request: POST http://localhost:8081/api/auth/verify?token=" + tutor.getVerificationCode());

    return tutor;
  }

  private void storeProfilePictureAndAddToDatabase(Tutor tutor, MultipartFile profilePicture) {
//    fileSystemStorageService.storeProfilePicture(tutor.getId().toString(), profilePicture);
    fileSystemStorageService.storeProfilePicture(tutor.getUsername(), profilePicture);
    String extension = Objects.requireNonNull(profilePicture.getOriginalFilename()).substring(
        profilePicture.getOriginalFilename().lastIndexOf("."));
//    tutor.setProfileImageUrl(tutor.getId().toString() + extension);
    tutor.setProfileImageUrl(tutor.getUsername() + extension);
    tutorRepository.save(tutor);
  }


  public void createTutor(TutorRequest tutorRequest, MultipartFile profilePicture) {
    Tutor tutor = createTutor(tutorRequest);
    tutorRepository.save(tutor);

    if (profilePicture != null) {
      storeProfilePictureAndAddToDatabase(tutor, profilePicture);
    }

  }

  public Tutor updateTutor(String id, TutorRequest tutorRequest,
      MultipartFile profilePicture) {

    Tutor tutor = tutorRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    tutor.setFirstName(tutorRequest.firstName());
    tutor.setLastName(tutorRequest.lastName());
    tutor.setUsername(tutorRequest.username());
    tutor.setEmail(tutorRequest.email());
    tutor.setPassword(tutorRequest.password());
    tutor.setDescription(tutorRequest.description());
    tutor.setExperienceYears(tutorRequest.experienceYears());
    tutor.setRole(Role.TUTOR);
    tutor.setRating(-1D);
    tutorRepository.save(tutor);


    if (!profilePicture.isEmpty()) {
      storeProfilePictureAndAddToDatabase(tutor, profilePicture);
    }
    return tutor;

  }

  public void deleteTutor(String id) {
    if (!tutorRepository.existsById(id)) {
      throw new UserNotFoundException("User not found with id: " + id);
    }
    tutorRepository.deleteById(id);
  }

  public Tutor getTutorByUsername(String username) {
    return tutorRepository.findByUsername(username).orElseThrow(
        () -> new UserNotFoundException("User not found with username: " + username));
  }

  public Tutor getTutorById(String id) {
    return tutorRepository.findById(id).orElseThrow(() -> new UserNotFoundException(
        "User not found with id: " + id));
  }

  public List<Tutor> getAllTutors() {
    return tutorRepository.findAll();
  }

  public List<Tutor> searchTutors(String query) {
    return List.of();
  }

  public Tutor updateTutorSubject(String id, String subjectId) {
    return null;
  }
}
