package com.ignite.demo.catalog;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.List;
import java.util.UUID;

public class Catalog {
    private CatalogCacheKey id;

    private List<Item> items;

    public Catalog(CatalogCacheKey id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public UUID getId() {
        return id.getId();
    }

    public List<Item> getItems() {
        return items;
    }
}
