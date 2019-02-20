package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Import({
        PersistenceConfig.class,
        MigrationConfig.class
})
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy
public class ApplicationConfig {}
