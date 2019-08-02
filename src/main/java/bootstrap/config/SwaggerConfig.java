package bootstrap.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger相关配置类
 */
@EnableSwagger2
@Configuration
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {


    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("bootstrap.controller")).paths(PathSelectors.any()).build();// cn为扫描的包路径
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口文档")// 页面标题
                .contact(new Contact("sw", "url", ""))// 作者
                .version("1.0")
                .description("描述").build();
    }
}
