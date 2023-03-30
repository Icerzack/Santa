package com.example.yaprofi1.dao;

import lombok.Data;

@Data
public class Participant {
    private long id;
    private String name;
    private String wish;
    private ParticipantShorten recipient;
}
