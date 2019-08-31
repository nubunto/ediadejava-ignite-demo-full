package com.ignite.demo.catalog;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CatalogService {
    private IgniteCache<CatalogCacheKey, Catalog> cache;

    private final SqlFieldsQuery getCatalogById = new SqlFieldsQuery("SELECT id, items FROM catalog WHERE id = ?");
    private final SqlFieldsQuery insertCatalog = new SqlFieldsQuery("INSERT INTO catalog (id) VALUES (?)");

    public CatalogService(IgniteCache<CatalogCacheKey, Catalog> cache) {
        this.cache = cache;
    }

    public static CatalogService fromCache(Ignite ignite, String name) {
        return new CatalogService(ignite.getOrCreateCache(name));
    }

    public CatalogCacheKey create() {
        CatalogCacheKey key = new CatalogCacheKey();
        cache.query(insertCatalog.setArgs(key.getId()));
        return key;
    }

    public Catalog getCatalogById(UUID id) {
        return null;
    }
}
