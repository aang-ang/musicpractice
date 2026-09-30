package com.example.musicpratice.Controller;

import com.example.musicpratice.model.PracticeGoal;
import com.example.musicpratice.service.GoalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:5173")
public class GoalController {
    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/song/{songId}")
    public List<PracticeGoal> getSongId(@PathVariable int songId) {
        return goalService.getGoalSongId(songId);
    }

    @PostMapping
    public void add(@RequestBody PracticeGoal goal) {
        goalService.addGoal(goal);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody PracticeGoal goal) {
        goal.setId(id);
        goalService.updateGoal(goal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        goalService.deleteGoal(id);
    }
}
