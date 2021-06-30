package com.quan.windsleeve.service.impl;

import com.quan.windsleeve.dto.MinUserDTO;
import com.quan.windsleeve.exception.http.RegisterUserException;
import com.quan.windsleeve.model.MinProUser;
import com.quan.windsleeve.model.User;
import com.quan.windsleeve.repository.MinUserRepository;
import com.quan.windsleeve.repository.UserRepository;
import com.quan.windsleeve.service.IUserService;
import com.quan.windsleeve.util.LocalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinUserRepository minUserRepository;

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

    /**
     * @Description: 根据 昵称 查询用户信息
     * @param nickName
     * @return com.quan.windsleeve.model.MinProUser
     * @Author: Guiquan Chen
     * @Date: 2021/6/1
     */
    @Override
    public Boolean findByNickname(String nickName) {
        MinProUser user = minUserRepository.findByNickName(nickName);
        if(user == null) {
          log.info("当前用户不存在, nickName=[{}]",nickName);
          return false;
        }
        return true;
    }

    /**
     * @Description: 保存小程序用户信息
     * @param minUserDTO
     * @return: null
     * @Author: Guiquan Chen
     * @Date: 2021/6/1
     */
    @Override
    public void save(MinUserDTO minUserDTO) {
        String openId = LocalUser.getUser().getOpenid();
        MinProUser minUser = new MinProUser();
        BeanUtils.copyProperties(minUserDTO,minUser);
        if(openId != null && !openId.equals("")) {
            minUser.setOpenId(openId);
        }
        try {
            minUserRepository.save(minUser);
        }catch (Exception e) {
           log.error("注册用户失败, data=[{}]",minUser.toString(),e);
           throw new RegisterUserException(10005);
        }
    }
}
