package com.ams.checkin;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.ams.checkin.entity.CheckInRecord;
import com.ams.checkin.repository.CheckinRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class CheckinServiceApplication
{
	private static final Logger logger = LoggerFactory.getLogger(CheckinServiceApplication.class);

	public static void main(String[] args)
	{
		SpringApplication.run(CheckinServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner init(CheckinRepository repository)
	{
		return args ->
		{
			CheckInRecord record = new CheckInRecord("Krishnakumar", "Ramachandran", "28A", LocalDateTime
				.now(), "BF101", LocalDate.of(2019, 01, 22), "1");

			CheckInRecord result = repository.save(record);
			logger.info("checked in successfully ..." + result);

			logger.info("Looking to load checkedIn record...");
			logger.info("Result: " + repository.findById(result.getId())
				.get());

		};
	}

}
