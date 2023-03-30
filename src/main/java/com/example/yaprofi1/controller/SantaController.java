package com.example.yaprofi1.controller;

import com.example.yaprofi1.dto.*;
import com.example.yaprofi1.service.SantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SantaController {

    @Autowired
    private SantaService santaService;

    @PostMapping ("/group")
    public ResponseEntity addGroup(@RequestBody AddGroupRequest addGroupRequest) {
        String name = addGroupRequest.getName();
        String description = addGroupRequest.getDescription();
        return santaService.addGroup(name, description);
    }

    @GetMapping("/groups")
    public ResponseEntity getGroups() {
        return santaService.getGroups();
    }

    @GetMapping("/group/{id}")
    public ResponseEntity getGroup(@PathVariable long id) {
        return santaService.getGroup(id);
    }

    @PutMapping("/group/{id}")
    public ResponseEntity getGroup(@RequestBody UpdateGroupRequest updateGroupRequest, @PathVariable long id) {
        String name = updateGroupRequest.getName();
        String description = updateGroupRequest.getDescription();
        return santaService.updateGroup(id, name, description);
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroup(@PathVariable long id) {
        return santaService.deleteGroup(id);
    }

    @PostMapping("/group/{id}/participant")
    public ResponseEntity addParticipant(@RequestBody AddParticipantRequest addParticipantRequest, @PathVariable long id) {
        String name = addParticipantRequest.getName();
        String wish = addParticipantRequest.getWish();
        return santaService.addParticipant(id, name, wish);
    }

    @DeleteMapping("/group/{groupId}/participant/{participantId}")
    public ResponseEntity deleteGroup(@PathVariable long groupId, @PathVariable long participantId) {
        return santaService.deleteParticipant(groupId, participantId);
    }

    @PostMapping("/group/{id}/toss")
    public ResponseEntity addParticipant(@PathVariable long id) {
        return santaService.toss(id);
    }

    @GetMapping("/group/{groupId}/participant/{participantId}/recipient")
    public ResponseEntity getRecipientResponse(@PathVariable long groupId, @PathVariable long participantId){
        return santaService.getRecipient(groupId, participantId);
    }
}
