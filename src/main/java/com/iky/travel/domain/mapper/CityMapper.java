package com.iky.travel.domain.mapper;

import com.iky.travel.domain.dto.CityDTO;
import com.iky.travel.domain.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CityMapper {

  CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);


  City dtoToCity(CityDTO cityDTO);

  CityDTO cityToDto(City city);
}