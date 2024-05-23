package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysLog;
import com.github.pagehelper.Page;

/**
* @author Administrator
* @description 针对表【sys_log(系统日志表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysLog
*/
public interface SysLogMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysLog record);
    
    int updateByPrimaryKey(SysLog record);

    Page<SysLog> selectAllIf(SysLog sysLog);
}
