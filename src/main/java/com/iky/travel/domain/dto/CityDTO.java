package com.iky.travel.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityDTO implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String id;
  @NotNull(message = "Plate no is required.")
  @Positive(message = "Population must be greater than 0.")
  private int plateNo;
  @NotBlank(message = "Name is required.")
  private String name;
  @NotNull(message = "Population is required.")
  @Positive(message = "Population must be greater than 0.")
  private Long population;
  @NotBlank(message = "Country is required.")
  private String country;
  @Size(min = 1, message = "Top activities must contain at least one item.")
  private String[] topActivities;
}
