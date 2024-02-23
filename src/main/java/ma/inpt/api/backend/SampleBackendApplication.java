package ma.inpt.api.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
@ComponentScan(basePackages = { "ma.inpt.api.backend"})
public class SampleBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleBackendApplication.class, args);
	}

}
