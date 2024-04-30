package asia.lhweb.diagnosis.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * JDBCUtils
 *
 * @author 罗汉
 * @version 1.0
 * @date 2024/02/21
 */
public class JDBCUtils {
    private static DataSource ds;

    // 在静态代码块完成 ds初始化
    static {
        Properties properties = new Properties();
        try {
            // 因为我们是web项目，他的工作目录在out, 文件的加载，需要使用类加载器
            // 找到我们的工作目录
            properties.load(JDBCUtils.class.getClassLoader().getResourceAsStream("database.properties"));
            // properties.load(new FileInputStream("src\\druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(properties);
            System.out.println("连接成功！！！！");
        } catch (Exception e) {
            System.out.println("数据库连接失败22");
            e.printStackTrace();
        }

    }

    // 编写getConnection方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 提交事务
     */
    public static void commit(Connection connection) {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }
    }

    /**
     * 回滚 回滚,撤销和连接关联的操作，例如dml操作 删除、修改、添加
     */
    public static void rollBack(Connection connection) {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(connection);
            }
        }
    }
    public static void close(Connection connection, Statement statement) {
        close(connection, statement,null);
    }
    public static void close(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
