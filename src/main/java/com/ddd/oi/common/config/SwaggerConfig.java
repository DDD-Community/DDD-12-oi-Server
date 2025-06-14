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
		// 액세스 토큰 방식
		SecurityScheme accessTokenAuth = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER)
			.name("Authorization");

		// 리프레시 토큰 방식
		SecurityScheme refreshTokenAuth = new SecurityScheme()
			.type(SecurityScheme.Type.APIKEY)
			.in(SecurityScheme.In.COOKIE)
			.name("refreshToken");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("accessTokenAuth")
			.addList("refreshTokenAuth");

		// 서버 URL에 /api 추가
		Server localServer = new Server();
		Server devServer = new Server();
		localServer.setUrl("http://localhost:8080");

		devServer.setUrl("http://34.64.169.89:8080");

		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("accessTokenAuth", accessTokenAuth)
				.addSecuritySchemes("refreshTokenAuth", refreshTokenAuth))
			.security(List.of(securityRequirement))
			.info(new Info()
				.title("오이(Oi) API 명세서")
				.description("오이(Oi) API 명세서입니다.")
				.version("1.0.0"))
			.servers(List.of(localServer,devServer));
	}
}
