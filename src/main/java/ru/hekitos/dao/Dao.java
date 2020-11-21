package ru.hekitos.dao;

import ru.hekitos.model.User;

import java.util.List;

public interface Dao {
    public List<User> getAll();
    public User getByEmail(String email);
    public void add(User user);
}
