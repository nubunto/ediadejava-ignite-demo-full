package com.ignite.demo.catalog;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.math.BigDecimal;
import java.util.UUID;

public class Item {
    private ItemCacheKey id;

    @QuerySqlField(index = true)
    @AffinityKeyMapped
    private UUID catalogId;

    @QuerySqlField
    private String name;

    @QuerySqlField
    private BigDecimal price;

    public Item(ItemCacheKey id, String name, BigDecimal price, UUID catalogId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.catalogId = catalogId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getCatalogId() {
        return catalogId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", catalogId=" + catalogId +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
