package kg.alatoo.labor_exchange.payload.response;

import java.sql.Timestamp;

public record AdResponse(
        String id,
        String tutorId,
        String title,
        String lessonName,
        Integer hourlyPay,
        String description,
        Timestamp createdAt,
        Boolean isActive
) {
}
