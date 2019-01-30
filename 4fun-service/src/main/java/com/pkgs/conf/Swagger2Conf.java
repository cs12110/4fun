package com.pkgs.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 conf
 * <p>
 * 访问地址: `http://127.0.0.1:8081/swagger-ui.html`
 * <p/>
 *
 * @author cs12110 created at: 2019/1/30 14:25
 * <p>
 * since: 1.0.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Conf {


    /**
     * swagger2配置文件,配置扫描包
     *
     * @return Docket
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pkgs.ctrl"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建API信息
     *
     * @return ApiInfo
     */
    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("Api home")
                .contact("cs12110")
                .version("1.0")
                .description("rest api docs")
                .build();
    }
}
