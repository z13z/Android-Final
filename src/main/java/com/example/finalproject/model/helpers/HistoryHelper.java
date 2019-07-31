package com.example.finalproject.model.helpers;

import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.entities.HistoryWithMessages;

import java.util.ArrayList;
import java.util.List;

public class HistoryHelper {

    public static List<HistoryEntryDTO> getDtos(List<HistoryWithMessages> dbObjects) {
        List<HistoryEntryDTO> resultList = new ArrayList<>();
        for (HistoryWithMessages dbEntry : dbObjects) {
            resultList.add(getDto(dbEntry));
        }
        return resultList;
    }

    private static HistoryEntryDTO getDto(HistoryWithMessages dbEntry) {
        HistoryEntryDTO dto = new HistoryEntryDTO();
        dto.setId(dbEntry.getHistory().getId());
        dto.setPhoneName(dbEntry.getHistory().getPhoneName());
        dto.setStartTime(dbEntry.getHistory().getStartTime());
        dto.setMessages(MessageHelper.getDtos(dbEntry.getMessages()));
        return dto;
    }
}
