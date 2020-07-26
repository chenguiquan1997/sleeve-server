package com.quan.windsleeve.service;

import com.quan.windsleeve.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IUserService{

   User findUserByOpenid(String openid);

}
