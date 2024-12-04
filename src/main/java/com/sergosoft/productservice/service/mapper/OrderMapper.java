package com.sergosoft.productservice.service.mapper;

import com.sergosoft.productservice.domain.order.OrderDetails;
import com.sergosoft.productservice.dto.order.OrderResponseDto;
import com.sergosoft.productservice.repository.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDetails toOrderDetails(OrderEntity orderEntity);

    OrderResponseDto toOrderResponseDto(OrderDetails orderDetails);

}
