package Main;

import RestController.PollUserRest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan(basePackageClasses = PollUserRest.class)
public class SpringApp {

    public static void main(String[] args) { SpringApplication.run(SpringApp.class, args); }
}
