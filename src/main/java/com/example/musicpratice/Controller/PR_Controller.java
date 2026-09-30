package com.example.musicpratice.Controller;


import com.example.musicpratice.model.PracticeRecord;
import com.example.musicpratice.service.PR_Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/records")
@CrossOrigin(origins = "http://localhost:5173")
public class PR_Controller {
    private final PR_Service prService;

    public PR_Controller(PR_Service prService) {
        this.prService = prService;
    }

    @PostMapping
    public void add (@RequestBody PracticeRecord record) {
        prService.addRecord(record);
    }

    // 곡별 기록 조회
    @GetMapping("/song/{songId}")
    public List<PracticeRecord> getBySongId(@PathVariable int songId) {
        return prService.getSongRecord(songId);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        prService.deleteRecord(id);
    }
}
