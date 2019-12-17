package demo.config;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import sharding.EnableSharding;

import javax.persistence.EntityManager;

/**
 * @author fun
 * @date 19-3-7
 */
@EnableSharding
@SpringBootConfiguration
public class DemoConfig {

    @Autowired
    private EntityManager entityManager;

    @Bean
    public JPQLQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
