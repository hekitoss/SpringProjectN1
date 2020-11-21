package ru.hekitos.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.hekitos.model.User;

import java.util.List;

@Component
public class HibernateUserDao implements Dao{

    @Autowired
    private SessionFactory sessionFactory;

    private Session currentSession(){
        return sessionFactory.openSession();
    }

    public List<User> getAll(){
        return currentSession().createQuery("from User", User.class).list();
    }

    public User getByEmail(String email){
        Query<User> q = currentSession().createQuery("from User where email =:email",User.class);
        q.setParameter("email",email);
        return q.list().stream().findAny().orElse(null);
    }

    public void add(User user){
        currentSession().save(user);
    }
}
