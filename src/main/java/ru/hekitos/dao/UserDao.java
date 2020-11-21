package ru.hekitos.dao;

import org.springframework.stereotype.Component;
import ru.hekitos.model.User;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
public class UserDao implements Dao {

    private static Connection connection;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = UserDao.class.getClassLoader().getResourceAsStream("persistence.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<User> getAll() {
        List<User>users = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setName(rs.getString(1));
                user.setSurname(rs.getString(2));
                user.setEmail(rs.getString(3));
                users.add(user);
            }
            return users;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    public User getByEmail(String email){
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "Select * from users where email = ?");
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                User user = new User();
                user.setName(rs.getString(1));
                user.setSurname(rs.getString(2));
                user.setEmail(rs.getString(3));
                return user;
            }
            return null;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void add(User user){
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement("insert into users values (?, ?, ?)");
            ps.setString(1,user.getName());
            ps.setString(2,user.getSurname());
            ps.setString(3,user.getEmail());
            ps.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


}
