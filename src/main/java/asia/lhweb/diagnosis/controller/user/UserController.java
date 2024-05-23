package asia.lhweb.diagnosis.controller.user;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.constant.BaseConstant;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.LoginUserDTO;
import asia.lhweb.diagnosis.model.vo.LoginUserVO;
import asia.lhweb.diagnosis.service.SysUserService;
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
@Api("用户相关接口")
@RestController("userUserController")
@RequestMapping("/user/user/")
public class UserController {
    @Resource
    private SysUserService userService;

    @ApiOperation("用户退出登录")
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int res = userService.userLogout(request);
        return ResultUtils.success(res, BaseConstant.USER_LOGOUT_SUCCESS);
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody LoginUserDTO loginUserDTO, HttpServletRequest request) {
        if (loginUserDTO == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String userAccount = loginUserDTO.getUserAccount();
        String userPassword = loginUserDTO.getUserPassword();
        String code = loginUserDTO.getCode();
        if (!StringUtils.hasLength(userAccount)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能为空");
        }
        if (!StringUtils.hasLength(userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不能为空");
        }
        if (!StringUtils.hasLength(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码不能为空");
        }
        String codeStr = (String) request.getSession().getAttribute(BaseConstant.CODE_KEY);
        if (codeStr == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码已过期");
        }
        request.getSession().removeAttribute(BaseConstant.CODE_KEY);
        if (!codeStr.equals(code)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "验证码错误");
        }
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);


        return ResultUtils.success(loginUserVO, BaseConstant.USER_LOGIN_SUCCESS);
    }

    /**
     * 用户注册
     *
     * @param sysUser 系统用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public BaseResponse<Integer> userRegister(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int res = userService.userRegister(sysUser);
        return ResultUtils.success(res);
    }

    @ApiOperation("获取登录用户信息")
    @GetMapping("/getInfo")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LoginUserVO loginUserVO = userService.getLoginUserVO(request);
        return ResultUtils.success(loginUserVO);
    }

    @ApiOperation("更新用户头像")
    @GetMapping("/updateAvatarUrl")
    public BaseResponse<Boolean> updateAvatarUrl(Integer userId, String avatarUrl) {
        return ResultUtils.success(userService.updateAvatarUrl(userId, avatarUrl));
    }

    @ApiOperation("充值")
    @GetMapping("/addMoney")
    public BaseResponse<Boolean> addMoney(Integer userId, Double money, String outTradeNo) {
        return ResultUtils.success(userService.addMoney(userId, money, outTradeNo));
    }
}
