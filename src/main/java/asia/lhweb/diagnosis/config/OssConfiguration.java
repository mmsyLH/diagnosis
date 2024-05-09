package asia.lhweb.diagnosis.config;


import asia.lhweb.diagnosis.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 配置类，用于创建阿里oss工具类对象
 *
 * @author 罗汉
 * @date 2024/05/09
 */
@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties){
        log.info("开始上传阿里云文件上传工具类对象:{}",aliOssProperties);
       return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName());
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        // 设置文件上传大小的上限
        resolver.setMaxUploadSize(10485760); // 10MB的限制
        return resolver;
    }
}
