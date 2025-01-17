package asia.lhweb.diagnosis.controller.admin;

import asia.lhweb.diagnosis.annotation.Log;
import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.enums.OperatorType;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.model.PageResult;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import asia.lhweb.diagnosis.model.vo.SysUserStatisticsVO;
import asia.lhweb.diagnosis.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDate;

/**
 * @author :罗汉
 * @date : 2024/4/28
 * ("/user/user/")
 */
@Api("管理员中用户相关接口")
@RestController
@RequestMapping("/admin/user/")
public class UserController {
    @Resource
    private SysUserService userService;

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
    @Log( businessType= "删除", operatorType = OperatorType.ADMIN)
    public BaseResponse<Boolean> deleteById(@RequestParam("id") Integer id) {
        boolean res = userService.deleteById(id);
        return ResultUtils.success(res);
    }

    /**
     * 获得用户统计报告
     * 通过注解描述日期格式
     *
     * @param begin 开始
     * @param end   结束
     * @return {@link BaseResponse}<{@link SysUserStatisticsVO}>
     */
    @GetMapping("/userStatistics")
    @ApiOperation("用户统计")
    public BaseResponse<SysUserStatisticsVO> getUserStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
        SysUserStatisticsVO sysUserStatisticsVO = userService.getUserStatistics(begin, end);
        return ResultUtils.success(sysUserStatisticsVO);
    }

    /**
     * 更新用户
     *
     * @param sysUser 系统用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    @Log( businessType= "更新", operatorType = OperatorType.ADMIN)
    public BaseResponse<Boolean> updateUser(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int res = userService.updateUser(sysUser);
        if (res <= 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success(true);
    }
    /**
     * 用户添加
     *
     * @param sysUser 系统用户
     * @return {@link BaseResponse}<{@link Integer}>
     */
    @ApiOperation("用户添加")
    @PostMapping("/add")
    public BaseResponse<Integer> userAdd(@RequestBody SysUser sysUser) {
        if (sysUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        int res = userService.userRegister(sysUser);
        return ResultUtils.success(res);
    }
}
