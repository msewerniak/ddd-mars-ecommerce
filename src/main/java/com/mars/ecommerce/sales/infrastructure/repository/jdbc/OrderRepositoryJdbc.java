package com.mars.ecommerce.sales.infrastructure.repository.jdbc;

import com.mars.ecommerce.sales.domain.order.OrderItemSnapshot;
import com.mars.ecommerce.sales.domain.order.OrderRepository;
import com.mars.ecommerce.sales.domain.order.OrderSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class OrderRepositoryJdbc implements OrderRepository {

    public static final String ORDER_ID = "orderId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<OrderSnapshot> orderMapper(List<OrderItemSnapshot> orderItemSnapshotList) {

        return (rs, rowNum) -> new OrderSnapshot(UUID.fromString(rs.getString("id")),
                UUID.fromString(rs.getString("clientId")), rs.getString("clientName"), orderItemSnapshotList,
                rs.getString("deliveryAddress"), rs.getString("discountDescription"), rs.getString("paymentStatus"),
                rs.getString("orderStatus"));
    }

    private static RowMapper<OrderItemSnapshot> orderItemMapper() {

        return (rs, rowNum) -> new OrderItemSnapshot(rs.getString(ORDER_ID), rs.getLong("productId"),
                rs.getString("productName"), rs.getString("productCategory"), rs.getString("productDescription"),
                rs.getInt("quantity"), rs.getLong("totalPrice"), rs.getString("totalPriceCurrency"));

    }

    @Override
    public OrderSnapshot find(UUID orderId) {

        List<OrderItemSnapshot> orderItemSnapshotList =
                jdbcTemplate.query("SELECT * FROM order_items where orderId = :orderId", Map.of(ORDER_ID, orderId),
                        orderItemMapper());

        return jdbcTemplate.queryForObject("SELECT * FROM orders where id = :orderId", Map.of(ORDER_ID, orderId),
                orderMapper(orderItemSnapshotList));
    }

    @Override
    public void save(OrderSnapshot order) {

        String sql =
                "INSERT INTO orders (id, clientId, clientName, deliveryAddress, discountDescription, paymentStatus, orderStatus) "
                        + "VALUES (:id, :clientId, :clientName, :deliveryAddress, :discountDescription, :paymentStatus, :orderStatus)";
        jdbcTemplate.update(sql,
                Map.of("id", order.id(), "clientId", order.clientId(), "clientName", order.clientName(),
                        "deliveryAddress", order.deliveryAddress(), "discountDescription", order.discountDescription(),
                        "paymentStatus", order.paymentStatus(), "orderStatus", order.orderStatus()));

        save(order.orderItems());
    }

    private void save(List<OrderItemSnapshot> orderItems) {

        Map[] params = orderItems.stream().map(this::mapOrderItem).toArray(Map[]::new);

        jdbcTemplate.batchUpdate(
                "INSERT INTO order_items (orderId, productId, productName, productCategory, productDescription, quantity, totalPrice, totalPriceCurrency) "
                        + "VALUES (:orderId, :productId, :productName, :productCategory, :productDescription, :quantity, :totalPrice, :totalPriceCurrency)",
                params);
    }

    private Map<String, Object> mapOrderItem(OrderItemSnapshot item) {
        return Map.of(ORDER_ID, item.orderId(), "productId", item.productId(), "productName", item.productName(),
                "productCategory", item.productCategory(), "productDescription", item.productDescription(), "quantity",
                item.quantity(), "totalPrice", item.totalPrice(), "totalPriceCurrency", item.totalPriceCurrency());
    }
}
