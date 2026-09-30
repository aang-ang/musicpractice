package com.example.musicpratice.service;

import com.example.musicpratice.model.Song;
import com.example.musicpratice.repository.SongRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SongService {
    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song>getAllSongs(){
        return songRepository.findAll();
    }

    public void addSong(Song song) {
        songRepository.insert(song);
    }

    public void updateSong(Song song) {
        songRepository.update(song);
    }

    public void deleteSong(int id) {
        songRepository.delete(id);
    }

    // 상세정보
    public Song getSongById(int id) { return songRepository.findById(id); }

    // 숙련도 업데이트
    public void updateLevel(Song song) {
        songRepository.updateLevel(song);
    }
}