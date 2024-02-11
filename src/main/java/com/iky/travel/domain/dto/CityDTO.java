package com.iky.travel.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO {

  private int plateNo;
  private String name;
  private Long population;
  private String country;
  private String[] topActivities;
}
