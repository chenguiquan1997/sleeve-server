package com.quan.windsleeve.api.v1;

import com.quan.windsleeve.dto.TokenDTO;
import com.quan.windsleeve.dto.TokenGetDTO;
import com.quan.windsleeve.exception.http.NoAuthorizationException;
import com.quan.windsleeve.service.impl.WxAuthenticationService;
import com.quan.windsleeve.util.JwtToken;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private WxAuthenticationService wxAuthenticationService;

    /**
     * 用户获取token令牌，也就是用户进行登录验证的过程
     * @param userData
     * @return
     */
    @PostMapping("/getToken")
    public Map<String,String> getToken(@RequestBody @Validated TokenGetDTO userData) {
        System.out.println("code进来了");
        String code = userData.getAccount();
        String type = userData.getType();
        String response = null;
        Map<String,String> tokenMap = new HashMap<>();
        switch (type) {
            case "0":
                response = wxAuthenticationService.wxCode2Session(code);
                System.out.println("生成的jwt令牌："+response);
            case "1":
                break;
            default:
                System.out.println("没有找到");
        }
        tokenMap.put("token",response);
        return tokenMap;
    }

    @PostMapping("/verify")
    @ResponseBody
    public Map<String,Boolean> verifyToken(@RequestBody TokenDTO tokenDTO) {
        //throw new NoAuthorizationException(10003);
        System.out.println("服务端获取的token: "+tokenDTO.getToken());
        Boolean isValid = JwtToken.verifyToken(tokenDTO.getToken());
        Map<String,Boolean> validMap = new HashMap<>();
        if(isValid == true) {
            validMap.put("is_valid",true);
        }else {
            validMap.put("is_valid",false);
        }
        return validMap;
    }
}
