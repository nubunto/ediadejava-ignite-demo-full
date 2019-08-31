package com.ignite.demo.catalog;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.UUID;

public class ItemCacheKey {
    @QuerySqlField(index = true)
    private UUID id;

    public ItemCacheKey(UUID id) {
        this.id = id;
    }

    public ItemCacheKey() {
        this(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
