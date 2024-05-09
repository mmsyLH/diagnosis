package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysRole;
import com.github.pagehelper.Page;

/**
* @author Administrator
* @description 针对表【sys_role(系统角色表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysRole
*/
public interface SysRoleMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    Page<SysRole> selectAllIf(SysRole sysRole);
}
