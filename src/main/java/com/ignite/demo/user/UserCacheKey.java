package com.ignite.demo.user;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.UUID;

public class UserCacheKey {
    @QuerySqlField(index = true)
    private final UUID id;

    public UserCacheKey(UUID id) {
        this.id = id;
    }

    public UserCacheKey() {
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
