package com.iky.travel.domain.service.city.impl;

import static com.iky.travel.constant.common.RedisConstant.CITY_KEY;

import com.iky.travel.domain.dto.CityDTO;
import com.iky.travel.domain.mapper.CityMapper;
import com.iky.travel.domain.model.City;
import com.iky.travel.domain.repository.city.CityRepository;
import com.iky.travel.domain.service.city.CityService;
import com.iky.travel.exception.city.CityAlreadyExistsException;
import com.iky.travel.exception.city.CityDeleteException;
import com.iky.travel.exception.city.CityNotFoundException;
import java.util.Optional;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final HashOperations<String, String, CityDTO> hashOperations;
  private final CityRepository cityRepository;

  public CityServiceImpl(CityRepository cityRepository,
      RedisTemplate<String, Object> redisTemplate) {
    this.cityRepository = cityRepository;
    this.redisTemplate = redisTemplate;
    this.hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public boolean addCity(CityDTO cityDTO) {
    if (cityExists(cityDTO.getName())) {
      throw new CityAlreadyExistsException("City Already Exists: " + cityDTO.getName());
    }
    City city = CityMapper.INSTANCE.dtoToCity(cityDTO);
    cityRepository.save(city);
    hashOperations.put(generateRedisKey(city.getName()), city.getName(), cityDTO);
    return true;
  }

  @Override
  public boolean updateCity(CityDTO updatedCity) {
    if (!cityExists(updatedCity.getName())) {
      throw new CityNotFoundException("City to update not found: " + updatedCity.getName());
    }
    City city = CityMapper.INSTANCE.dtoToCity(updatedCity);
    cityRepository.save(city);
    hashOperations.put(generateRedisKey(city.getName()), city.getName(), updatedCity);
    return true;
  }

  @Override
  public boolean cityExists(String cityName) {
    if (Boolean.TRUE.equals(hashOperations.hasKey(generateRedisKey(cityName), cityName))) {
      return true;
    }
    return cityRepository.findByName(cityName).isPresent();
  }

  @Override
  public Optional<CityDTO> getCity(String cityName) {
    CityDTO cityDTO = hashOperations.get(generateRedisKey(cityName), cityName);
    if (cityDTO != null) {
      incrementCityQueryCount(cityName);
      return Optional.of(cityDTO);
    }
    Optional<City> optionalCity = cityRepository.findByName(cityName);
    if (optionalCity.isPresent()) {
      cityDTO = CityMapper.INSTANCE.cityToDto(optionalCity.get());
      hashOperations.put(generateRedisKey(cityName), cityName, cityDTO);
      incrementCityQueryCount(cityName);
      return Optional.of(cityDTO);
    }
    return Optional.empty();
  }

  @Override
  public boolean deleteCity(String cityName) {
    try {
      hashOperations.delete(generateRedisKey(cityName), cityName);
      cityRepository.deleteByName(cityName);
    } catch (Exception ex) {
      throw new CityDeleteException("Error when deleting city:" + cityName, ex);
    }
    return false;
  }

  public void incrementCityQueryCount(String cityName) {
    redisTemplate.opsForZSet().incrementScore("popularDestinations", cityName, 1);
  }

  private String generateRedisKey(String cityName) {
    return CITY_KEY + ":" + cityName;
  }
}
