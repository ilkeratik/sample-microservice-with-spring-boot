package com.iky.travel.domain.repository.city;

import com.iky.travel.domain.model.City;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

  Optional<City> findByName(String name);

  boolean deleteByName(String name);
}
