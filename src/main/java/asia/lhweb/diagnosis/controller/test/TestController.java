package asia.lhweb.diagnosis.controller.test;

import asia.lhweb.diagnosis.common.BaseResponse;
import asia.lhweb.diagnosis.common.enums.ErrorCode;
import asia.lhweb.diagnosis.common.ResultUtils;
import asia.lhweb.diagnosis.exception.BusinessException;
import asia.lhweb.diagnosis.mapper.SysUserMapper;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.model.dto.SysUserDTO;
import asia.lhweb.diagnosis.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :罗汉1
 * @date : 2024/4/29
 */
@RestController
@Controller
@RequestMapping("/text")
@Api(tags = "接口文档测试")
public class TestController {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "index";
    }

    /**
     * 测试逻辑删除改成更新
     *
     * @param id id
     * @return {@link String}
     */
    @ApiOperation("测试接口")
    @PostMapping("/mapperTest")
    public String test(@ApiParam("测试接口测试的id") Integer id){
        Long id1= Long.valueOf(id);
        int i=sysUserMapper.deleteByPrimaryKey(id1);
        System.out.println("test");
        return "index";
    }

    /**
     * 测试查询拼接逻辑删除字段条件
     * @return {@link String}
     */
    @PostMapping("/mapperTes2")
    public String test2(){
        List<SysUser> sysUserList=sysUserMapper.selectList();
        System.out.println(sysUserList);
        return "index";
    }

    /**
     * 测试更新拼接逻辑删除字段
     *
     * @return {@link String}
     */
    @PostMapping("/mapperTes3")
    public String test3(SysUserDTO sysUserDTO){
        // int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        // System.err.println("更新结果："+i);
        return "index";
    }

    /**
     * 测试全局异常
     *
     * @return {@link String}
     */
    @ApiOperation("测试全局异常")
    @PostMapping("/tes4")
    public BaseResponse test4(SysUserDTO sysUserDTO){
        int i=2;
        if (i==2){
            throw new BusinessException(ErrorCode.SUCCESS,"测试全局异常ok");
        }
        return ResultUtils.success("测试全局异常");
    }
}
