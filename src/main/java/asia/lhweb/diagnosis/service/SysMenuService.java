package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(系统菜单表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface SysMenuService{

    /**
     * 按admin id选择菜单树
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link SysMenuLeftVO}>
     */
    List<SysMenuLeftVO> selectMenuTreeByAdminId(Integer adminId);
}
