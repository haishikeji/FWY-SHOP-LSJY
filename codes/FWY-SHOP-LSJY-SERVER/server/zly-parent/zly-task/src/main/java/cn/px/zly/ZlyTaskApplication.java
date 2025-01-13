package cn.px.zly;

import cn.px.core.redis.EnableMultiRedisTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 品讯科技
 * @date 2024-08
 */
@EnableCaching
@EnableScheduling
@EnableMultiRedisTemplate
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan(basePackages={"cn.px"}, annotationClass= Mapper.class)
@SpringBootApplication(scanBasePackages={"cn.px"})
public class ZlyTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZlyTaskApplication.class, args);
    }
}
