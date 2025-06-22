package org.ssriot.ssr_iot.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aopalliance.intercept.Interceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.ssriot.ssr_iot.utils.JwtUtil;
import org.ssriot.ssr_iot.utils.ThreadLocalUtil;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    //在目标方法执行之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        //获取请求头中的token
        String token = request.getHeader("Authorization");
        //验证token
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存到Threadlocal里面
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //http状态响应码401，不放行
            response.setStatus(401);
            return false;
        }
    }

    //在目标方法执行之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清除ThreadLocal中的数据，防止内存泄露
        ThreadLocalUtil.remove();
    }
}
