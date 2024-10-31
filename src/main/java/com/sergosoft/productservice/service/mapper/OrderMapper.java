package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.order.Order;
import com.sergosoft.productservice.domain.order.OrderItem;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.dto.order.item.OrderItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "sellerId", source = "sellerId")
    @Mapping(target = "buyerId", source = "buyerId")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "totalPrice", source = "totalPrice")
    @Mapping(target = "createdAt", source = "createdAt")
    OrderResponseDto toDto(Order entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "price", source = "price")
    OrderItemResponseDto toDto(OrderItem item);

    List<OrderItemResponseDto> mapItems(List<OrderItem> items);
}

