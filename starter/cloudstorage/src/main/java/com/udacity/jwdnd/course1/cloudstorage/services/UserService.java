package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.SignupForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService {
    private UserMapper userMapper;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int addUser(User user){
        logger.info("user service adding user");
        int rowsInserted = this.userMapper.addUser(user);
        logger.info(rowsInserted + " user added");

        List<User> users = userMapper.findAllUsers();

        for (User u : users){
            System.out.println(u.getUsername() + " - " + u.getPassword());
        }

        int size = users.size();
        System.out.println("DB SIZE: " + size);
        System.out.println("-------------");
        return rowsInserted;
    }

    public int addUserHashed(SignupForm signupForm){
        if (getUser(signupForm.getUsername()) != null)
        {
            logger.warn("duplicated user");
            return 0;
        }
        String salt = RandomStringUtils.randomAlphanumeric(16);
        String hashedPass = hashService.getHashedValue(signupForm.getPassword(), salt);
        int res = userMapper.addUserHashed(new User(null, signupForm.getUsername(), salt, signupForm.getFirstname(), signupForm.getLastname(), hashedPass));
        logger.warn("1 user added.");
        return res;
    }

    public User getUser(String username){
        User u = userMapper.getUser(username);
        return u;
    }

    public List<User> Users(){
        List<User> users = userMapper.findAllUsers();
        return users;
    }
}
