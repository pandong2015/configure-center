package tech.pcloud.configure.center.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tech.pcloud.configure.center.server.config.AppProperties;
import tech.pcloud.configure.center.server.config.NettyServerProperties;

/**
 * @ClassName AppConfig
 * @author pandong
 * @Date 2018年9月21日 上午9:38:13
 */
@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("tech.pcloud.configure.center.server")
@MapperScan("tech.pcloud.configure.center.server.mapper")
@EnableConfigurationProperties({NettyServerProperties.class, AppProperties.class})
public class AppConfig {

}
