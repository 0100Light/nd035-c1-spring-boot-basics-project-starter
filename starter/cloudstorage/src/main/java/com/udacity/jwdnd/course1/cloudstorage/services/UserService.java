package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public int addUser(User user){
        System.out.println("user service adding user");
        int rowsInserted = this.userMapper.addUser(user);
        System.out.println("-------------");
        System.out.println(rowsInserted);
        List<User> users = userMapper.findAllUsers();

        for (User u : users){
            System.out.println(u.getUsername() + " - " + u.getPassword());
        }

        int size = users.size();
        System.out.println("DB SIZE: " + size);
        System.out.println("-------------");
        return 1;
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
