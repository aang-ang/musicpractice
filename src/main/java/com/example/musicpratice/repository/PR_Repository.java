package com.example.musicpratice.repository;

import com.example.musicpratice.model.PracticeRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PR_Repository {
    private final JdbcTemplate jdbc;

    public PR_Repository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insert(PracticeRecord record) {
        jdbc.update("insert into practice_record (song_id, start_time, end_time) values (?,?,?)",
                record.getSongId(), record.getStartTime(), record.getEndTime());
    }
}
