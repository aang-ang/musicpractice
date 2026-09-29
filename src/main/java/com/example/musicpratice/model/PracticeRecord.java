package com.example.musicpratice.model;
import com.example.musicpratice.model.PracticeRecord;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PracticeRecord {
    // 시작 & 종료 시간은 버튼을 누른 그 시각을 기준으로 함
    private int id;
    private int songId;                 // 선택한 곡
    private LocalDateTime startTime;    // 시작 시간
    private LocalDateTime endTime;      // 종료 시간
    private LocalDateTime createdDay;   // 기록 생성일
}


