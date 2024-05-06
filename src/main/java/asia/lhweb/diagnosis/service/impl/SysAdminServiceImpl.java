package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysAdminMapper;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Administrator
* @description 针对表【sys_admin(系统后台)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class SysAdminServiceImpl
implements SysAdminService{
    @Resource
    private SysAdminMapper sysAdminMapper;

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Override
    public LoginAdminVO adminLogin(String sysAccount, String sysPassword, HttpServletRequest request) {
        // 1 校验
        if (!StringUtils.hasLength(sysAccount)||!StringUtils.hasLength(sysPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户或者密码为空");
        }
        if (sysAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度不能小于4位");
        }
        if (sysPassword.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于4");
        }
        // 账户不能包含特殊字符
        String validRule = "^[a-zA-Z\\d]+$";
        Matcher matcher = Pattern.compile(validRule).matcher(sysAccount);
        // 如果包含非法字符，则返回
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含非法字符");
        }

        // 2 加密 import org.springframework.util.DigestUtils;
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysPassword).getBytes());


        // 查询用户是否存在
        SysAdmin loginSysAdmin = sysAdminMapper.selectOne(sysAccount,encryptPassword);
        // 用户不存在
        if (loginSysAdmin == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或者密码错误 ");
        }
        // 3 脱敏
        LoginAdminVO cleanAdmin = getSafetyAdmin(loginSysAdmin);

        // 4 token
        String token = createToken(cleanAdmin);
        cleanAdmin.setToken(token);

        // 5 redis存token 毫秒
        redisCacheUtil.setCacheObject(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, cleanAdmin.getSysAccount()), cleanAdmin, BaseConstant.JWT_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return cleanAdmin;
    }

    /**
     * 创建令牌
     *
     * @param loginAdminVO 登录管理员
     * @return 令牌
     */
    public String createToken(LoginAdminVO loginAdminVO)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sysAccount", loginAdminVO.getSysAccount());
        claims.put("adminName", loginAdminVO.getAdminName());
        claims.put("phone", loginAdminVO.getPhone());
        claims.put("avatar", loginAdminVO.getAvatar());
        return createToken(claims);
    }
    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims)
    {
        String token = JwtUtil.createJWT(BaseConstant.JWT_EXPIRE_TIME, claims);
        System.err.println("token:"+token);
        if (!StringUtils.hasText(token)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "令牌生成失败");
        }
        return token;
    }
    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request)
    {
        String token = request.getHeader(BaseConstant.TOKEN_NAME);
        if (!StringUtils.hasText(token)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "令牌不能为空");
        }
        return token;
    }

    /**
     * 获得安全管理
     *
     * @param sysAdmin 系统管理员
     * @return {@link LoginAdminVO}
     */
    @Override
    public LoginAdminVO getSafetyAdmin(SysAdmin sysAdmin) {
        LoginAdminVO loginAdminVO = new LoginAdminVO();
        loginAdminVO.setSysAccount(sysAdmin.getSysAccount());
        loginAdminVO.setAdminName(sysAdmin.getAdminName());
        loginAdminVO.setAvatar(sysAdmin.getAvatar());
        loginAdminVO.setCreateTime(sysAdmin.getCreateTime());
        loginAdminVO.setPhone(sysAdmin.getPhone());
        return loginAdminVO;
    }
}
