package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.AdminConstant;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.CounselorMapper;
import asia.lhweb.diagnosis.mapper.SysAdminMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.Counselor;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.domain.SysAdminUser;
import asia.lhweb.diagnosis.model.dto.SysAdminDTO;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.utils.JwtUtil;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.jsonwebtoken.Claims;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Resource
    private CounselorMapper counselorMapper;

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

        if (loginSysAdmin.getAdminRoleId()!=3&&loginSysAdmin.getStatus()!=1){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "您的账户已被禁用。请联系超级管理员");
        }

        // 3 脱敏
        LoginAdminVO cleanAdmin = getSafetyAdmin(loginSysAdmin);

        // 3.5补全信息
        Integer id = cleanAdmin.getId();
        Counselor counselor= counselorMapper.selectOneByAdminId(id);
        if (counselor!=null){
            cleanAdmin.setCounselorName(counselor.getCounselorName());
            cleanAdmin.setCounselorID(counselor.getId());
        }

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
    private String getAdminToken(HttpServletRequest request) {
        String token = request.getHeader(BaseConstant.ADMIN_TOKEN_NAME);
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
        loginAdminVO.setMoney(sysAdmin.getMoney());
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
        if (request == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "请求为空");
        }

        String token = request.getHeader(BaseConstant.ADMIN_TOKEN_NAME);
        if (!StringUtils.hasLength(token)) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "token为空");
        }

        LoginAdminVO loginAdminVO;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            System.err.println(claims);
            String adminAccount = claims.get("sysAccount", String.class);
            String redisKey = String.format(BaseConstant.REDIS_ADMIN_KEY_FORMAT, adminAccount);
            if (!redisCacheUtil.hasKey(redisKey)) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }

            // 获取缓存中的管理员信息
            loginAdminVO = redisCacheUtil.getCacheObject(redisKey);
            if (loginAdminVO == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }

            // 校验Token是否一致
            String redisToken = loginAdminVO.getToken();
            if (!token.equals(redisToken)) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
            }

            // 从数据库获取管理员信息，确保数据最新
            SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(Long.valueOf(loginAdminVO.getId()));
            if (sysAdmin == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN, "管理员信息不存在");
            }

            // 更新安全的管理员信息
            loginAdminVO = getSafetyAdmin(sysAdmin);
            loginAdminVO.setToken(token);
            // 更新缓存中的管理员信息
            redisCacheUtil.setCacheObject(redisKey, loginAdminVO, BaseConstant.REDIS_EXPIRATION_TIME, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.NOT_LOGIN, "Token验证失败或已过期，请重新登录！");
        }

        return loginAdminVO;
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

    /**
     * Admin用户页面
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link PageResult}<{@link SysAdminUser}>
     */
    @Override
    public PageResult<SysAdminUser> adminUserPage(SysAdminUser sysAdminUser) {
        int pageNo = sysAdminUser.getPageNo();
        int pageSize = sysAdminUser.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        System.err.println(sysAdminUser);
        Page<SysAdminUser> sysAdminUserPage = sysAdminMapper.selectSysAdminUserIf(sysAdminUser);
        if (sysAdminUserPage == null || sysAdminUserPage.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<SysAdminUser> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) sysAdminUserPage.getTotal());
        pageResult.setItems(sysAdminUserPage.getResult());
        pageResult.setPageTotalCount(sysAdminUserPage.getPages());
        return pageResult;
    }

    /**
     * 添加管理
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addAdmin(SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        SysAdmin sysAdmin = new SysAdmin();
        BeanUtils.copyProperties(sysAdminUser, sysAdmin);
        //查询账户是否已经存在
        SysAdmin sysAdmin2 = new SysAdmin();
        sysAdmin2.setSysAccount(sysAdmin.getSysAccount());
        SysAdmin sysAdmin1 = sysAdminMapper.selectOneByActive(sysAdmin2);
        if (sysAdmin1!=null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户已经存在");
        }
        //md5加密加盐
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysAdmin.getSysPassword()).getBytes());
        sysAdmin.setSysPassword(encryptPassword);
        int res = sysAdminMapper.insert(sysAdmin);
        if (res <=0) {
          throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"添加管理员错误");
        }
        return res;
    }

    /**
     * 添加咨询师
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addAdminAndCounselor(SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysAdmin sysAdmin = new SysAdmin();
        BeanUtils.copyProperties(sysAdminUser, sysAdmin);
        //查询账户是否已经存在
        SysAdmin sysAdmin2 = new SysAdmin();
        sysAdmin2.setSysAccount(sysAdmin.getSysAccount());
        SysAdmin sysAdmin1 = sysAdminMapper.selectOneByActive(sysAdmin2);
        if (sysAdmin1!=null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"咨询师账户已经存在");
        }
        //md5加密加盐
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysAdmin.getSysPassword()).getBytes());
        sysAdmin.setSysPassword(encryptPassword);
        int res = sysAdminMapper.insert(sysAdmin);
        if (res <=0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"添加咨询师错误");
        }

        Counselor counselor = new Counselor();
        BeanUtils.copyProperties(sysAdminUser, counselor);
        counselor.setCounselorName(sysAdminUser.getAdminName());
        counselor.setEducationlv(sysAdminUser.getEducationLv());
        counselor.setAdminId(sysAdmin.getId());
        int insertRes2 = counselorMapper.insert(counselor);
        if (insertRes2 <=0) {
            throw  new BusinessException(ErrorCode.SYSTEM_ERROR,"添加咨询师错误");
        }
        return insertRes2;
    }

    /**
     * 更新状态
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    @Override
    public int updateStatus(SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysAdmin sysAdmin1 = new SysAdmin();
        sysAdmin1.setId(sysAdminUser.getId());
        SysAdmin sysAdmin2 = sysAdminMapper.selectOneByActive(sysAdmin1);
        if (sysAdmin2 == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在");


        }
        sysAdmin2.setStatus(sysAdminUser.getStatus());
        int res = sysAdminMapper.updateByPrimaryKeySelective(sysAdmin2);
        if (res >0) {
            return res;
        }
        return 0;
    }

    /**
     * 重置密码
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    @Override
    public int resetPassword(SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (sysAdminUser.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (sysAdminUser.getSysPassword() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SysAdmin sysAdmin = new SysAdmin();
        sysAdmin.setId(sysAdminUser.getId());
        SysAdmin sysAdmin1 = sysAdminMapper.selectOneByActive(sysAdmin);
        if (sysAdmin1 == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //设置密码为加密后的密码
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + sysAdminUser.getSysPassword()).getBytes());
        sysAdmin1.setSysPassword(encryptPassword);
        int res = sysAdminMapper.updateByPrimaryKeySelective(sysAdmin1);
        if (res <=0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return res;
    }

    /**
     * 删除
     *
     * @param id id
     * @return int
     */
    @Override
    public int delete(Integer id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (sysAdminMapper.deleteByPrimaryKey(Long.valueOf(id)) <=0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return 1;
    }

}
