package com.iky.travel.controller.city;

import static com.iky.travel.constant.common.ApiPathConstants.API_V1_CITY;

import com.iky.travel.domain.dto.CityDTO;
import com.iky.travel.exception.city.CityAddException;
import com.iky.travel.exception.city.CityAlreadyExistsException;
import com.iky.travel.exception.city.CityNotFoundException;
import com.iky.travel.exception.city.CityUpdateException;
import com.iky.travel.service.city.CityService;
import java.net.URI;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(API_V1_CITY)
public class CityController {

  private final CityService cityService;

  public CityController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping("{city}")
  public ResponseEntity<CityDTO> getCity(@PathVariable String city) {
    Optional<CityDTO> cityDTO = cityService.getCity(city);
    if (cityDTO.isEmpty()) {
      throw new CityNotFoundException("City not found: " + city);
    }
    return ResponseEntity.ok(cityDTO.get());
  }

  @PostMapping
  public ResponseEntity<Object> addCity(@RequestBody CityDTO cityDTO) {
    if (cityService.cityExists(cityDTO.getName())) {
      throw new CityAlreadyExistsException("City already exists: " + cityDTO.getName());
    }
    boolean added = cityService.addCity(cityDTO);
    if (added) {
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{name}")
          .buildAndExpand(cityDTO.getName())
          .toUri();
      return ResponseEntity.created(location).build();
    } else {
      throw new CityAddException("Error when adding city: " + cityDTO.getName());
    }
  }

  @PutMapping
  public ResponseEntity<Object> updateCity(@RequestBody CityDTO cityDTO) {
    if (!cityService.cityExists(cityDTO.getName())) {
      throw new CityNotFoundException("City to update is not found: " + cityDTO.getName());
    }
    boolean added = cityService.updateCity(cityDTO);
    if (added) {
      URI location = ServletUriComponentsBuilder.fromCurrentRequest()
          .path("/{name}")
          .buildAndExpand(cityDTO.getName())
          .toUri();
      return ResponseEntity.created(location).build();
    } else {
      throw new CityUpdateException("Error when updating city: " + cityDTO.getName());
    }
  }
}
