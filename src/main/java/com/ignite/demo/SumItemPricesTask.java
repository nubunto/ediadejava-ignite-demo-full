package com.ignite.demo;

import com.ignite.demo.catalog.Item;
import com.ignite.demo.catalog.ItemCacheKey;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobAdapter;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class SumItemPricesTask extends ComputeTaskSplitAdapter<UUID, BigDecimal> {
    private final IgniteCache<ItemCacheKey, Item> itemCache;

    public SumItemPricesTask(IgniteCache<ItemCacheKey, Item> itemCache) {
        this.itemCache = itemCache;
    }

    @Override
    protected Collection<? extends ComputeJob> split(int gridSize, UUID catalogId) throws IgniteException {
        List<ComputeJob> jobs = new ArrayList<>();
        for(int i = 0; i < gridSize; i++) {
            jobs.add(createPricesJobAdapter(catalogId));
        }
        return jobs;
    }

    private ComputeJob createPricesJobAdapter(UUID catalogId) {
        return new ComputeJobAdapter() {
            @Override
            public Object execute() throws IgniteException {
                BigDecimal sum = BigDecimal.ZERO;
                System.out.println("Querying for catalog id "+ catalogId);
                try(FieldsQueryCursor<List<?>> cursor = itemCache.query(buildQuery(catalogId))) {
                    for(List<?> row : cursor) {
                        BigDecimal price = (BigDecimal)row.get(0);
                        System.out.println("Counting price " + price);
                        sum = sum.add(price);
                    }
                }
                return sum;
            }
        };
    }

    private SqlFieldsQuery buildQuery(UUID catalogId) {
        return new SqlFieldsQuery("SELECT price FROM item WHERE catalogId = ?")
                .setArgs(catalogId)
                .setLocal(true);
    }

    @Override
    public BigDecimal reduce(List<ComputeJobResult> results) throws IgniteException {
        BigDecimal sum = BigDecimal.ZERO;
        for(ComputeJobResult result : results) {
            sum = sum.add(result.getData());
        }
        return sum;
    }
}
