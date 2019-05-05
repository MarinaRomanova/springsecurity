package com.suez.acoustic_logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suez.acoustic_logger.entity.AcousticLogger;
import com.suez.acoustic_logger.repository.AcousticLoggerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcousticLoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcousticLoggerApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AcousticLoggerRepository repository) {
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            AcousticLogger[] acousticLoggers;
//            try {
//                acousticLoggers = objectMapper.readValue(new URL("https://accousticloggers.firebaseio.com/accousticloggers/-LbwLSfqHxl5MHw2T1_a.json"), AcousticLogger[].class);
//                repository.saveAll(Arrays.asList(acousticLoggers));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        };
    }

}
