package kg.alatoo.labor_exchange.payload.response;

import java.sql.Timestamp;

public record AdResponse(
        String id,
        String username,
        String title,
        String lessonName,
        Integer price,
        String description,
        Timestamp createdAt,
        String type
) {
}
