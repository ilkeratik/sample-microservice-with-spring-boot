package com.iky.travel.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "cities")
@Data
public class City {

  @Id
  private String id;
  private String name;
  @Field("plate_no")
  private int plateNo;
  private Long population;
  private String country;
  @Field("top_activities")
  private String[] topActivities;
}
