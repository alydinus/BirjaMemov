package kg.alatoo.labor_exchange.payload.response;

public record TutorResponse(
//    String id,
    String email,
    String firstName,
    String lastName,
    String username,
    String description,
    Integer experienceYears,
    String imageUrl
) {

}
