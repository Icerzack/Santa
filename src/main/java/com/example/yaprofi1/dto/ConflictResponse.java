package com.example.yaprofi1.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
public class ConflictResponse {
    private String errorDescription;
}
