package kg.alatoo.labor_exchange.service;

import kg.alatoo.labor_exchange.entity.Ad;
import kg.alatoo.labor_exchange.payload.request.AdRequest;

import java.util.List;
import java.util.UUID;

public interface AdService {
    Ad findById(UUID id);
    List<Ad> findAll();
    void save(AdRequest request);
    void update(UUID id, AdRequest request);
    void deleteById(UUID id);
}
