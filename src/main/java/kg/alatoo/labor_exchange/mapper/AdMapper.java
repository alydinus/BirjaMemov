package kg.alatoo.labor_exchange.mapper;

import kg.alatoo.labor_exchange.entity.Ad;
import kg.alatoo.labor_exchange.payload.response.AdResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AdMapper {
    public AdResponse toResponse(Ad ad) {
        AdResponse response = new AdResponse(
                ad.getId().toString(),
                ad.getTutor().getId().toString(),
                ad.getTitle(),
                ad.getSubject().getName(),
                ad.getHourlyPay(),
                ad.getDescription(),
                ad.getCreatedAt());
        return response;
    }

    public List<AdResponse> toResponses(List<Ad> ads) {
        List<AdResponse> responses = new ArrayList<>();
        for(Ad ad : ads) {
            responses.add(toResponse(ad));
        }
        return responses;
    }
}
