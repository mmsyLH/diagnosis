package asia.lhweb.diagnosis.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单表
 * @TableName sys_menu
 */
@Data
public class SysMenuLeftVO implements Serializable {
    private Integer id;
    /**
     * 系统菜单名
     */
    private String menuName;

    /**
     * 系统菜单组件
     */
    private String menuComponent;

    /**
     * 系统菜单路径
     */
    private String menuPath;

    /**
     * 菜单图标
     */
    private String menuIcon;

    /**
     * 菜单是否启用1启用0禁用
     */
    private Integer menuStatus;

    /**
     * 所属父级菜单id
     */
    private Integer menuParentId;

    /**
     * 菜单是否隐藏1隐藏0未隐藏
     */
    private Integer menuIsHidden;

    /**
     * 菜单排序-数字越大排在越上面
     */
    private Integer menuSort;

    /**
     * 菜单创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 是否添加
     */
    private boolean isAdd;

    public boolean getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean add) {
        isAdd = add;
    }

    private List<SysMenuLeftVO> sysMenuLeftVOList;
    
    private static final long serialVersionUID = 1L;


}