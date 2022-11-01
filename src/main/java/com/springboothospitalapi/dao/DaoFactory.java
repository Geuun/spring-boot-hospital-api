package com.springboothospitalapi.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DaoFactory {

    @Bean
    HospitalDao hospitalDao() {
        return new HospitalDao(new JdbcTemplate());
    }
}
