package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "order.id", target = "orderId")
    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDto toOrderResponseDto(OrderItemEntity orderItemEntity);

}

