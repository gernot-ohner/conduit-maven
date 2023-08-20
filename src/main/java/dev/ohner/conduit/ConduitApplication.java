package dev.ohner.conduit;

import dev.ohner.conduit.configuration.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class ConduitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConduitApplication.class, args);
	}

}
