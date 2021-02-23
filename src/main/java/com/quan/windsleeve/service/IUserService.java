package com.quan.windsleeve.service;

import com.quan.windsleeve.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface IUserService{

   User findUserByOpenid(String openid);

   Optional<User> findUserById(Long userId);

}
