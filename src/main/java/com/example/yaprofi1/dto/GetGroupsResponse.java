package com.example.yaprofi1.dto;

import com.example.yaprofi1.dao.GroupShorten;
import lombok.Data;

import java.util.List;

@Data
public class GetGroupsResponse {
    private List<GroupShorten> groups;
}