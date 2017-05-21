import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Dragos on 5/20/2017.
 */
@ComponentScan(basePackages = {"controller", "repository"})
@SpringBootApplication
public class MainService {
    public static void main(String[] args) {

        SpringApplication.run(MainService.class, args);
    }
}
