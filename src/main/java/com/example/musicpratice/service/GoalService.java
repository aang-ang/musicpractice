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

    public List<PracticeGoal> getGoalSongId(int songId) {
        return goalRepository.findBySongId(songId);
    }

    public void addGoal(PracticeGoal goal) {
        goalRepository.insert(goal);
    }

    public void updateGoal(PracticeGoal goal) {
        goalRepository.update(goal);
    }

    public void deleteGoal(int id) {
        goalRepository.delete(id);
    }
}
