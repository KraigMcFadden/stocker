package studio.blackbarn.stocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@SpringBootApplication
public class StockerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockerApplication.class, args);
	}

//	@Bean
//	public DataSource readWriteDataSource() {
//		return new DriverManagerDataSource("jdbc:mysql://stocker.cr9thz7p6eak.us-east-1.rds.amazonaws.com:3306/stocker",
//				"kwm1218x", "Kwm658!!");
//	}
}
