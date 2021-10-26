package com.tui.proof.model.mapper;

import com.tui.proof.model.Order;
import com.tui.proof.model.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "clientId", ignore = true)
    Order OrderDtoToOrder(OrderDTO orderDTO);
}
