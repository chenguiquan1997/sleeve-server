package com.quan.windsleeve.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {


    private static String jwtSign;
    private static Long expireTime;

    @Value("${missyou.security.jwt-sign}")
    public void setJwtSign(String jwtSign) {
        JwtToken.jwtSign = jwtSign;
    }

    @Value("${missyou.security.expire-time}")
    public void setExpireTime(Long expireTime) {
        JwtToken.expireTime = expireTime;
    }

    /**
     * 获取jwtToken
     * @param scope 令牌的范围
     * @param userId 用户id
     * @return String
     */
    public static String getJwtToken(int scope, Long userId) {
        return createJwtToken(scope,userId);
    }

    /**
     * 创建jwtToken
     * @param scope 当前令牌可以访问接口的范围
     * @param userId 用户id
     */
    private static String createJwtToken(int scope, Long userId) {
        //获取一个创建jwt令牌的算法
        System.out.println("secret: "+JwtToken.jwtSign);
        Algorithm jwtAlgorithm = Algorithm.HMAC256(JwtToken.jwtSign);
        Map<String,Long> time = makeJwtTokenExpireTime();
        String jwtToken = JWT.create()
                             .withClaim("userId",userId)
                             .withClaim("scope",scope)
                             .withClaim("currentTime",time.get("currentTime"))
                             .withClaim("expireTime",time.get("expireTime"))
                             .sign(jwtAlgorithm);
        return jwtToken;
    }

    /**
     * 生成令牌的过期时间
     * @return
     */
    private static Map<String,Long> makeJwtTokenExpireTime() {
        Map<String,Long> time = new HashMap<>();
        Long currentTime = System.currentTimeMillis();
        Long expireTime = currentTime + JwtToken.expireTime * 1000;
        time.put("currentTime",currentTime);
        time.put("expireTime",expireTime);
        System.out.println("current "+ time.get("currentTime"));
        System.out.println("expire "+ time.get("expireTime"));
        return time;
    }

    /**
     * 验证token的合法性
     * @param token
     * @return
     */
    public static Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtSign);
            //构建一个jwt的校验器
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            return false;
        }
        return true;
    }

    /**
     * 校验token，并获取一个Claim. 因为大多数情况下，我们不仅仅只需要校验token是否合法，还需要
     * 获取token中的一些信息，这些信息就是保存在Claim对象中的
     * @param token
     * @return
     */
    public static Map<String,Claim> getClaimByVerify(String token) {
        DecodedJWT jwtClaim;
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtSign);
            //构建一个jwt的校验器
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwtClaim = verifier.verify(token);

        } catch (JWTVerificationException exception){
            System.out.println("解析令牌发生错误");
            return null;
        }
        return jwtClaim.getClaims();
    }

}
