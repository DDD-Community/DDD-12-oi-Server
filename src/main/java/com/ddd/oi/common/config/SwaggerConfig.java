package com.ddd.oi.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme accessToken = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
				.in(SecurityScheme.In.HEADER)
				.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement()
				.addList("accessToken");

		Server localServer = new Server().url("http://localhost:8080");
		Server devServer = new Server().url("http://3.39.107.176:8080");

		return new OpenAPI()
				.components(new Components().addSecuritySchemes("Authorization", accessToken))
				.security(List.of(securityRequirement))
				.info(new Info()
						.title("오이(Oi) API 명세서")
						.description("오이(Oi) API 명세서입니다.")
						.version("1.0.0"))
				.servers(List.of(devServer, localServer));
	}
}
