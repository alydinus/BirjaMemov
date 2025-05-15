package kg.alatoo.labor_exchange.payload.response;

public record TutorResponse(
        String email,
        String firstName,
        String lastName,
        String description,
        Integer experienceYears,
        String imageUrl
) {

}
