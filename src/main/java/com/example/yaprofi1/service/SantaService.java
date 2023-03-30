package com.example.yaprofi1.service;

import com.example.yaprofi1.dao.Group;
import com.example.yaprofi1.dao.GroupShorten;
import com.example.yaprofi1.dao.Participant;
import com.example.yaprofi1.dao.ParticipantShorten;
import com.example.yaprofi1.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SantaService {

    private List<Group> groupsList = new ArrayList<>();
    private long globalGroupIdGenerator = 0;
    private long globalParticipantIdGenerator = 0;

    public ResponseEntity addGroup(String name, String description) {
        Group newGroup = new Group();
        newGroup.setName(name);
        newGroup.setDescription(description);
        newGroup.setId(globalGroupIdGenerator);
        newGroup.setParticipants(new ArrayList<Participant>());
        groupsList.add(newGroup);

        AddGroupResponse addGroupResponse = new AddGroupResponse();
        addGroupResponse.setId(globalGroupIdGenerator);

        globalGroupIdGenerator = globalGroupIdGenerator + 1;

        return ResponseEntity.ok(addGroupResponse);
    }

    public ResponseEntity getGroups() {

        List<GroupShorten> tempList = new ArrayList<>();

        for (Group gr : groupsList) {
            GroupShorten temp = new GroupShorten();
            temp.setId(gr.getId());
            temp.setName(gr.getName());
            temp.setDescription(gr.getDescription());
            tempList.add(temp);
        }

        GetGroupsResponse getGroupsResponse = new GetGroupsResponse();

        getGroupsResponse.setGroups(tempList);

        return ResponseEntity.ok(getGroupsResponse);
    }

    public ResponseEntity getGroup(long id) {
        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == id) {
                contains = true;
                break;
            }
        }
        if (contains) {
            Group group = groupsList.get((int) id);

            GetGroupResponse getGroupResponse = new GetGroupResponse();

            getGroupResponse.setId(group.getId());
            getGroupResponse.setName(group.getName());
            getGroupResponse.setDescription(group.getDescription());
            getGroupResponse.setParticipants(group.getParticipants());

            return ResponseEntity.ok(getGroupResponse);
        } else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого id группы нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

    }

    public ResponseEntity updateGroup(long id, String name, String description) {
        UpdateGroupResponse updateGroupResponse = new UpdateGroupResponse();
        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == id) {
                contains = true;
                break;
            }
        }
        if ((name == null) || name.equals("")) {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Имя не может быть пустым");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        } else if(!contains) {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого id группы нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        } else {
            Group groupToUpdate = groupsList.get((int) id);
            groupToUpdate.setName(name);
            groupToUpdate.setDescription(description);
            updateGroupResponse.setResult(true);
        }

        return ResponseEntity.ok(updateGroupResponse);
    }

    public ResponseEntity deleteGroup(long id) {
        DeleteGroupResponse deleteGroupResponse = new DeleteGroupResponse();

        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == id) {
                contains = true;
                break;
            }
        }
        if (contains) {
            groupsList.remove((int) id);
            deleteGroupResponse.setResult(true);
        }
        else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого id группы нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

        return ResponseEntity.ok(deleteGroupResponse);
    }

    public ResponseEntity addParticipant(long id, String name, String wish) {
        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == id) {
                contains = true;
                break;
            }
        }
        if (contains) {
            Group groupToAdd = groupsList.get((int) id);

            List<Participant> participants = groupToAdd.getParticipants();

            Participant newParticipant = new Participant();
            newParticipant.setId(globalParticipantIdGenerator);
            newParticipant.setName(name);
            newParticipant.setWish(wish);

            participants.add(newParticipant);

            groupToAdd.setParticipants(participants);

            AddParticipantResponse addParticipantResponse = new AddParticipantResponse();
            addParticipantResponse.setId(globalParticipantIdGenerator);

            globalParticipantIdGenerator = globalParticipantIdGenerator + 1;

            return ResponseEntity.ok(addParticipantResponse);
        } else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого id группы нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

    }

    public ResponseEntity deleteParticipant(long groupId, long participantId) {
        DeleteParticipantResponse deleteGroupResponse = new DeleteParticipantResponse();

        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == groupId) {
                contains = true;
                break;
            }
        }
        if (contains) {
            boolean containsP = false;
            for (Participant p : groupsList.get((int) groupId).getParticipants()) {
                if (p.getId() == participantId) {
                    containsP = true;
                    break;
                }
            }
            if (containsP) {
                groupsList.get((int) groupId).getParticipants().remove((int) participantId);
                deleteGroupResponse.setResult(true);
            } else {
                ConflictResponse conflictResponse = new ConflictResponse();
                conflictResponse.setErrorDescription("Такой groupId есть, но такого participantId - нет");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
            }

        }
        else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого groupId нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

        return ResponseEntity.ok(deleteGroupResponse);
    }

    public ResponseEntity toss(long id) {
        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == id) {
                contains = true;
                break;
            }
        }
        if (contains) {
            Group groupToToss = groupsList.get((int) id);

            if (groupToToss.getParticipants().size() >= 3) {
                List<Participant> participants = groupToToss.getParticipants();
                Participant[] temp = new Participant[participants.size()];
                int counter = 0;
                for (Participant participant : participants) {
                    temp[counter] = participant;
                    counter++;
                }
                for (int i = 0; i < temp.length; i++) {
                    ParticipantShorten participantShorten = new ParticipantShorten();
                    if (i == temp.length-1) {
                        participantShorten.setId(temp[0].getId());
                        participantShorten.setName(temp[0].getName());
                        participantShorten.setWish(temp[0].getWish());

                    } else {
                        participantShorten.setId(temp[i+1].getId());
                        participantShorten.setName(temp[i+1].getName());
                        participantShorten.setWish(temp[i+1].getWish());

                    }
                    temp[i].setRecipient(participantShorten);
                }
                participants = Arrays.stream(temp).toList();

                TossResponse tossResponse = new TossResponse();
                tossResponse.setParticipants(participants);

                return ResponseEntity.ok(tossResponse);
            } else {
                ConflictResponse conflictResponse = new ConflictResponse();
                conflictResponse.setErrorDescription("Размер группы < 3");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
            }
        } else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого id группы нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

    }

    public ResponseEntity getRecipient(long groupId, long participantId) {
        boolean contains = false;
        for (Group gr : groupsList) {
            if (gr.getId() == groupId) {
                contains = true;
                break;
            }
        }
        if (contains) {
            List<Participant> participants = groupsList.get((int) groupId).getParticipants();
            boolean containsP = false;
            for (Participant p : participants) {
                if (p.getId() == participantId) {
                    containsP = true;
                    break;
                }
            }
            if (containsP) {
                ParticipantShorten participantShorten = participants.get((int) participantId).getRecipient();

                GetRecipientResponse getRecipientResponse = new GetRecipientResponse();

                if(participantShorten == null) {
                    ConflictResponse conflictResponse = new ConflictResponse();
                    conflictResponse.setErrorDescription("Такой groupId есть, такой participantId есть, но пока не производилась жеребьевка");
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
                }

                getRecipientResponse.setId(participantShorten.getId());
                getRecipientResponse.setName(participantShorten.getName());
                getRecipientResponse.setWish(participantShorten.getWish());

                return ResponseEntity.ok(getRecipientResponse);
            } else {
                ConflictResponse conflictResponse = new ConflictResponse();
                conflictResponse.setErrorDescription("Такой groupId есть, но такого participantId - нет");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
            }
        }
        else {
            ConflictResponse conflictResponse = new ConflictResponse();
            conflictResponse.setErrorDescription("Такого groupId нет");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(conflictResponse);
        }

    }
}
