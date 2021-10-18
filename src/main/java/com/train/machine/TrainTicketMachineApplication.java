package com.train.machine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@ComponentScan("com.train.machine")
public class TrainTicketMachineApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainTicketMachineApplication.class, args);
	}
}
