package com.example.yaprofi1.dao;

import lombok.Data;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Data
public class Group {
    private long id;
    private String name;
    private String description;
    private List<Participant> participants;
}
