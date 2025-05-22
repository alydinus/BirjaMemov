package kg.alatoo.labor_exchange.service.impl;

import kg.alatoo.labor_exchange.entity.Ad;
import kg.alatoo.labor_exchange.entity.Tutor;
import kg.alatoo.labor_exchange.exception.exceptions.NotFoundException;
import kg.alatoo.labor_exchange.payload.request.AdRequest;
import kg.alatoo.labor_exchange.repository.AdRepository;
import kg.alatoo.labor_exchange.service.AdService;
import kg.alatoo.labor_exchange.service.TutorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final TutorService tutorService;

    public Ad findById(UUID id) {
        return adRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad not found with id: " + id));
    }

    public List<Ad> findAll() {
        return adRepository.findAll();
    }

    public void save(AdRequest request) {
        Ad ad = new Ad();
        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));
        ad.setTutor(tutor);
        ad.setTitle(request.title());
        ad.setDescription(request.description());
        ad.setLessonName(request.lessonName());
        ad.setHourlyPay(request.hourlyPay());
    }

    public void update(UUID id, AdRequest request) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new NotFoundException("Ad not found with id: " + id));

        Tutor tutor = tutorService.getTutorById(UUID.fromString(request.tutorId()));

        ad.setTutor(tutor);
        ad.setTitle(request.title());
        ad.setDescription(request.description());
        ad.setLessonName(request.lessonName());
        ad.setHourlyPay(request.hourlyPay());
        adRepository.save(ad);
    }

    public void deleteById(UUID id) {
        adRepository.deleteById(id);
    }
}
