package com.example.finalproject.model.helpers;

import com.example.finalproject.model.dtos.HistoryEntryDTO;
import com.example.finalproject.model.entities.HistoryEntry;
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

    public static HistoryEntryDTO getDto(HistoryWithMessages dbEntry) {
        HistoryEntryDTO dto = new HistoryEntryDTO();
        dto.setId(dbEntry.getHistory().getId());
        dto.setPhoneName(dbEntry.getHistory().getPhoneName());
        dto.setStartTime(dbEntry.getHistory().getStartTime());
        dto.setMessages(MessageHelper.getDtos(dbEntry.getMessages()));
        return dto;
    }

    public List<HistoryEntry> fromDtos(List<HistoryEntryDTO> dtos) {
        List<HistoryEntry> entities = new ArrayList<>();
        for (HistoryEntryDTO dto : dtos) {
            entities.add(fromDto(dto));
        }
        return entities;
    }

    private HistoryEntry fromDto(HistoryEntryDTO dto){
        HistoryEntry entry = new HistoryEntry();
        entry.setId(dto.getId());
        entry.setPhoneName(dto.getPhoneName());
        entry.setStartTime(dto.getStartTime());
        return entry;
    }
}
