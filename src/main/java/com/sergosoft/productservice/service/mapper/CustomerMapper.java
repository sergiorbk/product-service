package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.Customer;
import com.sergosoft.productservice.dto.CosmoCatResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "registrationDate", source = "registrationDate")
    CosmoCatResponseDto toDto(Customer customer);
}
