package com.quan.windsleeve.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quan.windsleeve.model.User;
import com.quan.windsleeve.repository.UserRepository;
import com.quan.windsleeve.service.IUserService;
import com.quan.windsleeve.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信授权服务
 */

@Service
public class WxAuthenticationService {

    @Value("${wx.appid}")
    private String appId;

    @Value("${wx.appsecret}")
    private String appSecret;

    @Value("${wx.auth_url}")
    private String authUrl;

    @Value("${missyou.security.api-scope}")
    private Integer scope;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtToken jwtToken;

    public String wxCode2Session(String code) {
        String reqUrl = this.authUrl + "appid=" + this.appId + "&secret=" + this.appSecret + "&js_code=" + code;
        System.out.println(reqUrl);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(reqUrl, String.class);
        Map<String,Object> codeSessionMap = new HashMap<>();
        try {
            //可以将String类型转换为Map类型
            codeSessionMap = mapper.readValue(response,Map.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String openid = (String) codeSessionMap.get("openid");
        if(openid == null) return null;
        // 查询数据库，判断当前用户有没有注册
        User userInfo = userService.findUserByOpenid(openid);
        if(userInfo == null) {
            System.out.println("系统中没有当前用户");
            //注册，返回jwt令牌
            User newUser = registerNewUser(openid);
            return jwtToken.getJwtToken(this.scope,newUser.getId());
        }
        System.out.println("系统中存在当前用户");
        //todo 直接返回jwt令牌
        return jwtToken.getJwtToken(this.scope,userInfo.getId());
    }

    /**
     * 注册新用户
     * @param openid
     */
    private User registerNewUser(String openid) {
        if(openid == null) return null;
        User user = User.builder()
                .openid(openid)
                .build();
        Date date = new Date();
        System.out.println("当前系统时间 "+date);
        user.setCreateTime(date);
        user.setUpdateTime(date);
        userRepository.save(user);
        return userService.findUserByOpenid(openid);
    }
}
