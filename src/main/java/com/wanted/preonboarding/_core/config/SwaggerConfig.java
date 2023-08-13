package com.wanted.preonboarding._core.config;

import io.swagger.v3.oas.models.Components;
        import io.swagger.v3.oas.models.OpenAPI;
        import io.swagger.v3.oas.models.info.Info;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Wanted BackEnd Pre Onboarding RestFulAPI")
                .description("원티드 2023 08월 백엔드 | 프리온보딩 인턴십")
                .version("1.0.0");
    }
}
