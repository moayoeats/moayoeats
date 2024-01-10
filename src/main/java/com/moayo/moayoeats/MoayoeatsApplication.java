package com.moayo.moayoeats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoayoeatsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoayoeatsApplication.class, args);
    }

}
