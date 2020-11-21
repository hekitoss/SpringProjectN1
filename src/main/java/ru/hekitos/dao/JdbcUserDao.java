package ru.hekitos.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.hekitos.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcUserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users",
                new BeanPropertyRowMapper<>(User.class));

    }

    public User getByEmail(String email){
        return jdbcTemplate.query(
                "Select * from users where email = ?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(User.class)).stream().findAny().orElse(null);
    }

    public void add(User user){
        jdbcTemplate.update("insert into users values (?, ?, ?)",
        user.getName(),user.getSurname(),user.getEmail());
    }
}
