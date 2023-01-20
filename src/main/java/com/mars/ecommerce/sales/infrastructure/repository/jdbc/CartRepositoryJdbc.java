package com.mars.ecommerce.sales.infrastructure.repository.jdbc;

import com.mars.ecommerce.sales.domain.cart.Cart;
import com.mars.ecommerce.sales.domain.cart.CartId;
import com.mars.ecommerce.sales.domain.cart.CartRepository;
import com.mars.ecommerce.sales.domain.cart.CartSnapshot;
import com.mars.ecommerce.support.ddd.DddAnnotations;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@DddAnnotations.DomainRepositoryImpl
@RequiredArgsConstructor
@Repository
public class CartRepositoryJdbc implements CartRepository {

    public static final String CART_ID = "cartId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Cart find(CartId cartId) {

        List<CartSnapshot.CartItemSnapshot> items =
                jdbcTemplate.query("SELECT * FROM cart_items where cartId = :cartId", Map.of(CART_ID, cartId.id()),
                        cartMapper(cartId.id()));

        CartSnapshot cartSnapshot = new CartSnapshot(cartId.id(), items);

        return Cart.fromSnapshot(cartSnapshot);
    }

    private RowMapper<CartSnapshot.CartItemSnapshot> cartMapper(UUID cartId) {
        return (rs, rowNum) -> new CartSnapshot.CartItemSnapshot(cartId, rs.getLong("productId"), rs.getLong("price"),
                rs.getString("currency"),
                CartSnapshot.CartItemSnapshot.CartItemType.valueOf(rs.getString("cartItemType")));
    }

    @Override
    public void save(Cart cart) {

        jdbcTemplate.update("DELETE FROM cart_items where cartId = :cartId", Map.of(CART_ID, cart.id().id()));

        Map[] params = cart.snapshot().items().stream().map(this::mapCartItemSnapshot).toArray(Map[]::new);

        jdbcTemplate.batchUpdate(
                "INSERT INTO cart_items (cartId, productId, price, currency, cartItemType) "
                        + "VALUES (:cartId, :productId, :price, :currency, :type)", params);
    }

    private Map<String, Object> mapCartItemSnapshot(CartSnapshot.CartItemSnapshot item) {
        return Map.of(CART_ID, item.cartId(), "productId", item.productId(), "price", item.price(), "currency",
                item.currency(), "type", item.type().name());
    }
}
