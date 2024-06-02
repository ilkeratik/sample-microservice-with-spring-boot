package com.iky.travel.controller.travel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iky.travel.domain.service.travel.TravelService;
import com.iky.travel.exception.common.RedisException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class TravelControllerTest {

  @Mock
  private TravelService travelService;

  @InjectMocks
  private TravelController travelController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testGetPopularDestinations() {
    // Mock data
    Set<Object> popularDestinations = Set.of("Paris", "Tokyo", "New York");
    when(travelService.getMostQueriedCities(3)).thenReturn(popularDestinations);

    // Call the method
    ResponseEntity<Set<Object>> response = travelController.getPopularDestinations();

    // Verify the result
    assertEquals(popularDestinations, response.getBody());
    verify(travelService, times(1)).getMostQueriedCities(3);
  }

  @Test
  void testClearPopularDestinations_Success() {
    when(travelService.clearPopularDestinations()).thenReturn(true);

    ResponseEntity<String> response = travelController.clearPopularDestinations();

    assertEquals("Successfully cleared popular destinations from Redis", response.getBody());
    verify(travelService, times(1)).clearPopularDestinations();
  }

  @Test
  void testClearPopularDestinations_Failure() {
    when(travelService.clearPopularDestinations()).thenReturn(false);

    try {
      travelController.clearPopularDestinations();
    } catch (RedisException e) {
      assertEquals("Error when clearing popular destinations from Redis", e.getMessage());
    }

    verify(travelService, times(1)).clearPopularDestinations();
  }

  @Test
  void testGetAllDestinations() {
    // Mock data
    Set<Object> allDestinations = Set.of("London", "Rome", "Sydney");
    when(travelService.getAllCities()).thenReturn(allDestinations);

    // Call the method
    ResponseEntity<Set<Object>> response = travelController.getAllDestinations();

    // Verify the result
    assertEquals(allDestinations, response.getBody());
    verify(travelService, times(1)).getAllCities();
  }
}
