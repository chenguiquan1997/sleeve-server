package com.quan.windsleeve.core.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.quan.windsleeve.core.annotation.ScopeLevel;
import com.quan.windsleeve.exception.http.JwtVerifyException;
import com.quan.windsleeve.exception.http.NoAuthorizationException;
import com.quan.windsleeve.model.User;
import com.quan.windsleeve.service.IUserService;
import com.quan.windsleeve.util.JwtToken;
import com.quan.windsleeve.util.LocalUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class JwtTokenInterceptor extends HandlerInterceptorAdapter {

    private final Logger log = LoggerFactory.getLogger(JwtTokenInterceptor.class);

    @Autowired
    private IUserService userService;

    /**
     *
     * @param request
     * @param response
     * @param handler 就是当前拦截器，需要拦截的对象
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        System.out.println("当前接口的api："+request.getRequestURI());
        //判断当前被访问的api是否需要权限认证
        Optional<ScopeLevel> scopeLevel = getAPIScopeLevel(handler);
        //如果当前api不存在@scopeLevel注解，证明它是公开的api接口
        if(!scopeLevel.isPresent()) {
            return true;
        }

        //从request中获取token
        String token = getTokenFromHeader(request);
        log.info("从header中获取到的token=[{}]",token);
        //验证token
        Map<String,Claim> claimMap = new HashMap<>();
        try {
            claimMap = JwtToken.getClaimByVerify(token);
        }catch (TokenExpiredException e){
            log.info("拦截器解析token时，token过期，抛出异常, token=[{}]",token,e);
            throw new JwtVerifyException(10008);
        }
        catch (JWTVerificationException e) {
            log.info("拦截器解析token时，出现异常, token=[{}]",token,e);
            throw new JwtVerifyException(10008);
        }
        //从 Claim 中获取 scope 和 userId
        Claim scopeClaim = claimMap.get("scope");
        Claim userClaim = claimMap.get("userId");

        Integer tokenLevel = scopeClaim.asInt();
        Long userId = userClaim.asLong();
        // 加入到threadLocal中
        setScopeAndUserToThreadLocal(tokenLevel,userId);
        // 验证当前token是否有权限访问api
        Boolean isPermission = isTokenPermission(tokenLevel,scopeLevel.get());
        if(isPermission) {
          return true;
        }
        log.info("当前用户没有访问这个接口的权限, userId=[{}], userScope=[{}], apiScope=[{}], ",userId,tokenLevel,scopeLevel.get(),request.getRequestURI());
        return false;
    }

    /**
     * 将当前用户 访问 api 的 scope 权限，和 用户数据 存储到 当前线程的 ThreadLocal 中
     * @param tokenLevel 访问 api 的 scope 权限
     * @param userId
     */
    private void setScopeAndUserToThreadLocal(Integer tokenLevel, Long userId) {
        Optional<User> user = userService.findUserById(userId);
        User u = user.get();
        System.out.println(u.getOpenid());
        if(user.isPresent()) {
            LocalUser.setUser(user.get(),tokenLevel);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        //当前线程执行完任务以后，需要销毁掉内部的user信息
        LocalUser.clear();
    }

    /**
     * 判断当前token是否过期
     * @param expireTime
     */
    private Boolean isTokenExpire(Long expireTime, Long userId) {
        Long currentTime = System.currentTimeMillis();
        //如果当前时间 < 过期时间，那么认为当前token可以继续使用
        if(currentTime <= expireTime) {
            return true;
        }
        log.error("当前token已过期，请重新获取, expireTime=[{}], userId=[{}]",new Date(expireTime),userId);
        throw new NoAuthorizationException(10003);
    }

    /**
     * 验证当前token的level是否 > 当前api默认的level权限，如果大于，可以访问该api，小于则不可以
     * @param tokenLevel
     * @param scopeLevel
     * @return
     */
    private Boolean isTokenPermission(int tokenLevel, ScopeLevel scopeLevel) {
        int apiScopeLevel = scopeLevel.value();
        System.out.println("当前api接口的访问权限： "+apiScopeLevel);
        //如果token令牌中的level < api中的level，证明用户没有权限访问当前的api接口
        if(tokenLevel < apiScopeLevel) {
            throw new NoAuthorizationException(10002);
        }
        return true;
    }

    /**
     * 从请求头中获取token
     * @param request
     */
    private String getTokenFromHeader(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(jwtToken == null) {
            throw new NoAuthorizationException(10001);
        }
        String[] tokenArr = jwtToken.split(" ");
        String token = tokenArr[1];
        if(token == null || token == "") {
            throw new NoAuthorizationException(10001);
        }
        return token;
    }

    /**
     * 获取当前api方法的scopeLevel
     * @param handler
     * @return Optional<ScopeLevel>
     */
    private Optional<ScopeLevel> getAPIScopeLevel(Object handler) {
        /**
         * HandlerMethod类是Spring MVC的类，可以通过该类获取接口方法中的 注解，参数等
         */
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if(scopeLevel == null) return Optional.empty();
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }
}
