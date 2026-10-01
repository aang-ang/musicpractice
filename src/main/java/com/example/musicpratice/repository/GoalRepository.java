package com.example.musicpratice.repository;

import com.example.musicpratice.model.PracticeGoal;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GoalRepository {
    private final JdbcTemplate jdbc;

    public GoalRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // db에서 온 데이터를 PracticeGoal 객체로 변환하는 함수
    private final RowMapper<PracticeGoal> goalMapper = (rs, rowNum) -> {
        PracticeGoal goal = new PracticeGoal();
        goal.setId(rs.getInt("id"));
        goal.setSongId(rs.getInt("song_id"));
        goal.setStartDate(rs.getDate("start_date").toLocalDate());
        goal.setEndDate(rs.getDate("end_date").toLocalDate());

        goal.setGoalDetail(rs.getString("goal_detail"));
        goal.setGoalMinutes(rs.getInt("goal_minutes"));
        goal.setChangeDetail(rs.getString("change_detail"));
        goal.setStatus(rs.getString("status"));
        goal.setCreatedDay(rs.getTimestamp("created_day").toLocalDateTime());

        return goal;
    };

    // 특정 곡의 목표 목록 가져오는 함수
    public List<PracticeGoal> findBySongId(int songId) {
        return jdbc.query("select * from practice_goal where song_id=? order by created_day desc", goalMapper,songId);
    }

    // 새 목표를 db에 저장 함수
    public void insert(PracticeGoal goal) {
        jdbc.update("insert into practice_goal(song_id, start_date, end_date, goal_detail, goal_minutes, change_detail) values (?,?,?,?,?,?)",
                goal.getSongId(), goal.getStartDate(), goal.getEndDate(), goal.getGoalDetail(), goal.getGoalMinutes(), goal.getChangeDetail());
    }

    // 기존 목표 내용 수정 함수
    public void update(PracticeGoal goal) {
        jdbc.update("update practice_goal set start_date=?, end_date=?, goal_detail=?, goal_minutes=?, change_detail=? where id=?",
                goal.getStartDate(), goal.getEndDate(), goal.getGoalDetail(), goal.getGoalMinutes(), goal.getChangeDetail(), goal.getId());
    }

    // 목표 삭제 함수
    public void delete(int id) {
        jdbc.update("delete from practice_goal where id=?", id);
    }

    // 달성률 계산 (해당 목표 기간 안에 있는 연습 기록의 총 분을 합산)
    public int getAchieve(int goalId) {
        String sql = "select coalesce(sum(timestampdiff(second, start_time, end_time)), 0) / 60 " +
                "from practice_record " +
                "where song_id = (select song_id from practice_goal where id = ?) " +
                "and date(start_time) between (select start_date from practice_goal where id = ?) " +
                "and (select end_date from practice_goal where id = ?)";
        return jdbc.queryForObject(sql, Integer.class, goalId, goalId, goalId);
    }

}
