package com.iky.travel.service.city.impl;

import static com.iky.travel.constant.common.RedisConstant.CITY_KEY;

import com.iky.travel.domain.dto.CityDTO;
import com.iky.travel.service.city.CityService;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final SetOperations<String, Object> setOperations;

  public CityServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
    this.setOperations = redisTemplate.opsForSet();
  }

  @Override
  public boolean addCity(CityDTO city) {
    Long added = setOperations.add(CITY_KEY, city);
    return added != null && added > 0;
  }

  @Override
  public boolean updateCity(CityDTO updatedCity) {
    Optional<CityDTO> cityOptional = Objects.requireNonNull(setOperations.members(CITY_KEY))
        .stream()
        .map(e -> (CityDTO) e)
        .filter(c -> c.getName().equalsIgnoreCase(updatedCity.getName()))
        .findFirst();
    if (cityOptional.isPresent()) {
      CityDTO existingCity = cityOptional.get();
      setOperations.remove(CITY_KEY, existingCity);
      setOperations.add(CITY_KEY, updatedCity);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean cityExists(String cityName) {
    return Objects.requireNonNull(setOperations.members(CITY_KEY)).stream()
        .anyMatch(city -> ((CityDTO) city).getName().equalsIgnoreCase(cityName));
  }

  @Override
  public Optional<CityDTO> getCity(String cityName) {
    Optional<CityDTO> cityOptional = Objects.requireNonNull(setOperations.members(CITY_KEY))
        .stream()
        .map(e -> (CityDTO) e)
        .filter(city -> cityName.equalsIgnoreCase(city.getName()))
        .findFirst();
    cityOptional.ifPresent(e -> incrementCityQueryCount(cityName));
    return cityOptional;
  }

  public void incrementCityQueryCount(String cityName) {
    redisTemplate.opsForZSet().incrementScore("popularDestinations", cityName, 1);
  }
}
