package com.iky.travel.service.travel.impl;

import static com.iky.travel.constant.common.RedisConstant.CITY_KEY;
import static com.iky.travel.constant.common.RedisConstant.POPULAR_DESTINATIONS_KEY;

import com.iky.travel.service.travel.TravelService;
import java.util.Set;
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

  @Override
  public Set<Object> getAllCities() {
    return redisTemplate.opsForSet().members(CITY_KEY);
  }

  @Override
  public boolean clearPopularDestinations() {
    return Boolean.TRUE.equals(redisTemplate.delete(POPULAR_DESTINATIONS_KEY));
  }
}
