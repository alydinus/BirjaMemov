package kg.alatoo.labor_exchange.mapper;

import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.payload.response.TutorResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TutorMapper {
  public TutorResponse toResponse(Tutor tutor) {
    return new TutorResponse(
        tutor.getId().toString(),
        tutor.getEmail(),
        tutor.getFirstName(),
        tutor.getLastName(),
        tutor.getUsername(),
        tutor.getDescription(),
        tutor.getExperienceYears(),
        tutor.getProfileImageUrl()
    );
  }

  public List<TutorResponse> toResponseList(List<Tutor> tutors) {
    return tutors.stream()
        .map(this::toResponse)
        .collect(Collectors.toList());
  }
}
