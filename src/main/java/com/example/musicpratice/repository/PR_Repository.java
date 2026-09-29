package com.example.musicpratice.repository;

import com.example.musicpratice.model.PracticeRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PR_Repository {
    private final JdbcTemplate jdbc;

    public PR_Repository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public final RowMapper<PracticeRecord> recordMapper = (rs, rowNum) -> {
        PracticeRecord record = new PracticeRecord();
        record.setId(rs.getInt("id"));
        record.setSongId(rs.getInt("song_id"));
        record.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
        record.setEndTime(rs.getTimestamp("created_day").toLocalDateTime());
        return record;
    };

    // 곡별 기록 조회
    public List<PracticeRecord> findBySongId(int songId) {
        return jdbc.query("select * from practice_record where song_id=? order by start_time desc", recordMapper, songId);
    }

    // 삭제
    public void delete(int id) {
        jdbc.update("delete from practice_record where id=?", id);
    }

    public void insert(PracticeRecord record) {
        jdbc.update("insert into practice_record (song_id, start_time, end_time) values (?,?,?)",
                record.getSongId(), record.getStartTime(), record.getEndTime());
    }
}
