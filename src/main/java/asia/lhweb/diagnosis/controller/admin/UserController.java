package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.LoginUserDTO;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import asia.lhweb.diagnosis.model.vo.LoginUserVO;
import asia.lhweb.diagnosis.service.SysUserService;
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
@ApiOperation("用户相关接口")
@RestController
@RequestMapping("/admin/user/")
public class UserController {
    @Resource
    private SysUserService userService;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;
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

    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int res = userService.userLogout(request);
        return ResultUtils.success(res, BaseConstant.USER_LOGOUT_SUCCESS);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<LoginUserVO>  userLogin(@RequestBody LoginUserDTO loginUserDTO, HttpServletRequest  request) {
        if (loginUserDTO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = loginUserDTO.getUserAccount();
        String userPassword = loginUserDTO.getUserPassword();
        String code = loginUserDTO.getCode();
        if (!StringUtils.hasLength(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户不能为空");
        }
        if (!StringUtils.hasLength(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码不能为空");
        }
        if (!StringUtils.hasLength(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码不能为空");
        }
        String codeStr = (String) request.getSession().getAttribute(BaseConstant.CODE_KEY);
        if (!codeStr.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"验证码错误");
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO, BaseConstant.USER_LOGIN_SUCCESS);
    }

    /**
     * 获取分页列表
     *
     * @param sysUserDTO 系统用户dto
     * @return {@link BaseResponse}<{@link PageResult}<{@link SysUser}>>
     */
    @GetMapping("/page")
    @ApiOperation("获取分页列表")
    public BaseResponse<PageResult<SysUser>> page(SysUserDTO sysUserDTO) {
        PageResult<SysUser> sysAdminPageResult = userService.page(sysUserDTO);
        return ResultUtils.success(sysAdminPageResult);
    }

    @GetMapping("/delete")
    @ApiOperation("删除用户")
    public BaseResponse<Boolean> deleteById(@RequestParam("id") Integer id) {
        boolean res = userService.deleteById(id);
        return ResultUtils.success(res);
    }
}
