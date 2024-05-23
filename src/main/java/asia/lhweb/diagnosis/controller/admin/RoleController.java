package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.enums.OperatorType;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysRole;
import asia.lhweb.diagnosis.model.dto.AddAndDelRoleDTO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/4/26
 */
@RestController
@RequestMapping("/admin/role")
@Api("角色相关接口")
public class RoleController {
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysAdminService sysAdminService;

    /**
     * 通过admin id和菜单id删除权限
     *
     * @param request    请求
     * @param delRoleDTO 删除角色dto
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping(value = "/delete")
    @Log( businessType= "删除", operatorType = OperatorType.ADMIN)
    @ApiOperation("根据管理员id和菜单id删除权限")
    public BaseResponse<Boolean> deleteRoleByRoleIdAndMenuId(@RequestBody AddAndDelRoleDTO delRoleDTO, HttpServletRequest request) {
        Integer roleId = delRoleDTO.getRoleId();
        List<Integer> menuIds = delRoleDTO.getMenuIds();
        // 检查管理员id是否为null，若为null则抛出业务异常
        if (menuIds == null || menuIds.isEmpty() || roleId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        for (Integer menuId : menuIds) {
            if (menuId == 12){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"权限配置的权限仅超级管理员可拥有，且不能删除");
            }
        }
        // 判断是否是超级管理员
        sysAdminService.noSuperAdmin(request);
        boolean res = sysRoleService.deleteRoleByRoleIdAndMenuId(menuIds, roleId);
        return ResultUtils.success(res, "删除成功");
    }

    /**
     * 通过admin id和menu id添加角色
     *
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @Log( businessType= "添加", operatorType = OperatorType.ADMIN)
    @PostMapping(value = "/add")
    @ApiOperation("根据管理员id和菜单id添加权限")
    public BaseResponse<Boolean> addRoleByRoleIdAndMenuId(@RequestBody AddAndDelRoleDTO addRoleDTO, HttpServletRequest request) {
        // 检查管理员id是否为null，若为null则抛出业务异常
        Integer roleId = addRoleDTO.getRoleId();
        List<Integer> menuIds = addRoleDTO.getMenuIds();
        if (menuIds == null || menuIds.isEmpty() || roleId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        for (Integer menuId : menuIds) {
            if (menuId == 12){
                throw new BusinessException(ErrorCode.PARAMS_ERROR,"权限配置的权限仅超级管理员可拥有，且不能删除");
            }
        }

        // 判断是否是超级管理员
        sysAdminService.noSuperAdmin(request);
        return ResultUtils.success(sysRoleService.addRoleByRoleIdAndMenuId(menuIds, roleId), "添加成功");
    }

    /**
     * 页面角色菜单
     * 获取分页列表
     *
     * @param sysRole 系统作用
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysRole}>>
     */
    @GetMapping("/pageRole")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<SysRole>> pageRole(SysRole sysRole) {
        PageResult<SysRole> sysAdminPageResult = sysRoleService.page(sysRole);
        return ResultUtils.success(sysAdminPageResult);
    }
}
