package asia.lhweb.diagnosis.interceptor;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author :罗汉
 * @date : 2024/4/24
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader(BaseConstant.TOKEN_NAME);
        if (token == null || "".equals(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "token为空");
        }
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String adminAccount = claims.get("sysAccount", String.class);
            if (!redisCacheUtil.hasKey(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount))){
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }
            LoginAdminVO tokenInRedis = redisCacheUtil.getCacheObject(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount));
            if (tokenInRedis == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN,"Token验证失败或已过期，请重新登录！");
            }
            String redisToken = tokenInRedis.getToken();
            if (!token.equals(redisToken)){
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }
        } catch (Exception e) {// 说明解密失败 客户端返回信息
            e.printStackTrace();
            throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
        }
        // 放行
        return true;
    }
}
