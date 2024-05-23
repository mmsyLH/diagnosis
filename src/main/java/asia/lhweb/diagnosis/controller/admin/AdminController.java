package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.enums.OperatorType;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.domain.SysAdminUser;
import asia.lhweb.diagnosis.model.dto.LoginAdminDTO;
import asia.lhweb.diagnosis.model.dto.SysAdminDTO;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author :罗汉
 * @date : 2024/4/28
 * ("/user/user/")
 */
@Api("管理员相关接口")
@ApiOperation("管理员相关接口")
@RestController
@RequestMapping("/admin/admin")
public class AdminController {
    @Resource
    private SysAdminService sysAdminService;

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public BaseResponse<LoginAdminVO> adminLogin(@RequestBody LoginAdminDTO loginAdminDTO, HttpServletRequest request) {
        if (loginAdminDTO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String sysAccount = loginAdminDTO.getSysAccount();
        String sysPassword = loginAdminDTO.getSysPassword();
        String code = loginAdminDTO.getCode();
        if (!StringUtils.hasLength(sysAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能为空");
        }
        if (!StringUtils.hasLength(sysPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
        }
        String codeStr = (String) request.getSession().getAttribute(BaseConstant.CODE_KEY);
        if (!StringUtils.hasLength(code) || !StringUtils.hasLength(codeStr)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码不能为空，请重新获取验证码");
        }
        request.getSession().removeAttribute(BaseConstant.CODE_KEY);
        if (!codeStr.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        LoginAdminVO loginAdminVO = sysAdminService.adminLogin(sysAccount, sysPassword, request);
        return ResultUtils.success(loginAdminVO, BaseConstant.ADMIN_LOGIN_SUCCESS);
    }

    /**
     * 获取分页列表
     *
     * @param sysAdminDTO 系统管理员
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysAdmin}>>
     */
    @GetMapping("/page")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<SysAdmin>> page(SysAdminDTO sysAdminDTO) {
        PageResult<SysAdmin> sysAdminPageResult = sysAdminService.page(sysAdminDTO);
        return ResultUtils.success(sysAdminPageResult);
    }


    /**
     * 管理userpage
     * 获取分页列表
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysAdminUser}>>
     */
    @GetMapping("/adminUserPage")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<SysAdminUser>> adminUserPage(SysAdminUser sysAdminUser) {
        PageResult<SysAdminUser> sysAdminPageResult = sysAdminService.adminUserPage(sysAdminUser);
        return ResultUtils.success(sysAdminPageResult);
    }
    /**
     * 添加管理员
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/addAdmin")
    @ApiOperation("添加管理员")
    @Log( businessType= "添加", operatorType = OperatorType.ADMIN)
    public BaseResponse<Integer> addAdmin(@RequestBody SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = sysAdminService.addAdmin(sysAdminUser);
        return ResultUtils.success(result, "添加管理员成功");
    }

    /**
     * 添加管理员和咨询师
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/addAdminAndCounselor")
    @ApiOperation("添加咨询师")
    @Log( businessType= "添加", operatorType = OperatorType.ADMIN)
    public BaseResponse<Integer> addAdminAndCounselor(@RequestBody SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = sysAdminService.addAdminAndCounselor(sysAdminUser);
        return ResultUtils.success(result, "添加管理员和咨询师成功");
    }

    /**
     * 更新状态
     *
     * @param sysAdminUser 系统管理员用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @PostMapping("/updateStatus")
    @ApiOperation("修改状态")
    @Log( businessType= "修改", operatorType = OperatorType.ADMIN)
    public BaseResponse<Integer> updateStatus(@RequestBody SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = sysAdminService.updateStatus(sysAdminUser);
        return ResultUtils.success(result, "修改状态成功");
    }

    /**
     * 获取个人信息
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link LoginAdminVO}>
     */
    @GetMapping("/getInfo")
    @ApiOperation("获取个人信息")
    public BaseResponse<LoginAdminVO> getInfo(HttpServletRequest request) {
        LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        if (loginAdminVO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户未登录");
        }
        return ResultUtils.success(loginAdminVO);
    }
    //重置密码
    @PostMapping("/resetPassword")
    @ApiOperation("重置密码")
    @Log( businessType= "重置密码", operatorType = OperatorType.ADMIN)
    public BaseResponse<Integer> resetPassword(@RequestBody SysAdminUser sysAdminUser) {
        if (sysAdminUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = sysAdminService.resetPassword(sysAdminUser);
        return ResultUtils.success(result, "重置密码成功");
    }

    @GetMapping("/delete")
    @ApiOperation("根据id删除")
    @Log( businessType= "删除", operatorType = OperatorType.ADMIN)
    public BaseResponse<Integer> delete(@RequestParam("id") Integer id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int result = sysAdminService.delete(id);
        return ResultUtils.success(result, "删除成功");
    }
}
