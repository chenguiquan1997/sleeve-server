package com.quan.windsleeve.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quan.windsleeve.exception.http.GetTokenException;
import com.quan.windsleeve.exception.http.NoOpenIdException;
import com.quan.windsleeve.model.User;
import com.quan.windsleeve.repository.UserRepository;
import com.quan.windsleeve.service.IUserService;
import com.quan.windsleeve.util.JwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.quan.windsleeve.exception.http.NoAuthorizationException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信授权服务
 */

@Service
public class WxAuthenticationService {

    private final Logger log = LoggerFactory.getLogger(WxAuthenticationService.class);

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
        String response = "";
        try {
            response = restTemplate.getForObject(reqUrl, String.class);
        }catch (Exception e) {
            log.error("调用微信第三方wxCode2Session接口时失败",e);
            throw new GetTokenException(10006);
        }
        Map<String,Object> codeSessionMap = new HashMap<>();
        try {
            //可以将String类型转换为Map类型
            codeSessionMap = mapper.readValue(response,Map.class);
        } catch (JsonProcessingException e) {
            log.warn("微信小程序授权数据类型转换失败，reponse=[{}]",response,e);
            e.printStackTrace();
        }
        String openid = (String) codeSessionMap.get("openid");
        if(openid == null) {
            throw new NoOpenIdException(10007);
        }
        // 查询数据库，判断当前用户有没有注册
        User userInfo = userService.findUserByOpenid(openid);
        if(userInfo == null) {
            log.info("当前用户未注册, openId=[{}]",openid);
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
        try {
            userRepository.save(user);
        }catch (Exception e) {
            log.warn("用户注册失败, openId=[{}]",openid,e);
            throw new NoAuthorizationException(10005);
        }

        return userService.findUserByOpenid(openid);
    }
}
