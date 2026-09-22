<script setup>
import {ref,onMounted} from 'vue'
import {getSongs, addSong, updateSong, deleteSong} from '../api/song'

const songs = ref([])
const showForm = ref(false)
const editTarget = ref(null)
const form = ref({title: '', artist: '', instrument: ''})

async function loadSongs() {
  const res = await getSongs()
  songs.value = res.data
}

function openAdd() {
  editTarget.value = null
  form.value = {title: '', artist: '', instrument: ''}
  showForm.value = true
}

function openEdit(song) {
  editTarget.value = song
  form.value = {title: song.title, artist: song.artist, instrument: song.instrument}
  showForm.value = true
}

async function submitForm() {
  const msg = editTarget.value ? '수정하시겠습니까?' : '등록하시겠습니까?'
  if (!confirm(msg)) return

  if (editTarget.value) {
    await updateSong(editTarget.value.id, form.value)
  } else {
    await addSong(form.value)
  }
  showForm.value = false
  loadSongs()
}

async function remove(id) {
  if (confirm('정말 삭제할까요?')) {
    await deleteSong(id)
    loadSongs()
  }
}

onMounted(loadSongs)

</script>

<template>
  <div>
    <button class="btn" @click="openAdd">+ 곡 등록</button>

    <table class="song-table">
      <thead>
        <tr>
          <th>곡명</th>
          <th>아티스트</th>
          <th>악기</th>
          <th>숙련도</th>
          <th>등록일</th>
        </tr>
      </thead>
      <tbody>
        <tr v-if="songs.length===0">
          <td colspan="6" class="empty">등록된 곡이 없습니다.</td>
        </tr>

        <tr v-for="song in songs" :key="song.id">
          <td>{{song.title}}</td>
          <td>{{song.artist}}</td>
          <td>{{song.instrument}}</td>
          <td>{{ '★'.repeat(song.level) }}{{ '☆'.repeat(5 - song.level) }}</td>
          <td>{{song.createdDay?.slice(0,10)}}</td>
          <td>
              <button class="btn-sm" @click="openEdit(song)">수정</button>
              <button class="btn-sm btn-del" @click="remove(song.id)">삭제</button>
          </td>
        </tr>
      </tbody>
    </table>

    <div v-if="showForm" class="modal-backdrop">
      <div class="modal">
        <h3>{{ editTarget ? '곡 수정' : '곡 등록'}}</h3>
        <input v-model="form.title" placeholder="곡 제목">
        <input v-model="form.artist" placeholder="아티스트">
        <input v-model="form.instrument" placeholder="악기">
        <div class="modal-buttons">
          <button class="btn" @click="submitForm">저장</button>
          <button class="btn-cancel" @click="showForm = false">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.btn {
  background: #20A9E8;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  margin-bottom: 16px;
  font-size: 14px;
}
.btn:hover {
  background: #1890c8;
}

.btn-sm {
  background: none;
  border: 1px solid #E5E5E5;
  padding: 4px 10px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  margin-right: 4px;
}
.btn-sm:hover {
  background: #f5f5f5;
}

.btn-del {
  color: #ff6b35;
  border-color: #ff6b35;
}
.btn-del:hover {
  background: #fff3ef;
}

.song-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}
.song-table th {
  text-align: left;
  padding: 10px 12px;
  border-bottom: 2px solid #E5E5E5;
  color: #888888;
  font-weight: 600;
}
.song-table td {
  padding: 12px;
  border-bottom: 1px solid #E5E5E5;
  color: #444444;
}

.empty {
  text-align: center;
  padding: 32px;
  color: #888888;
}

.modal-backdrop {
  position: fixed;
  inset: 0;
  background: rgba(0,0,0,0.3);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal {
  background: white;
  padding: 24px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 320px;
}
.modal h3 {
  font-size: 16px;
  color: #444;
}
.modal input {
  border: 1px solid #E5E5E5;
  padding: 8px 12px;
  border-radius: 6px;
  font-size: 14px;
  outline: none;
}
.modal input:focus {
  border-color: #20A9E8;
}

.modal-buttons {
  display: flex;
  gap: 8px;
}

.btn-cancel {
  background: none;
  border: 1px solid #E5E5E5;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}

</style>