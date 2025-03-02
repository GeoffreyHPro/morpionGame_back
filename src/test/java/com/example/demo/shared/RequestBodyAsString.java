package com.example.demo.shared;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class RequestBodyAsString {
    private ObjectMapper mapper;
    private ObjectWriter ow;

    public RequestBodyAsString() {
        this.mapper = new ObjectMapper();
        this.mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        this.ow = mapper.writer().withDefaultPrettyPrinter();
    }

    public String getString(Object content) throws JsonProcessingException {
        return ow.writeValueAsString(content);
    }

}
