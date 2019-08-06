package com.example.finalproject.model.helpers;

import com.example.finalproject.model.dtos.MessageDTO;
import com.example.finalproject.model.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageHelper {

    static List<MessageDTO> getDtos(List<Message> dbMessages) {
        List<MessageDTO> resultMessageDtos=new ArrayList<>();
        if (dbMessages != null) {
            for (Message message : dbMessages) {
                resultMessageDtos.add(getDto(message));
            }
        }
        return resultMessageDtos;
    }

    public static MessageDTO getDto(Message dbMessage){
        if (dbMessage == null) {
            return null;
        }
        MessageDTO dto = new MessageDTO();
        dto.setContent(dbMessage.getContent());
        dto.setTime(dbMessage.getTime());
        dto.setFromMe(dbMessage.isFromMe());
        dto.setId(dbMessage.getId());
        return dto;
    }
}
