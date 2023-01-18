package com.mars.ecommerce.sales.infrastructure.repository.jdbc;

import com.mars.ecommerce.sales.domain.product.ProductRepository;
import com.mars.ecommerce.sales.domain.product.ProductSnapshot;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryJdbc implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static RowMapper<ProductSnapshot> productMapper() {
        return (rs, rowNum) -> new ProductSnapshot(rs.getLong("id"), rs.getString("name"), rs.getString("category"),
                rs.getString("description"));
    }

    @Override
    public void save(ProductSnapshot product) {

        jdbcTemplate.update(
                "INSERT INTO products (id, name, category, description) VALUES (:id, :name, :category, :description)",
                Map.of("id", product.id(), "name", product.name(), "category", product.category(), "description",
                        product.description()));
    }

    @Override
    public List<ProductSnapshot> list() {
        return jdbcTemplate.query("SELECT * FROM products", productMapper());
    }

    @Override
    public ProductSnapshot find(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products where id = :id", Map.of("id", id), productMapper());
    }

    @Override
    public Long nextId() {
        return jdbcTemplate.queryForObject("select next value for productsSeq from dual", Map.of(), Long.class);
    }
}
