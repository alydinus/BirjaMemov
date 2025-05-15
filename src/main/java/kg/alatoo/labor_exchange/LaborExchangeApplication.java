package kg.alatoo.labor_exchange;

import kg.alatoo.labor_exchange.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LaborExchangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaborExchangeApplication.class, args);
	}

}
