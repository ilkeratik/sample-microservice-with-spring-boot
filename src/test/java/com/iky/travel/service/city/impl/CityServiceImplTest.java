package com.iky.travel.service.city.impl;

import static com.iky.travel.constant.common.RedisConstant.CITY_KEY;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.iky.travel.domain.dto.CityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

class CityServiceImplTest {

  RedisTemplate<String, Object> redisTemplate;
  SetOperations<String, Object> setOperations;
  CityServiceImpl cityService;

  @BeforeEach
  void setUp() {
    redisTemplate = mock(RedisTemplate.class);
    setOperations = mock(SetOperations.class);

    when(redisTemplate.opsForSet()).thenReturn(setOperations);
    cityService = new CityServiceImpl(redisTemplate);
  }

  @Test
  void addCity() {
    CityDTO newCity = new CityDTO();
    newCity.setName("Amsterdam");
    given(setOperations.add(CITY_KEY, newCity)).willReturn(1L);

    boolean result = cityService.addCity(newCity);

    assertTrue(result);
    then(setOperations).should().add(CITY_KEY, newCity);
  }
}
