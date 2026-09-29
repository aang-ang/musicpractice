package com.example.musicpratice.service;

import com.example.musicpratice.model.PracticeRecord;
import com.example.musicpratice.repository.PR_Repository;
import org.springframework.stereotype.Service;

@Service
public class PR_Service {
    private final PR_Repository prRepository;
    public PR_Service(PR_Repository prRepository) {
        this.prRepository = prRepository;
    }

    public void addRecord(PracticeRecord record) {
        prRepository.insert(record);
    }
}
