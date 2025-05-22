package kg.alatoo.labor_exchange.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kg.alatoo.labor_exchange.entity.Ad;
import kg.alatoo.labor_exchange.mapper.AdMapper;
import kg.alatoo.labor_exchange.payload.request.AdRequest;
import kg.alatoo.labor_exchange.payload.response.AdResponse;
import kg.alatoo.labor_exchange.service.AdService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subject")
@Validated
@RequiredArgsConstructor
public class AdController {

  private static Logger log = LoggerFactory.getLogger(AdController.class);
  private final AdService adService;
  private final AdMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<AdResponse> getAdById(@PathVariable UUID id) {
    return new ResponseEntity<>(mapper.toResponse(adService.findById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<AdResponse>> getAllAds(
      @RequestParam(required = false) UUID id) {
    return new ResponseEntity<>(mapper.toResponses(adService.findAll()), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> createSubject(
      @RequestBody @Valid AdRequest request
  ) {
    adService.save(request);
    return new ResponseEntity<>("subject was created successfully.", HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<String> updateSubject(
      @RequestBody @Valid AdRequest request,
      @RequestParam UUID id) {
    adService.update(id, request);
    return new ResponseEntity<>("subject was updated successfully.", HttpStatus.OK);

  }

    @DeleteMapping
    public ResponseEntity<String> deleteSubject(
            @RequestParam UUID id) {
        adService.deleteById(id);
        return new ResponseEntity<>("subject was deleted successfully.", HttpStatus.OK);
    }
}
