package asia.lhweb.diagnosis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Mybatis工具类
 *
 * @author 罗汉
 * @date 2023/09/15
 */
public class MybatisUtils {
    private static SqlSessionFactory sqlSessionFactory;

    private static String resource = "mybatis-config.xml";

    static {
        try {//import org.apache.ibatis.io.Resources;
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1. 获得 SqlSession 的实例
     * 2. SqlSession 提供了对数据库执行 SQL 命令所需的所有方法。
     * 3. 通过 SqlSession 实例来直接执行已映射的 SQL 语句
     *
     * @return
     */
    public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession();
    }
    public static SqlSession getSqlSession(Boolean autoCommit) {
        return sqlSessionFactory.openSession(autoCommit);
    }
}
