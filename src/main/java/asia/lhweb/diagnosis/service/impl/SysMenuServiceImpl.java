package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysMenuMapper;
import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;
import asia.lhweb.diagnosis.service.SysMenuService;
import asia.lhweb.diagnosis.utils.cache.RedisCacheUtil;
import asia.lhweb.diagnosis.utils.data.MenuTreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【sys_menu(系统菜单表)】的数据库操作Service实现
 * @createDate 2024-04-28 20:11:50
 */
@Service
public class SysMenuServiceImpl
        implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private RedisCacheUtil redisCacheUtil;

    /**
     * 按admin id选择菜单树
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link SysMenuLeftVO}>
     */
    @Override
    public HashMap<String, List<SysMenuLeftVO>> selectMenuTreeByAdminId(Integer adminId) {
        // 根据管理员id查询自己的菜单树
        List<SysMenuLeftVO> myMenuTree = sysMenuMapper.selectMenuTreeByAdminId(adminId);
        // 如果查询结果为空，表示该管理员没有权限，返回错误信息
        if (CollectionUtils.isEmpty(myMenuTree)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 方式1 获取管理员的所有权限菜单（包括子权限） 时间复杂度O(n^2)
        // List<SysMenuLeftVO> sysMenuLeftVOList = getChildPerms(myMenuTree, 0);
        // if (CollectionUtils.isEmpty(sysMenuLeftVOList)) {
        //     throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        // }
        // String redisKey = String.format(AdminConstant.MENU_ADMIN_KEY, adminId);
        // // 存入redis中
        // redisCacheUtil.setCacheList(redisKey, sysMenuLeftVOList);
        // // 设置过期时间为一小时（假设设置为一小时）
        // redisCacheUtil.expire(redisKey, 1, TimeUnit.HOURS);

        // 方式2 获取管理员的所有权限菜单（包括子权限） 时间复杂度O(n)
        // 1 再查询全部的树 上面已经查了自己有了权限的菜单树
        List<SysMenuLeftVO> allMenuTree = sysMenuMapper.selectAllMenuTree();

        // 2 去查询菜单树
        List<SysMenuLeftVO> trueTree=MenuTreeUtil.getResultMenuTree(myMenuTree, allMenuTree,true);
        List<SysMenuLeftVO> falseMenuTree = MenuTreeUtil.getResultMenuTree(myMenuTree, allMenuTree, false);
        HashMap<String, List<SysMenuLeftVO>> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("true",trueTree);
        objectObjectHashMap.put("false",falseMenuTree);

        return objectObjectHashMap;
    }



    @Override
    public List<SysMenuLeftVO> selectFalseTrueByAdminId(Integer adminId) {
        // 根据管理员id查询自己的菜单树
        List<SysMenuLeftVO> myMenuTree = sysMenuMapper.selectMenuTreeByAdminId(adminId);
        List<SysMenuLeftVO> allMenuTree = sysMenuMapper.selectAllMenuTree();
        // 如果查询结果为空，表示该管理员没有权限，返回错误信息
        if (CollectionUtils.isEmpty(myMenuTree)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        List<SysMenuLeftVO> resultMenuTree=MenuTreeUtil.getResultMenuTree(myMenuTree, allMenuTree,false);
        // 4 封装返回的map
        return resultMenuTree;
    }

    @Override
    public HashMap<String, List<SysMenuLeftVO>> selectMenuTreeByRoleId(Integer roleId) {
        // 根据管理员id查询自己的菜单树
        List<SysMenuLeftVO> myMenuTree = sysMenuMapper.selectMenuTreeByRoleId(roleId);
        // 如果查询结果为空，表示该管理员没有权限，返回错误信息
        if (CollectionUtils.isEmpty(myMenuTree)) {
            if (roleId==3){
                throw new BusinessException(ErrorCode.NULL_ERROR,"超级管理员没有可分配的菜单了 。请联系管理员！！");
            }

            myMenuTree=new ArrayList<>();
        }

        // 方式1 获取管理员的所有权限菜单（包括子权限） 时间复杂度O(n^2)
        // List<SysMenuLeftVO> sysMenuLeftVOList = getChildPerms(myMenuTree, 0);
        // if (CollectionUtils.isEmpty(sysMenuLeftVOList)) {
        //     throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        // }
        // String redisKey = String.format(AdminConstant.MENU_ADMIN_KEY, adminId);
        // // 存入redis中
        // redisCacheUtil.setCacheList(redisKey, sysMenuLeftVOList);
        // // 设置过期时间为一小时（假设设置为一小时）
        // redisCacheUtil.expire(redisKey, 1, TimeUnit.HOURS);

        // 方式2 获取管理员的所有权限菜单（包括子权限） 时间复杂度O(n)
        // 1 再查询全部的树 上面已经查了自己有了权限的菜单树
        List<SysMenuLeftVO> allMenuTree = sysMenuMapper.selectAllMenuTree();

        // 2 去查询菜单树
        List<SysMenuLeftVO> trueTree=MenuTreeUtil.getResultMenuTree(myMenuTree, allMenuTree,true);
        List<SysMenuLeftVO> falseMenuTree = MenuTreeUtil.getResultMenuTree(myMenuTree, allMenuTree, false);
        HashMap<String, List<SysMenuLeftVO>> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("true",trueTree);
        objectObjectHashMap.put("false",falseMenuTree);

        return objectObjectHashMap;
    }


    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuLeftVO> getChildPerms(List<SysMenuLeftVO> list, int parentId) {
        List<SysMenuLeftVO> returnList = new ArrayList<SysMenuLeftVO>();
        for (Iterator<SysMenuLeftVO> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenuLeftVO t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getMenuParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param t    子节点
     */
    private void recursionFn(List<SysMenuLeftVO> list, SysMenuLeftVO t) {
        // 得到子节点列表
        List<SysMenuLeftVO> childList = getChildList(list, t);
        t.setSysMenuLeftVOList(childList);
        for (SysMenuLeftVO tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenuLeftVO> getChildList(List<SysMenuLeftVO> list, SysMenuLeftVO t) {
        List<SysMenuLeftVO> tlist = new ArrayList<SysMenuLeftVO>();
        Iterator<SysMenuLeftVO> it = list.iterator();
        while (it.hasNext()) {
            SysMenuLeftVO n = (SysMenuLeftVO) it.next();
            if (n.getMenuParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuLeftVO> list, SysMenuLeftVO t) {
        return getChildList(list, t).size() > 0;
    }

}
