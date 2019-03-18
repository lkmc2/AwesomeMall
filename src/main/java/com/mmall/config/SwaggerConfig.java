package com.mmall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author lkmc2
 * @date 2018/9/25
 * @description Swagger2配置类
 * 页面访问路径：http://localhost:8080/swagger-ui.html
 */


@WebAppConfiguration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = "com.mmall.controller")
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("电商项目")
                .description("电商项目接口文档")
                .version("1.0.0")
                .contact(new Contact("lkmc2", "https://github.com/lkmc2", "lkmc2@163.com"))
                .build();
    }

}

