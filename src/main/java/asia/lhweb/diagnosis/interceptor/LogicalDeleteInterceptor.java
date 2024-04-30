package asia.lhweb.diagnosis.interceptor;

import asia.lhweb.diagnosis.annotation.DeleteMarker;
import asia.lhweb.diagnosis.annotation.MyEntity;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 自定义拦截器，1 用于将物理删除操作转换为逻辑删除操作，并加入条件判断逻辑删除为activeValue的值。
 *  2 拦截更新语句 后面添加逻辑删除字段
 *  3 拦截查询语句 后面添加逻辑删除字段
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class LogicalDeleteInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        if (mappedStatement == null) {
            throw new IllegalArgumentException("MappedStatement is null");
        }

        String sql = mappedStatement.getBoundSql(parameter).getSql();
        //删除
        if (isDeleteOperation(sql)) {
            handleDelete(invocation, mappedStatement, parameter);
        }
        // 更新和查询
        // else if (isQueryOperation(sql) || isUpdateOperation(sql)) {
        //     handleQueryOrUpdate(invocation, mappedStatement, parameter);
        // }
        return invocation.proceed();
    }

    private boolean isDeleteOperation(String sql) {
        return sql.toUpperCase().startsWith("DELETE");
    }

    private boolean isQueryOperation(String sql) {
        return sql.toUpperCase().startsWith("SELECT");
    }

    private boolean isUpdateOperation(String sql) {
        return sql.toUpperCase().startsWith("UPDATE");
    }

    private void handleDelete(Invocation invocation, MappedStatement mappedStatement, Object parameter) throws Throwable {
        Class<?> entityClass = getEntityClass(mappedStatement);
        String deleteMarkerColumn = getDeleteMarkerColumnName(entityClass);
        String[] deleteMarkerValues = getDeleteMarkerValues(entityClass);

        String updatedSql = deleteToUpdateSql(mappedStatement.getBoundSql(parameter).getSql(), deleteMarkerColumn, deleteMarkerValues);

        MappedStatement newMs = copyFromMappedStatement(mappedStatement, updatedSql);
        invocation.getArgs()[0] = newMs;
    }

    private void handleQueryOrUpdate(Invocation invocation, MappedStatement mappedStatement, Object parameter) throws Throwable {
        Class<?> entityClass = getEntityClass(mappedStatement);
        System.out.println("handleQueryOrUpdate():entityClass: " + entityClass);
        String deleteMarkerColumn = getDeleteMarkerColumnName(entityClass);
        String activeValue = getDeleteMarkerValues(entityClass)[0];

        String originalSql = mappedStatement.getBoundSql(parameter).getSql();
        System.out.println("handleQueryOrUpdate():originalSql: " + originalSql);

        // 检查原始SQL中是否已经包含逻辑删除条件
        if (originalSql.toUpperCase().contains(deleteMarkerColumn.toUpperCase())) {
            System.out.println("Logic delete condition already exists in the original SQL, skipping...");
            return;
        }

        // 构建更新语句，加入逻辑删除条件
        String updatedSql = updateSqlWithDeleteMarker(originalSql, deleteMarkerColumn, activeValue);
        System.err.println("-------------------------------handleQueryOrUpdate():updatedSql: " + updatedSql);
        // MappedStatement newMs = copyFromMappedStatement(mappedStatement, updatedSql);
        MappedStatement newMs = copyFromMappedStatementByUpdate(mappedStatement, updatedSql);
        invocation.getArgs()[0] = newMs;
    }


    private Class<?> getEntityClass(MappedStatement mappedStatement) {
        String id = mappedStatement.getId();
        String className = id.substring(0, id.lastIndexOf("."));
        Class<?> mapperClass;
        try {
            mapperClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Mapper class not found", e);
        }

        MyEntity entityAnnotation = mapperClass.getAnnotation(MyEntity.class);
        if (entityAnnotation != null) {
            return entityAnnotation.value();
        } else {
            throw new IllegalArgumentException("Entity class not found");
        }
    }

    private String getDeleteMarkerColumnName(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DeleteMarker.class)) {
                return field.getAnnotation(DeleteMarker.class).columnName();
            }
        }
        return "is_delete";
    }

    private String[] getDeleteMarkerValues(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(DeleteMarker.class)) {
                DeleteMarker deleteMarkerAnnotation = field.getAnnotation(DeleteMarker.class);
                return new String[]{String.valueOf(deleteMarkerAnnotation.activeValue()), String.valueOf(deleteMarkerAnnotation.deletedValue())};
            }
        }
        return new String[]{"0", "1"};
    }

    private String deleteToUpdateSql(String sql, String deleteMarkerColumn, String[] deleteMarkerValues) {
        String activeValue = deleteMarkerValues[0];
        String deletedValue = deleteMarkerValues[1];
        String updatedSql = sql;
        System.err.println("updatedSql: " + updatedSql);
        updatedSql = updatedSql.replaceAll("(?i)DELETE FROM", "UPDATE")
                .replaceAll(deleteMarkerColumn + "\\s*=\\s*" + activeValue, deleteMarkerColumn + " = " + deletedValue)
                .replaceFirst("(?i)WHERE", "SET " + deleteMarkerColumn + " = " + deletedValue + " WHERE");
        return updatedSql;
    }

    private String updateSqlWithDeleteMarker(String originalSql, String deleteMarkerColumn, String activeValue) {
        // 找到 WHERE 子句的位置
        int whereIndex = originalSql.toUpperCase().indexOf("WHERE");
        if (whereIndex == -1) {
            throw new IllegalArgumentException("WHERE clause not found in the original SQL");
        }

        // 添加逻辑删除条件到 WHERE 子句中
        String updatedSql = originalSql+ " and " + deleteMarkerColumn + " = " + activeValue + "";
        return updatedSql;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, String sql) {
        BoundSql boundSql = ms.getBoundSql(null);
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String property = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(property)) {
                newBoundSql.setAdditionalParameter(property, boundSql.getAdditionalParameter(property));
            }
        }
        return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType())
                .resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .databaseId(ms.getDatabaseId())
                .lang(ms.getLang())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultMaps(ms.getResultMaps())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache())
                .build();
    }
    private MappedStatement copyFromMappedStatementByUpdate(MappedStatement ms, String sql) {
        BoundSql boundSql = ms.getBoundSql(null);
        List<ParameterMapping> parameterMappings = new ArrayList<>();
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            // 检查属性是否在新 SQL 中有对应的占位符
            if (sql.indexOf(mapping.getProperty()) != -1) {
                parameterMappings.add(mapping);
            }
        }

        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMappings, boundSql.getParameterObject());
        for (ParameterMapping mapping : parameterMappings) {
            String property = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(property)) {
                newBoundSql.setAdditionalParameter(property, boundSql.getAdditionalParameter(property));
            }
        }
        return new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), new BoundSqlSqlSource(newBoundSql), ms.getSqlCommandType())
                .resource(ms.getResource())
                .fetchSize(ms.getFetchSize())
                .statementType(ms.getStatementType())
                .keyGenerator(ms.getKeyGenerator())
                .databaseId(ms.getDatabaseId())
                .lang(ms.getLang())
                .timeout(ms.getTimeout())
                .parameterMap(ms.getParameterMap())
                .resultMaps(ms.getResultMaps())
                .cache(ms.getCache())
                .flushCacheRequired(ms.isFlushCacheRequired())
                .useCache(ms.isUseCache())
                .build();
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private static class BoundSqlSqlSource implements SqlSource {
        private final BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
