package asia.lhweb.diagnosis.interceptor;


import asia.lhweb.diagnosis.common.Result;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import com.alibaba.fastjson.JSON;
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
    @Resource
    private SysAdminService sysAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //解析token
        String token = request.getHeader(BaseConstant.TOKEN_NAME);
        if (token == null || "".equals(token)) {
            response.getWriter().write(JSON.toJSONString(Result.error("token为空")));
            return false;
        }
        LoginAdminVO tokenInRedis;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String adminAccount = claims.get("sysAccount", String.class);
            if (!redisCacheUtil.hasKey(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount))) {
                response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
                return false;
            }
            tokenInRedis = redisCacheUtil.getCacheObject(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount));
            if (tokenInRedis == null) {
                response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
                return false;
            }
            String redisToken = tokenInRedis.getToken();
            if (!token.equals(redisToken)) {
                response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
                return false;
            }
        } catch (Exception e) {// 说明解密失败 客户端返回信息
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(Result.error(403,"Token验证失败或已过期，请重新登录！")));
            return false;
        }
        // 放行
        return true;
    }
}
