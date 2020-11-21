package ru.hekitos;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.hekitos.dao.HibernateUserDao;
import ru.hekitos.dao.JdbcUserDao;
import ru.hekitos.dao.UserDao;
import ru.hekitos.model.User;
import ru.hekitos.util.UserValidator;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JdbcUserDao jdbcUserDao;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private HibernateUserDao hibernateUserDao;

    @GetMapping("/view/{name}")
    public String view(@PathVariable("name") String name,
                       Model model){
        model.addAttribute("msg", "Hello "+name);
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) throws SQLException {
        model.addAttribute("users",hibernateUserDao.getAll());
        return "users";

    }

    @GetMapping("/users/new")
    public String getSignUp(Model model){
        model.addAttribute("user", new User());
        return "/sign_up";
    }

    @PostMapping("/users/new")
    public String signUp(@ModelAttribute @Valid User user, BindingResult result){
        userValidator.validate(user, result);
        if(result.hasErrors()) return "/sign_up";
        hibernateUserDao.add(user);
        return "redirect:/users";
    }

}

