package asia.lhweb.diagnosis.constant;

/**
 * 一卡通项目的常量
 *
 * @author 罗汉
 * @date 2024/03/11
 */
public interface BaseConstant {
    /**
     * 盐
     */
    String SALT = "LH";
    /**
     * 项目名称
     */
    String SERVER_NAME = "Diagnosis";

    String CODE_KEY = "Diagnosis-Code";
    String REDIS_ADMIN_KEY_FORMAT = "diagnosis:admin:loginAdmin:%s";
    String REDIS_USER_KEY_FORMAT = "diagnosis:user:loginAdmin:%s";
    /**
     * 应用名称
     */
    String WEBAPP_NAME = "webapp";
    String TOKEN_NAME = "Diagnosis-Token";
    /**
     * 最大请求大小
     */
    int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 设置最大请求大小为50MB
    /**
     * JWT过期时间
     */
    int JWT_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    /**
     * 用户登录状态的键
     */
    String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户权限 1表示管理员
     */
    int ADMIN_ROLE = 1;
    /**
     * 用户权限 0表示管理员
     */
    int DEFAULT_ROLE = 0;

    /**
     * 用户注册成功
     */
    String USER_REGISTER_SUCCESS = "注册成功";
    /**
     * 用户退出成功
     */
    String USER_LOGOUT_SUCCESS = "退出登录成功";
    /**
     * 用户登录成功
     */
    String USER_LOGIN_SUCCESS = "登录成功";
    String ADMIN_LOGIN_SUCCESS = "登录成功";
    /**
     * 令牌前缀
     */
    String LOGIN_ADMIN_KEY = "login_admin_key";
    String LOGIN_USER_KEY = "login_user_key";
}
