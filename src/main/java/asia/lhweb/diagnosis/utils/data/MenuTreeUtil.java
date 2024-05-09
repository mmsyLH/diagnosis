package asia.lhweb.diagnosis.utils.data;

import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;

import java.util.*;

/**
 * 构建树工具类
 * @author :罗汉
 * @date :2024/5/7
 */
public class MenuTreeUtil {

    // 主函数，用于测试
    public static void main(String[] args) {
        // 示例代码，演示如何使用深拷贝函数
    }

    /**
     * 将当前用户拥有的菜单项标记为已添加状态。
     * 遍历当前用户菜单树（myMenuTree）和所有菜单树（allMenuTree），
     * 如果某个菜单项在两个树中都存在，则将其在allMenuTree中的对应项标记为已添加（isAdd为true）。
     *
     * @param myMenuTree 当前用户拥有的菜单树列表，每个菜单项包含菜单的详细信息，如ID等。
     * @param allMenuTree 所有可用的菜单树列表，用于比对和标记已添加的菜单项。
     */
    public static void markUserMenusAsAdded(List<SysMenuLeftVO> myMenuTree, List<SysMenuLeftVO> allMenuTree) {
        // 遍历当前用户菜单树，为在allMenuTree中找到的对应菜单项设置已添加标记
        for (SysMenuLeftVO item : myMenuTree) {
            // 在所有菜单树中查找与当前菜单项相匹配的项，并进行标记
            for (SysMenuLeftVO item2 : allMenuTree) {
                if (Objects.equals(item.getId(), item2.getId())) { // 当ID匹配时，标记为已添加
                    item2.setIsAdd(true);
                }
            }
        }
    }

    /**
     * 深拷贝菜单树列表
     * @param allMenuTree 原始菜单树列表
     * @return 深拷贝后的菜单树列表
     */
    public static List<SysMenuLeftVO> deepCopy(List<SysMenuLeftVO> allMenuTree) {
        // 创建一个新的菜单树列表，用于存放深拷贝后的对象
        List<SysMenuLeftVO> allMenuTreeCopy = new ArrayList<>();
        for (SysMenuLeftVO sysMenuLeftVO : allMenuTree) {
            // 将复制后的元素添加到新列表
            allMenuTreeCopy.add(copyMenu(sysMenuLeftVO));
        }
        return allMenuTreeCopy;
    }

    /**
     * 根据菜单树创建映射表，并筛选出实际需要的菜单
     * @param menuTreeCopy 菜单树的深拷贝列表
     * @param isAdd 标记是否已添加
     * @return 筛选后的菜单树列表
     */
    public static List<SysMenuLeftVO> createTreeMap(List<SysMenuLeftVO> menuTreeCopy,boolean isAdd) {
        // 创建一个映射表，用于快速查找菜单
        Map<Integer, SysMenuLeftVO> menuTreeMap = new HashMap<>();
        for (SysMenuLeftVO menu : menuTreeCopy) {
            menuTreeMap.put(menu.getId(), menu);
        }

        // 筛选出需要添加到菜单树的菜单项
        List<Integer> trueIds = new ArrayList<>();
        for (SysMenuLeftVO menu : menuTreeCopy) {
            if (menu.getIsAdd()==isAdd) {
                trueIds.add(menu.getId());
                SysMenuLeftVO pNode = menu;
                // 查找父节点并将其加入筛选列表
                while (pNode.getMenuParentId() != 0 && !trueIds.contains(pNode.getMenuParentId())) {
                    pNode = menuTreeMap.get(pNode.getMenuParentId());
                    trueIds.add(pNode.getId());
                }
            }
        }

        // 创建一个新的映射表，仅包含需要的菜单
        Map<Integer, SysMenuLeftVO> trueMenuTreeMap = new HashMap<>();
        for (Integer id : trueIds) {
            trueMenuTreeMap.put(id, menuTreeMap.get(id));
        }
        return createMenuList(trueMenuTreeMap);
    }

    /**
     * 根据映射表创建菜单树列表
     * @param menuTreeMap 菜单的映射表
     * @return 创建好的菜单树列表
     */
    public static List<SysMenuLeftVO> createMenuList(Map<Integer, SysMenuLeftVO> menuTreeMap) {
        // 创建根节点
        SysMenuLeftVO heard = new SysMenuLeftVO();
        heard.setId(0);
        List<SysMenuLeftVO> resultList = new ArrayList<>();
        heard.setSysMenuLeftVOList(resultList);

        // 将根节点直接添加到结果列表，将其他节点添加到它们的父节点下
        for (SysMenuLeftVO menu : menuTreeMap.values()) {
            if (menu.getMenuParentId() == 0) {
                resultList.add(menu);
            } else {
                menuTreeMap.get(menu.getMenuParentId()).getSysMenuLeftVOList().add(menu);
            }
        }
        return resultList;
    }

    /**
     * 复制菜单对象
     * @param sysMenuLeftVO 要复制的菜单对象
     * @return 复制后的菜单对象
     */
    public static SysMenuLeftVO copyMenu(SysMenuLeftVO sysMenuLeftVO) {
        SysMenuLeftVO sysMenuLeftVOCopy = new SysMenuLeftVO();
        // 复制字段
        sysMenuLeftVOCopy.setId(sysMenuLeftVO.getId());
        sysMenuLeftVOCopy.setMenuName(sysMenuLeftVO.getMenuName());
        sysMenuLeftVOCopy.setMenuPath(sysMenuLeftVO.getMenuPath());
        sysMenuLeftVOCopy.setMenuComponent(sysMenuLeftVO.getMenuComponent());
        sysMenuLeftVOCopy.setMenuIcon(sysMenuLeftVO.getMenuIcon());
        sysMenuLeftVOCopy.setMenuSort(sysMenuLeftVO.getMenuSort());
        sysMenuLeftVOCopy.setMenuStatus(sysMenuLeftVO.getMenuStatus());
        sysMenuLeftVOCopy.setMenuIsHidden(sysMenuLeftVO.getMenuIsHidden());
        sysMenuLeftVOCopy.setCreateTime(sysMenuLeftVO.getCreateTime());
        sysMenuLeftVOCopy.setUpdateTime(sysMenuLeftVO.getUpdateTime());
        sysMenuLeftVOCopy.setMenuParentId(sysMenuLeftVO.getMenuParentId());

        // 创建空列表以避免空指针异常
        ArrayList<SysMenuLeftVO> objects = new ArrayList<>();
        sysMenuLeftVOCopy.setSysMenuLeftVOList(objects);
        return sysMenuLeftVOCopy;
    }

    /**
     * 获得标记菜单树
     *
     * @param myMenuTree  我菜单树
     * @param allMenuTree 所有菜单树
     * @return {@link List}<{@link SysMenuLeftVO}>
     */
    public static List<SysMenuLeftVO> getMarkedMenuTree(List<SysMenuLeftVO> myMenuTree, List<SysMenuLeftVO> allMenuTree) {
        List<SysMenuLeftVO> tempList = deepCopy(allMenuTree);
        markUserMenusAsAdded(myMenuTree, tempList);
        return tempList;
    }

    /**
     * 根据权限标记获取最终菜单树
     *
     * @param myMenuTree 当前用户菜单树
     * @param allMenuTree 所有菜单树
     * @param isAdd 菜单的添加状态标记
     * @return 返回过滤后的菜单树列表
     */
    public static List<SysMenuLeftVO> getResultMenuTree(List<SysMenuLeftVO> myMenuTree, List<SysMenuLeftVO> allMenuTree, boolean isAdd) {
        return createTreeMap(getMarkedMenuTree(myMenuTree, allMenuTree),isAdd);
    }
}
