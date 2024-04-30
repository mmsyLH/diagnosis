package asia.lhweb.diagnosis.controller;

import asia.lhweb.diagnosis.mapper.SysUserMapper;
import asia.lhweb.diagnosis.model.domain.SysUser;
import asia.lhweb.diagnosis.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author :罗汉
 * @date : 2024/4/29
 */
@RestController
@Controller
@RequestMapping("/text")
public class TestController {
    @Autowired
    private SysUserService sysUserService;

    @Resource
    private SysUserMapper sysUserMapper;

    @RequestMapping("/hello")
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
    @RequestMapping("/mapperTest")
    public String test(Integer id){
        Long id1= Long.valueOf(id);
        int i=sysUserMapper.deleteByPrimaryKey(id1);
        System.out.println("test");
        return "index";
    }

    /**
     * 测试查询拼接逻辑删除字段条件
     * @return {@link String}
     */
    @RequestMapping("/mapperTes2")
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
    @RequestMapping("/mapperTes3")
    public String test3(SysUser sysUser){
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        System.err.println("更新结果："+i);
        return "index";
    }
}
