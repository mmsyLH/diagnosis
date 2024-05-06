package asia.lhweb.diagnosis.service.impl;

import asia.lhweb.diagnosis.common.BaseContext;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysUserMapper;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.vo.LoginUserVO;
import asia.lhweb.diagnosis.service.SysUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Administrator
* @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class SysUserServiceImpl
implements SysUserService{
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 按id删除
     *
     * @param i 我
     * @return int
     */
    @Override
    public int deleteById(int i) {
        Long id=Long.valueOf(i);
        int res=sysUserMapper.deleteByPrimaryKey(id);
        return res;
    }

    /**
     * 用户注销
     *
     * @param request 请求
     * @return int
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录
        // request.getSession().removeAttribute(BaseConstant.USER_LOGIN_STATE);
        //jwt去除登录令牌
        BaseContext.removeCurrentId();
        return 1;
    }

    /**
     * 用户登录
     *
     * @param userAccount  用户帐户
     * @param userPassword 用户密码
     * @param request      请求
     * @return {@link LoginUserVO}
     */
    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1 校验
        if (!StringUtils.hasLength(userAccount)||!StringUtils.hasLength(userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "用户或者密码为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度不能小于4位");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度不能小于8");
        }
        // 账户不能包含特殊字符
        String validRule = "^[a-zA-Z\\d]+$";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        // 如果包含非法字符，则返回
        if (!matcher.matches()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不能包含非法字符");
        }

        // 2 加密 import org.springframework.util.DigestUtils;
        String encryptPassword = DigestUtils.md5DigestAsHex((BaseConstant.SALT + userPassword).getBytes());


        // 查询用户是否存在
        SysUser user = sysUserMapper.selectOne(userAccount,encryptPassword);
        // 用户不存在
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或者密码错误 ");
        }
        // 3 脱敏
        LoginUserVO cleanUser = getSafetyUser(user);

        return cleanUser;
    }

    /**
     * 获得安全用户
     *
     * @param user 用户
     * @return {@link LoginUserVO}
     */
    private LoginUserVO getSafetyUser(SysUser user) {
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setUserAccount(user.getUserAccount());
        loginUserVO.setUserName(user.getUserName());
        loginUserVO.setAvatar(user.getAvatar());
        loginUserVO.setPhone(user.getPhone());
        loginUserVO.setAddress(user.getAddress());
        loginUserVO.setSex(user.getSex());
        loginUserVO.setAge(user.getAge());
        loginUserVO.setMoney(user.getMoney());
        return loginUserVO;
    }
}
