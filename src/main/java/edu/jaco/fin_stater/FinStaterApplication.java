package edu.jaco.fin_stater;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class FinStaterApplication {

	public static void main(String[] args) throws SQLException {
		ConfigurableApplicationContext ctx = SpringApplication.run(FinStaterApplication.class, args);
	}

	@Bean
	public CommandLineRunner printBeans(ApplicationContext ctx) {
		return (args) -> {
			System.out.println("Bean names:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			int i = 1;
			for (String beanName : beanNames) {
				System.out.println(i + ": " + beanName);
				i++;
			}
		};
	}

}
