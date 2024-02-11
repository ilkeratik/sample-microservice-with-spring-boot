package com.iky.travel.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class City {

  private int plateNo;
  private String name;
  private Long population;
  private String country;
  private String[] topActivities;
}
