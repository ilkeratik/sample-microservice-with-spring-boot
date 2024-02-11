package com.iky.travel.controller.travel;

import static com.iky.travel.constant.common.ApiPathConstants.API_V1_TRAVEL;

import com.iky.travel.exception.common.RedisException;
import com.iky.travel.service.travel.TravelService;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API_V1_TRAVEL)
public class TravelController {

  private final TravelService travelService;

  public TravelController(TravelService travelService) {
    this.travelService = travelService;
  }

  @GetMapping("popularDestinations")
  public ResponseEntity<Set<Object>> getPopularDestinations() {
    return ResponseEntity.ok(travelService.getMostQueriedCities(5));
  }

  @GetMapping("clearPopularDestinations")
  public ResponseEntity<String> clearPopularDestinations() {
    boolean result = travelService.clearPopularDestinations();
    if (result) {
      return ResponseEntity.ok("Successfully cleared popular destinations from Redis");
    } else {
      throw new RedisException("Error when clearing popular destinations from Redis");
    }
  }

  @GetMapping("allDestinations")
  public ResponseEntity<Set<Object>> getAllDestinations() {
    return ResponseEntity.ok(travelService.getAllCities());
  }
}
