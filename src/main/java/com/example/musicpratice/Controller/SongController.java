package com.example.musicpratice.Controller;

import com.example.musicpratice.model.Song;
import com.example.musicpratice.service.SongService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/songs")
public class SongController {
    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // 조회
    @GetMapping
    public List<Song> getAll() {
        return songService.getAllSongs();
    }
    // 등록
    @PostMapping
    public void add(@RequestBody Song song) {
        songService.addSong(song);
    }
    // 수정
    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Song song) {
        song.setId(id);
        songService.updateSong(song);
    }
    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        songService.deleteSong(id);
    }

    // 상세정보
    @GetMapping("/{id}")
    public Song getById(@PathVariable int id) {
        return songService.getSongById(id);
    }
}
