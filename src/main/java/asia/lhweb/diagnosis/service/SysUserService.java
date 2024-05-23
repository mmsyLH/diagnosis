package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import asia.lhweb.diagnosis.model.vo.LoginUserVO;
import asia.lhweb.diagnosis.model.vo.SysUserStatisticsVO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
* @author Administrator
* @description 针对表【sys_user(系统用户表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface SysUserService {

    /**
     * 按id删除
     *
     * @param i 我
     * @return int
     */
    boolean deleteById(int i);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return int
     */
    int userLogout(HttpServletRequest request);

    /**
     * 用户登录接口。
     * 通过用户账号和密码验证用户身份，并返回登录信息。
     *
     * @param userAccount 用户账号，用于身份验证。
     * @param userPassword 用户密码，用于身份验证。
     * @param request HttpServletRequest对象，用于获取客户端信息，例如IP地址等（可选，根据实际需求决定是否使用）。
     * @return LoginUserVO 登录成功返回用户信息的VO对象，包含用户身份标识等信息。
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 页面
     *
     * @param sysUserDTO 系统用户dto
     * @return {@link PageResult}<{@link SysUser}>
     */
    PageResult<SysUser> page(SysUserDTO sysUserDTO);

    /**
     * 获取用户统计信息
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link SysUserStatisticsVO}
     */
    SysUserStatisticsVO getUserStatistics(LocalDate begin, LocalDate end);

    /**
     * 用户注册
     *
     * @param sysUser 系统用户
     * @return int
     */
    int userRegister(SysUser sysUser);

    /**
     * 获取登录用户vo
     *
     * @param request 请求
     * @return {@link LoginUserVO}
     */
    LoginUserVO getLoginUserVO(HttpServletRequest request);

    /**
     * @param sysUser
     * @return int
     */
    int updateUser(SysUser sysUser);

    /**
     * 更新头像url
     *
     * @param userId    用户id
     * @param avatarUrl
     * @return {@link Boolean}
     */
    Boolean updateAvatarUrl(Integer userId, String avatarUrl);


    /**
     * 添加钱
     *
     * @param userId     用户id
     * @param money      钱
     * @param outTradeNo 我们贸易号
     * @return {@link Boolean}
     */
    Boolean addMoney(Integer userId, Double money, String outTradeNo);
}
