package asia.lhweb.diagnosis.controller.admin;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import asia.lhweb.diagnosis.service.SysMenuService;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * @author :罗汉
 * @date : 2024/4/2
 */
@RestController("adminMenuController")
@RequestMapping("/admin/menu")
@Api("菜单相关接口")
public class MenuController {
    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysAdminService sysAdminService;
    @Resource
    private RedisCacheUtil redisCacheUtil;

    /**
     * 根据管理员角色id查询菜单权限
     *
     * @param adminId 管理员id, 用于查询对应的管理员菜单权限
     * @return {@link BaseResponse<List<SysMenuLeftVO>>} 返回一个基础响应对象，包含菜单列表
     */
    @GetMapping(value = "/selectByAdminId")
    @ApiOperation("根据管理员角色id查询菜单权限")
    public BaseResponse< HashMap<String, List<SysMenuLeftVO>>> selectMenuTreeByAdminId(Integer adminId, HttpServletRequest request) {
        // 检查管理员id是否为null，若为null则抛出业务异常
        if (adminId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 判断是否是本人或者是管理员
        noMeOrNoSuperAdmin(adminId, request);

        // 1 查缓存
        // List<SysMenuLeftVO> cacheList = redisCacheUtil.getCacheList(String.format(AdminConstant.MENU_ADMIN_KEY, adminId));
        // if (cacheList != null && cacheList.size() > 0) {
        //     return ResultUtils.success(cacheList);
        // }
        // 2 没数据则走数据库 。根据管理员id查询菜单树形结构
        HashMap<String, List<SysMenuLeftVO>> hashMap = sysMenuService.selectMenuTreeByAdminId(adminId);
        // 返回成功响应，包含菜单列表
        return ResultUtils.success(hashMap);
    }

    /**
     * 按角色id选择菜单树
     * 根据管理员角色id查询菜单权限
     *
     * @param roleId  角色id
     * @param request 请求
     * @return {@link BaseResponse}< {@link HashMap}<{@link String}, {@link List}<{@link SysMenuLeftVO}>>>
     */
    @GetMapping(value = "/selectByRoleId")
    @ApiOperation("根据管理员权限id查询菜单权限")
    public BaseResponse< HashMap<String, List<SysMenuLeftVO>>> selectMenuTreeByRoleId(Integer roleId, HttpServletRequest request) {
        // 检查管理员id是否为null，若为null则抛出业务异常
        if (roleId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 判断是否是超级管理员
        noSuperAdmin(request);

        // 1 查缓存
        // List<SysMenuLeftVO> cacheList = redisCacheUtil.getCacheList(String.format(AdminConstant.MENU_ADMIN_KEY, adminId));
        // if (cacheList != null && cacheList.size() > 0) {
        //     return ResultUtils.success(cacheList);
        // }
        // 2 没数据则走数据库 。根据管理员id查询菜单树形结构
        HashMap<String, List<SysMenuLeftVO>> hashMap = sysMenuService.selectMenuTreeByRoleId(roleId);
        // 返回成功响应，包含菜单列表
        return ResultUtils.success(hashMap);
    }
    /**
     * 根据管理员角色id查询菜单权限
     *
     * @param adminId 管理员id, 用于查询对应的管理员菜单权限
     * @return {@link BaseResponse<List<SysMenuLeftVO>>} 返回一个基础响应对象，包含菜单列表
     */
    @GetMapping(value = "/selectFalseTrueByAdminId")
    @ApiOperation("根据管理员角色id查询菜单权限")
    @Deprecated
    public BaseResponse<List<SysMenuLeftVO>> selectFalseTrueByAdminId(Integer adminId, HttpServletRequest request) {
        // 检查管理员id是否为null，若为null则抛出业务异常
        if (adminId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 判断是否是本人或者是管理员
        noMeOrNoSuperAdmin(adminId, request);
        // 1 查缓存
        // List<SysMenuLeftVO> cacheList = redisCacheUtil.getCacheList(String.format(AdminConstant.MENU_ADMIN_KEY, adminId));
        // if (cacheList != null && cacheList.size() > 0) {
        //     return ResultUtils.success(cacheList);
        // }
        // 2 没数据则走数据库 。根据管理员id查询菜单树形结构
        List<SysMenuLeftVO> sysMenuList = sysMenuService.selectFalseTrueByAdminId(adminId);
        // 返回成功响应，包含菜单列表
        return ResultUtils.success(sysMenuList);
    }

    /**
     * 更新菜单状态
     *
     * @param sysMenuLeftVO 系统菜单左键
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */// update
    @PostMapping(value = "/updateStatus")
    @ApiOperation("更新菜单")
    public BaseResponse<Boolean> updateStatus(@RequestBody SysMenuLeftVO sysMenuLeftVO, HttpServletRequest request) {
        // // 通过HttpServletRequest获取当前登录的管理员信息
        // LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // // 判断当前请求的用户是否为超级管理员，且如果不是当前查询的管理员，则抛出无权限异常
        // if (!sysAdminService.isAdmin(request)) {
        //     throw new BusinessException(ErrorCode.NO_AUTH);
        // }
        // 调用service层方法更新菜单
        return ResultUtils.success(sysMenuService.updateStatus(sysMenuLeftVO));
    }
    /**
     * 更新菜单
     *
     * @param sysMenuLeftVO 系统菜单左键
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */// update
    @PostMapping(value = "/update")
    @ApiOperation("更新菜单")
    public BaseResponse<Boolean> update(@RequestBody SysMenuLeftVO sysMenuLeftVO, HttpServletRequest request) {
        // // 通过HttpServletRequest获取当前登录的管理员信息
        // LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // // 判断当前请求的用户是否为超级管理员，且如果不是当前查询的管理员，则抛出无权限异常
        // if (!sysAdminService.isAdmin(request)) {
        //     throw new BusinessException(ErrorCode.NO_AUTH);
        // }
        // 调用service层方法更新菜单
        return ResultUtils.success(sysMenuService.update(sysMenuLeftVO));
    }

    /**
     * 添加菜单
     *
     * @param sysMenuLeftVO 系统菜单左键
     * @param request       请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @PostMapping(value = "/add")
    @ApiOperation("添加菜单")
    public BaseResponse<Boolean> add(@RequestBody SysMenuLeftVO sysMenuLeftVO, HttpServletRequest request) {
        // 通过HttpServletRequest获取当前登录的管理员信息
        // LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // // 判断当前请求的用户是否为超级管理员，且如果不是当前查询的管理员，则抛出无权限异常
        // if (!sysAdminService.isAdmin(request)) {
        //     throw new BusinessException(ErrorCode.NO_AUTH);
        // }
        // 调用service层方法添加菜单
        return ResultUtils.success(sysMenuService.add(sysMenuLeftVO));
    }

    /**
     * 按id删除
     *
     * @param id      id
     * @param request 请求
     * @return {@link BaseResponse}<{@link Boolean}>
     */
    @GetMapping(value = "/delete")
    @ApiOperation("删除菜单")
    public BaseResponse<Boolean> deleteById(@RequestParam("id") Integer id, HttpServletRequest request) {
        // 通过HttpServletRequest获取当前登录的管理员信息
        // LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // // 判断当前请求的用户是否为超级管理员，且如果不是当前查询的管理员，则抛出无权限异常
        // if (!sysAdminService.isAdmin(request)) {
        //     throw new BusinessException(ErrorCode.NO_AUTH);
        // }
        //调用service层方法删除菜单
        return ResultUtils.success(sysMenuService.deleteMenuById(id));
    }



    /**
     * 判断是否是本人或者是管理员
     *
     * @param adminId 管理员id
     * @param request 请求
     */
    private void noMeOrNoSuperAdmin(Integer adminId, HttpServletRequest request) {
        // 通过HttpServletRequest获取当前登录的管理员信息
        LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // 判断当前请求的用户是否为超级管理员，且如果不是当前查询的管理员，则抛出无权限异常
        if (!sysAdminService.isAdmin(request) && !adminId.equals(loginAdminVO.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
    }

    /**
     * 判断是否是超级管理员
     *
     * @param request 请求
     */
    private void noSuperAdmin( HttpServletRequest request) {
        // 通过HttpServletRequest获取当前登录的管理员信息
        LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        // 判断当前请求的用户是否为超级管理员，不是则抛出无权限异常
        if (!Objects.equals(loginAdminVO.getAdminRoleId(), BaseConstant.SUPER_ADMIN_ID)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"仅有超级管理员才有权限");
        }
    }
    


}
