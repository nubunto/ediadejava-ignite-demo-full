package com.ignite.demo.user;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {
    private final IgniteCache<UserCacheKey, User> cache;

    private final SqlFieldsQuery insertUserQuery = new SqlFieldsQuery("INSERT INTO user (id, name) VALUES (?, ?)");
    private final SqlFieldsQuery fetchAllUsersQuery = new SqlFieldsQuery("SELECT id, name FROM user");

    public UserService(IgniteCache<UserCacheKey, User> cache) {
        this.cache = cache;
    }

    public static UserService fromCache(Ignite ignite, String name) {
        return new UserService(ignite.getOrCreateCache(name));
    }

    public UserCacheKey create(String name) {
        UserCacheKey userId = new UserCacheKey();
        cache.query(insertUserQuery.setArgs(userId.getId(), name));
        return userId;
    }

    public List<User> fetchAll() {
        List<User> users = new ArrayList<>();
        try(QueryCursor<List<?>> cursor = cache.query(fetchAllUsersQuery)) {
            for(List<?> row: cursor) {
                users.add(new User(new UserCacheKey((UUID)row.get(0)), (String)row.get(1)));
            }
        }
        return users;
    }
}
