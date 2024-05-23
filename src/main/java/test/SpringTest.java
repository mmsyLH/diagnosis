package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author :罗汉
 * @date : 2024/4/29
 */
public class SpringTest {
    public static void main(String[] args) {
        //测试spring容器是否能使用 获取一个bean实例
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-ioc.xml");
        Object sysUserService = applicationContext.getBean("sysUserServiceImpl");
        System.out.println(sysUserService);
        //
        // short s1=1;
        // s1=s1+1;
        // s1+=1;
    }

}
