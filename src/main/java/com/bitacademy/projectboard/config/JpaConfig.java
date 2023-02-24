package com.bitacademy.projectboard.config;

import com.bitacademy.projectboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

	// 게시물을 생성 했을 때 사랍 이름 정보를 알려주기 위해서 생성.
	// Auditing 할 때 마다 사람 이름이 들어간다.(게시글을 작성할때 마다 누가 만들었는지에 대한 정보) 
    @Bean
    public AuditorAware<String> auditorAware() { // 사람 이름이니까 string
    	 //  return () -> Optional.of("june"); --> 나중에 인증기능 붙인다.(식별할 수 있는)
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(BoardPrincipal.class::cast)
                .map(BoardPrincipal::getUsername);
    }

}