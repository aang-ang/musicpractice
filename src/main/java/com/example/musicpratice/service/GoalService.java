package com.example.musicpratice.service;

import com.example.musicpratice.model.PracticeGoal;
import com.example.musicpratice.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {
    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    // 곡별 목표 목록 가져옴
    public List<PracticeGoal> getGoalSongId(int songId) {
        return goalRepository.findBySongId(songId);
    }

    // 새 목표 저장
    public void addGoal(PracticeGoal goal) {
        goalRepository.insert(goal);
    }

    // 기존 목표 수정
    public void updateGoal(PracticeGoal goal) {
        goalRepository.update(goal);
    }

    // 목표 삭제
    public void deleteGoal(int id) {
        goalRepository.delete(id);
    }
}
