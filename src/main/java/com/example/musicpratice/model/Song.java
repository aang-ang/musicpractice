package com.example.musicpratice.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class Song {
    private int id;
    private String title;                               // 곡 제목
    private String artist;                              // 가수
    private String instrument;                          // 연습할 악기
    private int level;                                  // 숙련도
    private LocalDateTime createdDay;                   // 생성날짜
}