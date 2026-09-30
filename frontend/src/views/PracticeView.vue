<script setup>
import {ref, onMounted} from 'vue'
import {getSongs} from '../api/song'
import {addRecord} from "@/api/practice";
import {updateLevel} from "../api/song";

const startTime = ref(null)

const songs = ref([])
const selectedSong = ref(null)
const isRunning = ref(false)
const elapsed = ref(0)
let timer = null

const showPopup = ref(false)
const selectedLevel = ref(0)
const levelDesc = {
  0: '숙련도가 없습니다.',
  1: '1단계 - 입문',
  2: '2단계 - 초반 연주 가능',
  3: '3단계 - 중반 연주 가능',
  4: '4단계 - 완곡 가능(자잘한 실수)',
  5: '5단계 - 완벽하게 완곡 가능'
}

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
  showPopup.value = true // 팝업 열기
}
async function submitLevel() {
  await updateLevel(selectedSong.value.id, selectedLevel.value)
  showPopup.value = false
  selectedLevel.value = 0
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

    <div v-if="showPopup" class="modal-backdrop">
      <div class="modal">
        <h3>연습 완료</h3>
        <p class="modal-sub">현재 숙련도를 선택해주세요.</p>
        <div class="stars">
          <span v-for="n in 5" :key="n" class="star" :class="{filled: n <= selectedLevel}" @click="selectedLevel = n">★</span>
        </div>
        <p class="level-desc">{{levelDesc[selectedLevel]}}</p>
        <div class="modal-buttons">
          <button class="btn" @click="submitLevel()">확인</button>
          <button class="btn-cancel" @click="showPopup = false">건너뛰기</button>
        </div>
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

.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal {
  background: white;
  padding: 32px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  width: 320px;
}
.modal h3 {
  font-size: 18px;
  color: #444444;
}
.modal-sub {
  font-size: 13px;
  color: #888888;
}
.stars {
  display: flex;
  gap: 8px;
}
.star {
  font-size: 36px;
  color: #e5e5e5;
  cursor: pointer;
}
.star.filled {
  color: #20a9e8;
}
.level-desc {
  font-size: 13px;
  color: #888888;
}
.modal-buttons {
  display: flex;
  gap: 8px;
  margin-top: 8px;
}
.btn:hover {
  background: #20a9e8;
  color: white;
}
.btn-cancel {
  background: none;
  border: 1px solid #e5e5e5;
  padding: 10px 32px;
  border-radius: 2px;
  cursor: pointer;
  font-size: 14px;
  color: #444444;
}
.btn-cancel:hover {
  background: #f5f5f5;
}
</style>