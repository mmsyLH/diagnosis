package asia.lhweb.diagnosis.controller.admin;


import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.vo.SysMenuLeftVO;
import asia.lhweb.diagnosis.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author :罗汉
 * @date : 2024/4/2
 */
@RestController("adminMenuController")
@RequestMapping("/admin/menu")
public class MenuController {
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 根据管理员角色id查询菜单权限
     *
     * @param adminId 管理员id
     * @return {@link BaseResponse}
     */
    @GetMapping(value = "/selectByAdminId")
    public BaseResponse<List<SysMenuLeftVO>> selectMenuTreeByAdminId(Integer adminId) {
        if (adminId == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        List<SysMenuLeftVO> sysMenuList = sysMenuService.selectMenuTreeByAdminId(adminId);
        return ResultUtils.success(sysMenuList);
    }
}
