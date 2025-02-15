package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthService implements AuthenticationProvider {

    private UserService userService;
    private HashService hashService;
    private Logger logger = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        Object pass = authentication.getCredentials();

        User db_user = userService.getUser(name);
        if (db_user != null && pass != null) {
            String db_pass = db_user.getPassword();
            String pass_string = hashService.getHashedValue(pass.toString(), db_user.getSalt());
            if (pass_string.equals(db_pass)){
                System.out.println("login success");
                logger.warn("login success");
                return new UsernamePasswordAuthenticationToken(name, db_pass, new ArrayList<>());
                // last param: grantedAuthorities is VERY IMPORTANT, 沒提供相當於沒有任何權限…
            }
        }
//        System.out.println("-------------");
//        System.out.println("NAME: " + name);
//        System.out.println("pass: " + name);
//        System.out.println("-------------");

        System.out.println("login failed");
        logger.warn("login failed");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
