package com.moayo.moayoeats.backend.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
public class JpaConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean//프로젝트 전역에서 QueryDSL을 작성할 수 있도록 함
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
