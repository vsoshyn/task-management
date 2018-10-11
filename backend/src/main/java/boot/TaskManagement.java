package boot;

import config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(ApplicationConfig.class)
@SpringBootApplication
public class TaskManagement {
    public static void main(String[] args) {
        SpringApplication.run(TaskManagement.class);
    }
}
