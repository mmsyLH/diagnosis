package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.mapper.SysUserMapper;
import asia.lhweb.diagnosis.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_user(系统用户表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class SysUserServiceImpl
implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteById(int i) {
        Long id=Long.valueOf(i);
        int res=sysUserMapper.deleteByPrimaryKey(id);
        return res;
    }
}
