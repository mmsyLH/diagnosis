package asia.lhweb.diagnosis.mapper;

import asia.lhweb.diagnosis.model.domain.SysAdminCounselor;

/**
* @author Administrator
* @description 针对表【sys_admin_counselor(系统管理员和咨询师对应表)】的数据库操作Mapper
* @createDate 2024-04-29 20:24:12
* @Entity asia.lhweb.diagnosis.model.domain.SysAdminCounselor
*/
public interface SysAdminCounselorMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SysAdminCounselor record);

    int insertSelective(SysAdminCounselor record);

    SysAdminCounselor selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysAdminCounselor record);

    int updateByPrimaryKey(SysAdminCounselor record);

}
