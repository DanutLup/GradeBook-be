package gradebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableSpringDataWebSupport
@EnableWebMvc
public class GradeBookApp{

    public static void main(String[] args) {
        SpringApplication.run(GradeBookApp.class, args);
    }

}