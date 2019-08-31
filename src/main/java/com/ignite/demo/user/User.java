package com.ignite.demo.user;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.util.UUID;

public class User {
    private UserCacheKey id;

    @QuerySqlField
    private String name;

    public User(UserCacheKey id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return this.id.getId();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
