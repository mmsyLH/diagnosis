package asia.lhweb.diagnosis.service;

import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysRole;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role(系统角色表)】的数据库操作Service
* @createDate 2024-04-28 20:11:50
*/
public interface SysRoleService{
    /**
     * 通过admin id和menu id删除角色
     * 通过admin id和menu id删除角色
     * 通过admin id和菜单id删除菜单
     *
     * @param menuIdS 菜单id
     * @param adminId 管理员id
     * @return boolean
     */
    boolean deleteRoleByAdminIdAndMenuIdS(List<Integer> menuIdS, Integer adminId);

    /**
     * 通过admin id和菜单id添加角色
     *
     * @param menuIds 菜单id
     * @param adminId 管理员id
     * @return boolean
     */
    boolean addRoleByAdminIdAndMenuIdS(List<Integer> menuIds, Integer adminId);

    /**
     * 页面
     *
     * @param sysRole 系统作用
     * @return {@link PageResult}<{@link SysRole}>
     */
    PageResult<SysRole> page(SysRole sysRole);

    /**
     * 按角色id和菜单id删除角色
     *
     * @param menuIds 菜单id
     * @param roleId  角色id
     * @return boolean
     */
    boolean deleteRoleByRoleIdAndMenuId(List<Integer> menuIds, Integer roleId);

    boolean addRoleByRoleIdAndMenuId(List<Integer> menuIds, Integer roleId);
}
