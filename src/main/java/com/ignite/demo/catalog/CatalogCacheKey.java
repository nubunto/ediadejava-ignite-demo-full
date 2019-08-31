package com.ignite.demo.catalog;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.UUID;

public class CatalogCacheKey {
    @QuerySqlField(index = true)
    private UUID id;

    public CatalogCacheKey(UUID id) {
        this.id = id;
    }

    public CatalogCacheKey() {
        this(UUID.randomUUID());
    }

    public static CatalogCacheKey from(String uuid) {
        return new CatalogCacheKey(UUID.fromString(uuid));
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
