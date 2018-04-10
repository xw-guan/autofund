package com.xwguan.autofund.util;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

@Component
public class Jdk8JacksonMapper {
    
    private static final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new ParameterNamesModule())
        .registerModule(new Jdk8Module())
        .registerModule(new JavaTimeModule()) // new module, NOT JSR310Module
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ;

    public static ObjectMapper getMapper() {
        return mapper;
    }
}
