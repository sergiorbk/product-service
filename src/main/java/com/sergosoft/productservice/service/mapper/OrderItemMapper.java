package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.order.item.OrderItemDetails;
import com.sergosoft.productservice.repository.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(source = "order", target = "orderDetails")
    @Mapping(source = "product", target = "productDetails")
    OrderItemDetails toOrderItemDetails(OrderItemEntity orderItemEntity);

}

