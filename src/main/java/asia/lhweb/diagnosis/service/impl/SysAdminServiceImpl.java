package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.AdminConstant;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysAdminMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.dto.SysAdminDTO;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        implements SysAdminService {
    @Resource
    private SysAdminMapper sysAdminMapper;

    @Resource
    private RedisCacheUtil redisCacheUtil;

    @Override
    public LoginAdminVO adminLogin(String sysAccount, String sysPassword, HttpServletRequest request) {
        // 1 校验
        if (!StringUtils.hasLength(sysAccount) || !StringUtils.hasLength(sysPassword)) {
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
        SysAdmin loginSysAdmin = sysAdminMapper.selectOne(sysAccount, encryptPassword);
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
    public String createToken(LoginAdminVO loginAdminVO) {
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
    private String createToken(Map<String, Object> claims) {
        String token = JwtUtil.createJWT(BaseConstant.JWT_EXPIRE_TIME, claims);
        System.err.println("token:" + token);
        if (!StringUtils.hasText(token)) {
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
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.TOKEN_NAME);
        if (!StringUtils.hasText(token)) {
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
        loginAdminVO.setId(sysAdmin.getId());
        loginAdminVO.setSysAccount(sysAdmin.getSysAccount());
        loginAdminVO.setAdminName(sysAdmin.getAdminName());
        loginAdminVO.setAvatar(sysAdmin.getAvatar());
        loginAdminVO.setCreateTime(sysAdmin.getCreateTime());
        loginAdminVO.setPhone(sysAdmin.getPhone());
        loginAdminVO.setAdminRoleId(sysAdmin.getAdminRoleId());
        return loginAdminVO;
    }

    /**
     * 没有超级管理员
     * 判断是否是本人或者是管理员
     *
     * @param request 请求
     */
    @Override
    public void noSuperAdmin(HttpServletRequest request) {
        // 通过HttpServletRequest获取当前登录的管理员信息
        LoginAdminVO loginAdminVO = getLoginAdminVO(request);
        Integer adminRoleId = loginAdminVO.getAdminRoleId();
        if (!Objects.equals(adminRoleId, BaseConstant.SUPER_ADMIN_ID)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "没有该权限");
        }
    }

    @Override
    public LoginAdminVO getLoginAdminVO(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.TOKEN_NAME);
        if (token == null || "".equals(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "token为空");
        }
        LoginAdminVO tokenInRedis;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String adminAccount = claims.get("sysAccount", String.class);
            if (!redisCacheUtil.hasKey(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount))) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }
            tokenInRedis = redisCacheUtil.getCacheObject(String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount));
            if (tokenInRedis == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }
            String redisToken = tokenInRedis.getToken();
            if (!token.equals(redisToken)) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }
        } catch (Exception e) {// 说明解密失败 客户端返回信息
            e.printStackTrace();
            throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
        }

        return tokenInRedis;
    }

    /**
     * 判断当前用户是否是超级管理员
     *
     * @param request HttpServletRequest对象，用于获取当前请求的信息
     * @return boolean 返回用户是否为超级管理员的状态，是则返回true，否则返回false
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        // 从请求中获取登录管理员的信息
        LoginAdminVO loginAdminVO = getLoginAdminVO(request);
        // 判断登录管理员的角色ID是否等于超级管理员的角色ID
        return Objects.equals(loginAdminVO.getAdminRoleId(), AdminConstant.SUPER_ADMIN_ROLE_ID);
    }


    /**
     * 是否是超级管理员
     *
     * @param loginAdminVO 登录管理员
     * @return boolean 返回用户是否为超级管理员的状态，是则返回true，否则返回false
     */
    @Override
    public boolean isAdmin(LoginAdminVO loginAdminVO) {
        return Objects.equals(loginAdminVO.getAdminRoleId(), AdminConstant.SUPER_ADMIN_ROLE_ID);
    }

    @Override
    public PageResult<SysAdmin> page(SysAdminDTO sysAdminDTO) {
        int pageNo = sysAdminDTO.getPageNo();
        int pageSize = sysAdminDTO.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<SysAdmin> adminPage = sysAdminMapper.selectAllIf(sysAdminDTO);
        if (adminPage == null || adminPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<SysAdmin> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) adminPage.getTotal());
        pageResult.setItems(adminPage.getResult());
        pageResult.setPageTotalCount(adminPage.getPages());
        return pageResult;
    }

}
