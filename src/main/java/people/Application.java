package people;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories //enable jpa api
//@EntityScan
//@EnableWebMvc //enable spring mvc
@ComponentScan(basePackages = "people") //necessary to find code-defined beans and configurations
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //start app
    }
}
