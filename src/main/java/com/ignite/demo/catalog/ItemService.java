package com.ignite.demo.catalog;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemService {
    private final IgniteCache<ItemCacheKey, Item> cache;

    private final SqlFieldsQuery insertItem = new SqlFieldsQuery("INSERT INTO item (id, name, price, catalogId) VALUES (?, ?, ?, ?)");
    private final SqlFieldsQuery fetchAllItems = new SqlFieldsQuery("SELECT id, name, price, catalogId FROM item WHERE catalogId = ?");

    public ItemService(IgniteCache<ItemCacheKey, Item> cache) {
        this.cache = cache;
    }

    public static ItemService fromCache(Ignite ignite, String name) {
        return new ItemService(ignite.getOrCreateCache(name));
    }

    public static ItemService fromExistentCache(Ignite ignite, String name) {
        return new ItemService(ignite.cache(name));
    }

    public ItemCacheKey createItemForCatalog(CatalogCacheKey catalogId, String name, BigDecimal price) {
        ItemCacheKey itemId = new ItemCacheKey();
        cache.query(insertItem.setArgs(itemId.getId(), name, price, catalogId.getId()));
        return itemId;
    }

    public void assignItemToCatalog(CatalogCacheKey catalogId, ItemCacheKey itemId) {
    }

    public List<Item> fetchAll(CatalogCacheKey catalogId) {
        List<Item> items = new ArrayList<>();
        try(QueryCursor<List<?>> cursor = cache.query(fetchAllItems.setArgs(catalogId.getId()))) {
            for(List<?> row: cursor) {
                Item item = new Item(new ItemCacheKey((UUID)row.get(0)), (String)row.get(1), (BigDecimal)row.get(2), (UUID)row.get(3));
                items.add(item);
            }
        }
        return items;
    }
}
