package com.bitacademy.projectboard.config;

import java.util.Optional;

import org. springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration // 각종 설정을 잡을 때 사용 
public class JpaConfig {

	@Bean // 이름 넣는 곳 
	public AuditorAware<String> auditorAware() {
		return () -> Optional.of("june"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때, 수정하기 
	}
}
