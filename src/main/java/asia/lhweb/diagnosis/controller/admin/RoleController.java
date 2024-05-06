package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.service.SysRoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author :罗汉
 * @date : 2024/4/26
 */
@RestController
@RequestMapping("/admin/role")
public class RoleController {
    @Resource
    private SysRoleService sysRoleService;
    // @RequestMapping(value = "/selectByAdminRoleId")
    // public Result<CyRole> selectByAdminRoleId(Integer adminRoleId) {
    //     return cyRoleService.selectByAdminRoleId(adminRoleId);
    // }
    // @GetMapping(value = "/page")
    // public Result<PageResult<CyRole>> getAllRole(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize")int pageSize) {
    //     return sysRoleService.getAllRole(pageNo,pageSize);
    // }
}
