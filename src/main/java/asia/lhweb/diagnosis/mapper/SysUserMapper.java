package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.annotation.MyEntity;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user(系统用户表)】的数据库操作Mapper
* @createDate 2024-04-29 20:20:25
* @Entity asia.lhweb.diagnosis.model.domain.SysUser
*/
@MyEntity(SysUser.class)
public interface SysUserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    List<SysUser> selectList();

    SysUser selectOne(@Param("userAccount") String userAccount, @Param("userPassword") String encryptPassword);

    Page<SysUser> selectAllIf(SysUserDTO sysUserDTO);
}
