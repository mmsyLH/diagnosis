package asia.lhweb.diagnosis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"asia.lhweb.diagnosis"})
public class SwaggerConfig {
    /**
     * 创建管理端API文档
     */
    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(adminApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("asia.lhweb.diagnosis.controller.admin"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    /**
     * 创建用户端API文档
     */
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("用户端接口")
                .apiInfo(userApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("asia.lhweb.diagnosis.controller.user"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }


    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用类接口")
                .apiInfo(commonInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("asia.lhweb.diagnosis.controller.common"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }
    /**
     * 管理端API文档信息
     */
    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("管理端接口文档")
                .description("用于管理端的API文档")
                .version("1.0")
                .build();
    }

    /**
     * 用户端API文档信息
     */
    private ApiInfo userApiInfo() {
        return new ApiInfoBuilder()
                .title("用户端接口文档")
                .description("用于用户端的API文档")
                .version("1.0")
                .build();
    }

    /**
     * 测试端API文档信息
     */
    private ApiInfo commonInfo() {
        return new ApiInfoBuilder()
                .title("通用类接口文档")
                .description("用于用户端的API文档.例如验证码、支付系统、文件上传等")
                .version("1.0")
                .build();
    }
}
