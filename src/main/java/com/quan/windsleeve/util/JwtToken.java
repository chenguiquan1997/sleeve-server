package com.quan.windsleeve.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.quan.windsleeve.exception.http.CreateJwtException;
import com.quan.windsleeve.service.impl.WxAuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtToken {

    private static final Logger log = LoggerFactory.getLogger(JwtToken.class);

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
     * 创建jwt Token
     * @param scope 当前令牌可以访问接口的范围
     * @param userId 用户id
     */
    private static String createJwtToken(int scope, Long userId) {
        //获取一个创建jwt令牌的算法
        System.out.println("secret: "+JwtToken.jwtSign);
        Algorithm jwtAlgorithm = Algorithm.HMAC256(JwtToken.jwtSign);
        Map<String,Long> time = makeJwtTokenExpireTime();
        String jwtToken = "";
        try {
            jwtToken = JWT.create()
                    .withClaim("userId",userId)
                    .withClaim("scope",scope)
                    //令牌过期时间
                    .withExpiresAt(new Date(time.get("expireTime")))
                    //签发时间
                    .withIssuedAt(new Date(time.get("currentTime")))
                    .sign(jwtAlgorithm);
        }catch (JWTCreationException e) {
            log.error("创建Token令牌失败, userId=[{}]",userId,e);
            throw new CreateJwtException(10009);
        }
        log.info("用户获取的令牌：[{}], userId=[{}], currentTime=[{}], expireTime=[{}]",jwtToken,userId,time.get("currentTime"),time.get("expireTime"));
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
        System.out.println("current "+ new Date(time.get("currentTime")));
        System.out.println("expire "+ new Date(time.get("expireTime")));
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
        } catch (TokenExpiredException e) {
            log.info("当前token令牌已过期, token=[{}]",token,e);
            return false;
        } catch (JWTVerificationException e){
            log.info("解析token令牌时发生错误, token=[{}]",token,e);
            return false;
        }
        log.info("当前Token有效, token=[{}]",token);
        return true;
    }

    /**
     * 校验token，并获取一个Claim. 因为大多数情况下，我们不仅仅只需要校验token是否合法，还需要
     * 获取token中的一些信息，这些信息就是保存在Claim对象中的
     * @param token 令牌
     * @return Map<String,Claim>
     * @throws JWTVerificationException token令牌校验出错时，抛出的异常
     */
    public static Map<String,Claim> getClaimByVerify(String token) throws JWTVerificationException, TokenExpiredException {
        DecodedJWT jwtClaim;
//        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtSign);
            //构建一个jwt的校验器
            JWTVerifier verifier = JWT.require(algorithm).build();
            jwtClaim = verifier.verify(token);

//        } catch (JWTVerificationException exception){
//            log.error("解析token令牌时发生错误, token=[{}]",token);
//            return null;
//        }
        return jwtClaim.getClaims();
    }

}
