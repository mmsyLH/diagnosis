package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.domain.SysAdminUser;
import asia.lhweb.diagnosis.model.dto.SysAdminDTO;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_admin(系统后台)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface SysAdminService {

    /**
     * 管理员登录
     *
     * @param sysAccount  系统帐户
     * @param sysPassword 系统密码
     * @param request     请求
     * @return {@link LoginAdminVO}
     */
    LoginAdminVO adminLogin(String sysAccount, String sysPassword, HttpServletRequest request);

    LoginAdminVO getSafetyAdmin(SysAdmin sysAdmin);


    void noSuperAdmin(HttpServletRequest request);

    /**
     * 获得登录管理权限
     *
     * @param request 请求
     * @return {@link LoginAdminVO}
     */
    LoginAdminVO getLoginAdminVO(HttpServletRequest request);

    /**
     * 是管理
     *
     * @param request 请求
     * @return boolean
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 是管理
     *
     * @param loginAdminVO 登录管理员
     * @return boolean
     */
    boolean isAdmin(LoginAdminVO loginAdminVO);

    /**
     * 页面
     *
     * @param sysAdminDTO 系统管理员
     * @return {@link PageResult}<{@link SysAdmin}>
     */
    PageResult<SysAdmin> page(SysAdminDTO sysAdminDTO);

    /**
     * Admin用户页面
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link PageResult}<{@link SysAdminUser}>
     */
    PageResult<SysAdminUser> adminUserPage(SysAdminUser sysAdminUser);

    /**
     * 添加管理
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    int addAdmin(SysAdminUser sysAdminUser);

    int addAdminAndCounselor(SysAdminUser sysAdminUser);

    /**
     * 更新状态
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    int updateStatus(SysAdminUser sysAdminUser);

    /**
     * 重置密码
     *
     * @param sysAdminUser 系统管理员用户
     * @return int
     */
    int resetPassword(SysAdminUser sysAdminUser);

    /**
     * 删除
     *
     * @param id id
     * @return int
     */
    int delete(Integer id);
}
