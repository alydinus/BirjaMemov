package kg.alatoo.labor_exchange.mapper;

import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.response.SubjectResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SubjectMapper {

  public SubjectResponse toResponse(Subject subject) {
    return new SubjectResponse(
        subject.getId().toString(),
        subject.getName()
    );
  }

  public List<SubjectResponse> toResponses(List<Subject> subjects) {
    return subjects.stream()
        .map(this::toResponse)
        .toList();
  }

}
