package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysAdminMapper;
import asia.lhweb.diagnosis.mapper.SysMenuMapper;
import asia.lhweb.diagnosis.mapper.SysRoleMapper;
import asia.lhweb.diagnosis.mapper.SysRoleMenuMapper;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.domain.SysRole;
import asia.lhweb.diagnosis.service.SysRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_role(系统角色表)】的数据库操作Service实现
 * @createDate 2024-04-28 20:11:50
 */
@Service
public class SysRoleServiceImpl
        implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysAdminMapper sysAdminMapper;
    @Resource
    private SysMenuMapper sysMenuMapper;


    /**
     * 通过admin id和menu ids删除角色
     *
     * @param menuIdS 菜单id
     * @param adminId 管理员id
     * @return boolean
     */
    @Override
    public boolean deleteRoleByAdminIdAndMenuIdS(List<Integer> menuIdS, Integer adminId) {
        // 1、通过adminId查询权限
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(Long.valueOf(adminId));
        if (sysAdmin == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "该管理员不存在");
        }
        // 3、通过查询的权限和menuIdS去进行删除对应的权限
       int res= sysRoleMenuMapper.deleteRoleByRouleIdAndMenuIdS(menuIdS, sysAdmin.getAdminRoleId());
        if (res<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除权限失败");
        }
        return true;

    }

    @Override
    public boolean addRoleByAdminIdAndMenuIdS(List<Integer> menuIds, Integer adminId) {
        // 1、通过adminId查询权限
        SysAdmin sysAdmin = sysAdminMapper.selectByPrimaryKey(Long.valueOf(adminId));
        if (sysAdmin == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR, "该管理员不存在");
        }
        // 2、查询添加的菜单是否每个都存在
        menuIds.forEach(menuId -> {
            if (sysMenuMapper.selectByPrimaryKey(Long.valueOf(menuId)) == null) {
                throw new BusinessException(ErrorCode.NULL_ERROR, "添加的菜单中有不存在的菜单");
            }
        });

        // 3、添加
       int res=sysRoleMenuMapper.insertRoleByRouleIdAndMenuIdS(menuIds, sysAdmin.getAdminRoleId());
        if (res<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加权限失败");
        }
        return true;
    }

    @Override
    public PageResult<SysRole> page(SysRole sysRole) {
        int pageNo = sysRole.getPageNo();
        int pageSize = sysRole.getPageSize();
        PageHelper.startPage(pageNo, pageSize);
        // 紧跟着的第一个select方法会被分页
        Page<SysRole> sysRoles = sysRoleMapper.selectAllIf(sysRole);
        if (sysRoles == null || sysRoles.getResult().size() == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        PageResult<SysRole> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        pageResult.setTotalRow((int) sysRoles.getTotal());
        pageResult.setItems(sysRoles.getResult());
        pageResult.setPageTotalCount(sysRoles.getPages());
        return pageResult;
    }

    @Override
    public boolean deleteRoleByRoleIdAndMenuId(List<Integer> menuIds, Integer roleId) {
        // 3、通过查询的权限和menuIdS去进行删除对应的权限
        int res= sysRoleMenuMapper.deleteRoleByRouleIdAndMenuIdS(menuIds, roleId);
        if (res<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "删除权限失败");
        }
        return true;
    }

    @Override
    public boolean addRoleByRoleIdAndMenuId(List<Integer> menuIds, Integer roleId) {
        // 2、查询添加的菜单是否每个都存在
        menuIds.forEach(menuId -> {
            if (sysMenuMapper.selectByPrimaryKey(Long.valueOf(menuId)) == null) {
                throw new BusinessException(ErrorCode.NULL_ERROR, "添加的菜单中有不存在的菜单");
            }
        });

        // 3、添加
        int res=sysRoleMenuMapper.insertRoleByRouleIdAndMenuIdS(menuIds,roleId);
        if (res<=0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加权限失败");
        }
        return true;
    }
}
