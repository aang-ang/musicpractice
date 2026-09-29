package com.example.musicpratice.service;

import com.example.musicpratice.model.PracticeRecord;
import com.example.musicpratice.repository.PR_Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PR_Service {
    private final PR_Repository prRepository;
    public PR_Service(PR_Repository prRepository) {
        this.prRepository = prRepository;
    }

    public void addRecord(PracticeRecord record) {
        prRepository.insert(record);
    }

    public List<PracticeRecord> getRecordsBySongId(int songId) {
        return prRepository.findBySongId(songId);
    }

    public void deleteRecord(int id) {
        prRepository.delete(id);
    }
}
