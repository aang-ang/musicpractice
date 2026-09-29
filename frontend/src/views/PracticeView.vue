<script setup>
import {ref, onMounted} from 'vue'
import {getSongs} from '../api/song'
import {addRecord} from "@/api/practice";

const startTime = ref(null)

const songs = ref([])
const selectedSong = ref(null)
const isRunning = ref(false)
const elapsed = ref(0)
let timer = null

onMounted(async () => {
  const res = await getSongs()
  songs.value = res.data
})

function selectSong(song) {
  if (isRunning.value) return
  selectedSong.value = song
  elapsed.value = 0
}

function start() {
  isRunning.value = true
  startTime.value = new Date() // 시작 시간 저장
  timer = setInterval(() => {elapsed.value++}, 1000)
}

function toLocalISO(date) {
  const offset = date.getTimezoneOffset() * 60000
  return new Date(date - offset).toISOString().slice(0, 19)
}

async function stop() {
  isRunning.value = false
  clearInterval(timer)
  await addRecord({
    songId: selectedSong.value.id,
    startTime: toLocalISO(startTime.value),
    endTime: toLocalISO(new Date())
  })
}

function formatTime(sec) {
  const h = String(Math.floor(sec / 3600)).padStart(2, '0')
  const m = String(Math.floor((sec % 3600) / 60)).padStart(2, '0')
  const s = String(sec % 60).padStart(2, '0')
  return `${h}:${m}:${s}`
}
</script>

<template>
  <div class="practice-wrap">
    <div class="song-list">
      <p class="section-label">곡 선택</p>
      <div v-for="song in songs" :key="song.id" class="song-item" :class="{active:selectedSong?.id === song.id}" @click="selectSong(song)">
        <span>{{song.title}}</span>
        <span class="artist">{{song.artist}}</span>
      </div>
    </div>

    <div class="timer-area">
      <p class="section-label">{{selectedSong ? selectedSong.title : '곡을 선택해주세요'}}</p>
      <div class="timer">{{formatTime(elapsed)}}</div>
      <div class="timer-buttons">
        <button class="btn" :disabled="!selectedSong || isRunning" @click="start">시작</button>
        <button class="btn btn-stop" :disabled="!isRunning" @click="stop">종료</button>
      </div>
    </div>
  </div>

</template>

<style scoped>
.practice-wrap {
  display: flex;
  gap: 0;
}
.song-list {
  width: 220px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 4px;
  padding-right: 32px;
  border-right: 1px solid #E5E5E5;
}
.section-label {
  font-size: 13px;
  color: #888888;
  margin-bottom: 8px;
}
.song-item {
  padding: 10px 12px;
  border: 1px solid #E5E5E5;
  border-radius: 6px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  gap: 2px;
}
.song-item:hover {
  background: #f5f5f5;
}
.song-item.active {
  border-color: #20a9e8;
  background: #f0f9ff;
}
.artist {
  font-size: 12px;
  color: #888888;
}


.timer-area {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 24px;
  padding-top: 32px;
  padding-left: 32px;
}
.timer {
  font-size: 64px;
  font-weight: 300;
  color: #444444;
  letter-spacing: 4px;
  font-family: 'Counter New', monospace;
  border-top: 1px solid #E5E5E5;
  border-bottom: 1px solid #E5E5E5;
  padding: 16px 32px;
}
.timer-buttons {
  display: flex;
  gap: 12px;
}
.btn {
  background: none;
  color: #20a9e8;
  border: 1px solid #20a9e8;
  padding: 10px 32px;
  border-radius: 2px;
  cursor: pointer;
  font-size: 15px;
}
.btn:hover:not(:disabled) {
  background: #20a9e8;
  color: white;
}
.btn:hover {
  background: #20a9e8;
  color: white;
}
.btn:disabled {
  opacity: 0.4;
  cursor: default;
}
.btn-stop {
  color: #ff6b35;
  border-color: #ff6b35;
}
.btn-stop:hover {
  background: #ff6b35;
  color: white;
}
.btn-stop:hover:not(:disabled) {
  background: #ff6b35;
  color: white;
}
</style>