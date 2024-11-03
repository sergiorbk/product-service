package com.sergosoft.productservice.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    OrderItemResponseDto toDto(OrderItem orderItem);
}
