package com.iky.travel.service.city.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.iky.travel.domain.dto.CityDTO;
import com.iky.travel.domain.mapper.CityMapper;
import com.iky.travel.domain.model.City;
import com.iky.travel.domain.repository.city.CityRepository;
import com.iky.travel.domain.service.city.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

class CityServiceImplTest {

  RedisTemplate<String, Object> redisTemplate;
  HashOperations hashOperations;
  CityRepository cityRepository;
  CityServiceImpl cityService;

  @BeforeEach
  void setUp() {
    redisTemplate = mock(RedisTemplate.class);
    hashOperations = mock(HashOperations.class);
    cityRepository = mock(CityRepository.class);
    when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    cityService = new CityServiceImpl(cityRepository, redisTemplate);
  }

  @Test
  void addCity() {
    CityDTO newCityDTO = new CityDTO();
    newCityDTO.setName("Amsterdam");
    City newCity = CityMapper.INSTANCE.dtoToCity(newCityDTO);

    cityService.addCity(newCityDTO);

    verify(cityRepository, times(1)).save(newCity);
  }
}
