package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.dto.order.item.OrderItemCreationDto;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemsMapper {

    OrderItemEntity toEntity(final OrderItemCreationDto creationDto);
}
