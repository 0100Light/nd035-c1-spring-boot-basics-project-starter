package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS LIMIT 5")
    public List<User> findAllUsers();

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

//    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
//    @Insert("INSERT INTO USERS (username, password) VALUES (#{username}, #{password})")
    @Insert("insert into users (username, password) values (#{username}, #{password})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    public int addUser(User user);
}
