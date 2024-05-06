package asia.lhweb.diagnosis.service.impl;


import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysMenuMapper;
import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;
import asia.lhweb.diagnosis.service.SysMenuService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_menu(系统菜单表)】的数据库操作Service实现
* @createDate 2024-04-28 20:11:50
*/
@Service
public class SysMenuServiceImpl
implements SysMenuService{
    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * 按admin id选择菜单树
     *
     * @param adminId 管理员id
     * @return {@link List}<{@link SysMenuLeftVO}>
     */
    @Override
    public List<SysMenuLeftVO> selectMenuTreeByAdminId(Integer adminId) {
        // 根据管理员id查询菜单树
        List<SysMenuLeftVO> menuTree = sysMenuMapper.selectMenuTreeByAdminId(adminId);
        // 如果查询结果为空，表示该管理员没有权限，返回错误信息
        if (CollectionUtils.isEmpty(menuTree)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // 获取管理员的所有权限菜单（包括子权限）
        List<SysMenuLeftVO> sysMenuLeftVOList = getChildPerms(menuTree, 0);
        if (CollectionUtils.isEmpty(sysMenuLeftVOList)){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return sysMenuLeftVOList;
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
