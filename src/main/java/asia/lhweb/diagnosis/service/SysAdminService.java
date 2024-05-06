package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.model.domain.SysAdmin;
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
}
