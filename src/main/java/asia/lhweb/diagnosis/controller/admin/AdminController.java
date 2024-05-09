package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysAdmin;
import asia.lhweb.diagnosis.model.dto.LoginAdminDTO;
import asia.lhweb.diagnosis.model.dto.SysAdminDTO;
import asia.lhweb.diagnosis.model.vo.LoginAdminVO;
import asia.lhweb.diagnosis.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    // @ApiOperation("用户注册")
    // @PostMapping("/register")
    // public BaseResponse<Long> UserRegister(@RequestBody RegisterUserDTO userRegisterRequest) {
    //     if (userRegisterRequest == null) {
    //         throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //     }
    //     // String userAccount = userRegisterRequest.getUserAccount();
    //     // String userPassword = userRegisterRequest.getUserPassword();
    //     //
    //     // // 是否为空
    //     // if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, plantCode)) {
    //     //     throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
    //     // }
    //     // long res = userService.userRegister(userAccount, userPassword, checkPassword, plantCode);
    //     // return ResultUtils.success(res);
    // }

    // @ApiOperation("用户退出登录")
    // @PostMapping("/logout")
    // public BaseResponse<Integer> userLogout(HttpServletRequest request) {
    //     if (request == null){
    //         throw new BusinessException(ErrorCode.PARAMS_ERROR);
    //     }
    //     int res = sysAdminService.userLogout(request);
    //     return ResultUtils.success(res, BaseConstant.USER_LOGOUT_SUCCESS);
    // }

    @ApiOperation("用户登录")
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
     * 获取个人信息
     *
     * @param request 请求
     * @return {@link BaseResponse}<{@link LoginAdminVO}>
     */
    public BaseResponse<LoginAdminVO> getInfo(HttpServletRequest request) {
        LoginAdminVO loginAdminVO = sysAdminService.getLoginAdminVO(request);
        if (loginAdminVO == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户未登录");
        }
        return ResultUtils.success(loginAdminVO);
    }


}
