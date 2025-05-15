package kg.alatoo.labor_exchange.payload.response;

import java.sql.Timestamp;

public record ReviewResponse(
        String id,
        String tutorId,
        String studentId,
        Double rating,
        String comment,
        Timestamp createdAt
) {
}
