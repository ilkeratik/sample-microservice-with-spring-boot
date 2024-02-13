package com.iky.travel.domain.service.city;

import com.iky.travel.domain.dto.CityDTO;
import java.util.Optional;

public interface CityService {

  boolean addCity(CityDTO city);

  boolean updateCity(CityDTO updatedCity);

  boolean cityExists(String cityName);

  Optional<CityDTO> getCity(String cityName);

  boolean deleteCity(String cityName);
}
