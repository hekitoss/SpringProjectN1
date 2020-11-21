package ru.hekitos.service;

import ru.hekitos.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAll();
    public User getByEmail(String email);
    public void add(User user);
}
