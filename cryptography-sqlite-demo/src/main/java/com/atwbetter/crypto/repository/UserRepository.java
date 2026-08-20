package com.atwbetter.crypto.repository;

import com.atwbetter.crypto.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbc;
    private final SimpleJdbcInsert insert;
    private final RowMapper<User> rowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("email")
    );

    public UserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
        this.insert = new SimpleJdbcInsert(jdbc).withTableName("users").usingGeneratedKeyColumns("id");
    }

    public List<User> findAll() {
        return jdbc.query("SELECT id, name, email FROM users", rowMapper);
    }

    public Optional<User> findById(Long id) {
        List<User> list = jdbc.query("SELECT id, name, email FROM users WHERE id = ?", rowMapper, id);
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    public User save(User user) {
        if (user.getId() == null) {
            Map<String, Object> params = new HashMap<>();
            params.put("name", user.getName());
            params.put("email", user.getEmail());
            Number key = insert.executeAndReturnKey(params);
            user.setId(key.longValue());
            return user;
        } else {
            jdbc.update("UPDATE users SET name = ?, email = ? WHERE id = ?",
                    user.getName(), user.getEmail(), user.getId());
            return user;
        }
    }

    public void deleteById(Long id) {
        jdbc.update("DELETE FROM users WHERE id = ?", id);
    }

    public boolean existsById(Long id) {
        Integer count = jdbc.queryForObject("SELECT COUNT(1) FROM users WHERE id = ?", Integer.class, id);
        return count != null && count > 0;
    }
}
