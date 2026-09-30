package com.example.musicpratice.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
public class PracticeGoal {
    private int id;
    private int songId;             // 선택한 곡
    private LocalDate startDate;    // 목표 시작일
    private LocalDate endDate;      // 목표 종료일
    private String goalDetail;      // 목표 내용
    private int goalMinutes;        // 목표 연습 시간
    private String changeDetail;    // 개선 사항
    private String status;          // 목표 상태
    private LocalDateTime createdDay;   // 목표 등록한 날짜 & 시간

}
