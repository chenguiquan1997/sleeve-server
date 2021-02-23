package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.model.User;
import com.quan.windsleeve.repository.UserRepository;
import com.quan.windsleeve.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 根据openid查询用户信息
     * @param openid
     * @return
     */
    @Override
    public User findUserByOpenid(String openid) {
        if(openid == null || openid == "") {
            return null;
        }
        User user = userRepository.findByOpenid(openid);
        return user;
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional;
    }
}
