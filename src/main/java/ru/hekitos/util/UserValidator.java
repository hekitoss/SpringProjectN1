package ru.hekitos.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.hekitos.dao.Dao;
import ru.hekitos.dao.UserDao;
import ru.hekitos.model.User;
import ru.hekitos.service.UserService;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;



    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User)o;
        if (userService.getByEmail(user.getEmail())!=null){
            errors.rejectValue("email","","This email is already in use");
        }
    }
}
