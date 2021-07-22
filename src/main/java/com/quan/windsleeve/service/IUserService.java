package com.quan.windsleeve.service;

import com.quan.windsleeve.dto.MinUserDTO;
import com.quan.windsleeve.model.User;

import java.util.Optional;


public interface IUserService{

   User findUserByOpenid(String openid);

   Optional<User> findUserById(Long userId);

   /**
    * 根据 昵称 查询用户信息
    * @param nickName
    * @return
    */
   Boolean findByNickname(String nickName);

   void save(MinUserDTO minUserDTO);

}
