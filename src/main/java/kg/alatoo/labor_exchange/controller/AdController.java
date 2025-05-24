package kg.alatoo.labor_exchange.controller;

import jakarta.validation.Valid;
import kg.alatoo.labor_exchange.mapper.AdMapper;
import kg.alatoo.labor_exchange.payload.request.AdRequest;
import kg.alatoo.labor_exchange.payload.response.AdResponse;
import kg.alatoo.labor_exchange.service.AdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ads")
@Validated
@RequiredArgsConstructor
public class AdController {

  private final AdService adService;
  private final AdMapper mapper;

  @GetMapping("/{id}")
  public ResponseEntity<AdResponse> getAdById(@PathVariable UUID id) {
    return new ResponseEntity<>(mapper.toResponse(adService.findById(id)), HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<AdResponse>> getAllAds() {
    return new ResponseEntity<>(mapper.toResponses(adService.findAll()), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<String> creagteAd(
      @RequestBody @Valid AdRequest request
  ) {
    adService.save(request);
    return new ResponseEntity<>("subject was created successfully.", HttpStatus.CREATED);
  }

  @PutMapping
  public ResponseEntity<String> updateAd(
      @RequestBody @Valid AdRequest request,
      @RequestParam UUID id) {
    adService.update(id, request);
    return new ResponseEntity<>("subject was updated successfully.", HttpStatus.OK);

  }

    @DeleteMapping
    public ResponseEntity<String> deleteAd(
            @RequestParam UUID id) {
        adService.deleteById(id);
        return new ResponseEntity<>("subject was deleted successfully.", HttpStatus.OK);
    }
}
