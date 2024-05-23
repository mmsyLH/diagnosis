package asia.lhweb.diagnosis.config;

import asia.lhweb.diagnosis.aspet.SysLogAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author :罗汉
 * @date : 2024/5/14
 */
@Configuration
@EnableAspectJAutoProxy
public class SysLogAspectConfig {
    @Bean
    public SysLogAspect logAspect() {
        return new SysLogAspect();
    }
}
