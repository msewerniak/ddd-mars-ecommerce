package com.mars.ecommerce.shipping.infrastructure.repository;

import com.mars.ecommerce.shipping.readmodel.OrderDto;
import com.mars.ecommerce.shipping.readmodel.OrderFinder;
import com.mars.ecommerce.shipping.readmodel.OrderItemDto;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@DddAnnotations.DomainRepositoryImpl
@AllArgsConstructor
@Repository
public class OrderFinderRepositoryJdbc implements OrderFinder {

    public static final String ORDER_ID = "orderId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<OrderDto> orderMapper(List<OrderItemDto> orderItemDtoList) {

        return (rs, rowNum) -> new OrderDto(UUID.fromString(rs.getString("id")), orderItemDtoList,
                rs.getString("deliveryAddress"), rs.getString("orderStatus"));
    }

    private static RowMapper<OrderItemDto> orderItemMapper() {

        return (rs, rowNum) -> new OrderItemDto(UUID.fromString(rs.getString(ORDER_ID)), rs.getLong("productId"),
                rs.getString("productName"), rs.getString("productCategory"), rs.getString("productDescription"),
                rs.getInt("quantity"));

    }

    @Override
    public OrderDto find(UUID orderId) {

        List<OrderItemDto> orderItemDtos =
                jdbcTemplate.query("SELECT * FROM order_items where orderId = :orderId", Map.of(ORDER_ID, orderId),
                        orderItemMapper());

        return jdbcTemplate.queryForObject("SELECT * FROM orders where id = :orderId", Map.of(ORDER_ID, orderId),
                orderMapper(orderItemDtos));
    }

}
