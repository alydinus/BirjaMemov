package kg.alatoo.labor_exchange.mapper;

import kg.alatoo.labor_exchange.entity.Subject;
import kg.alatoo.labor_exchange.payload.response.SubjectResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SubjectMapper {
    public SubjectResponse toResponse(Subject subject) {
        SubjectResponse response = new SubjectResponse(
                subject.getId().toString(),
                subject.getTutor().getId().toString(),
                subject.getTitle(),
                subject.getLessonName(),
                subject.getHourlyPay(),
                subject.getDescription(),
                subject.getCreatedAt(),
                subject.getIsActive());
        return response;
    }

    public List<SubjectResponse> toResponses(List<Subject> subjects) {
        List<SubjectResponse> responses = new ArrayList<>();
        for(Subject subject : subjects) {
            responses.add(toResponse(subject));
        }
        return responses;
    }
}
