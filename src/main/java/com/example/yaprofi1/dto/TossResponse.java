package com.example.yaprofi1.dto;

import com.example.yaprofi1.dao.Participant;
import lombok.Data;

import java.util.List;

@Data
public class TossResponse {
    private List<Participant> participants;
}
