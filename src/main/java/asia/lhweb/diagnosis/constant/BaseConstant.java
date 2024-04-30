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
    /**
     * 应用名称
     */
    String WEBAPP_NAME = "webapp";
    String TOKEN_NAME = "Diagnosis-Token";
    /**
     * 最大请求大小
     */
    int MAX_REQUEST_SIZE = 1024 * 1024*50; // 设置最大请求大小为50MB
    /**
     * JWT过期时间
     */
    long JWT_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
}
