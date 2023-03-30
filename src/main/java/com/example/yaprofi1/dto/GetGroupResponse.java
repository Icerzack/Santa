package com.example.yaprofi1.dto;

import com.example.yaprofi1.dao.Participant;
import com.example.yaprofi1.dao.ParticipantShorten;
import lombok.Data;

import java.util.List;

@Data
public class GetGroupResponse {
    private long id;
    private String name;
    private String description;
    private List<Participant> participants;
}
