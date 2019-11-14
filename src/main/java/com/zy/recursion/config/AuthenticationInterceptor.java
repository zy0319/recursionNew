package com.zy.recursion.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zy.recursion.entity.user;
import com.zy.recursion.service.user.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private userService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("X-Access-Token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(annotation.PassToken.class)) {
            annotation.PassToken passToken = method.getAnnotation(annotation.PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(annotation.UserLoginToken.class)) {
            annotation.UserLoginToken userLoginToken = method.getAnnotation(annotation.UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("403");
                }
                // 获取 token 中的 user id
                String userName;
                try {
                    userName = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                if (userName == null) {
                    throw new RuntimeException("403");
                }
                user userEntity = userService.getUserByName(userName);
                if (userEntity == null || userEntity.getPasswd() == null || userEntity.getPasswd() == ""){
                    throw new RuntimeException("没有此用户或者密码为空");
                }
                //String encondePwd = new BASE64Encoder().encode("123456".getBytes());
                byte[] bytes = new BASE64Decoder().decodeBuffer(userEntity.getPasswd());
                String passwd = new String(bytes, "UTF-8");
                // 验证 token
                //JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("123456")).build();
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(passwd)).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                //将验证通过后的用户信息放到请求中
               httpServletRequest.setAttribute("currentUser", userEntity);
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}