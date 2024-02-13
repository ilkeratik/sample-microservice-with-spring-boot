package com.iky.travel.domain.service.travel.impl;

import static com.iky.travel.constant.common.RedisConstant.CITY_KEY;
import static com.iky.travel.constant.common.RedisConstant.POPULAR_DESTINATIONS_KEY;

import com.iky.travel.domain.service.travel.TravelService;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class TravelServiceImpl implements TravelService {

  private final RedisTemplate<String, Object> redisTemplate;

  public TravelServiceImpl(RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Set<Object> getMostQueriedCities(int topN) {
    return redisTemplate.opsForZSet().reverseRange(POPULAR_DESTINATIONS_KEY, 0, (long) topN - 1);
  }

  public Set<Object> getAllCities() {
    Set<String> keys = Optional.ofNullable(redisTemplate.keys(CITY_KEY + ":*"))
        .orElseGet(Collections::emptySet);

    return keys.stream()
        .flatMap(key -> redisTemplate.opsForHash().entries(key).values().stream())
        .filter(Objects::nonNull)
        .collect(Collectors.toSet());
  }


  @Override
  public boolean clearPopularDestinations() {
    return Boolean.TRUE.equals(redisTemplate.delete(POPULAR_DESTINATIONS_KEY));
  }
}
