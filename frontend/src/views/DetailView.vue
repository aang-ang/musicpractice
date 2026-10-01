<script setup>
import flatpickr from 'flatpickr'
import 'flatpickr/dist/flatpickr.min.css'
import {ref,onMounted, watch} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {getSongById} from '../api/song'
import {deleteRecord, getSongRecord} from "@/api/practice.js";
import {getGoalsSongId, addGoal, updateGoal, deleteGoal} from "../api/goal";

const goals = ref([])
const showGoalForm = ref(false)
const editGoal = ref(null)
const goalForm = ref({startDate: "", endDate:"", goalDetail:"", goalMinutes: "", changeDetail: ""})
const goalHours = ref(0)
const goalMins = ref(0)

async function loadGoals() {
  const res = await getGoalsSongId(route.params.id)
  goals.value = res.data
}

function openAddGoal() {
  editGoal.value = null
  goalForm.value = {startDate: "", endDate:"", goalDetail:"", goalMinutes: "", changeDetail: ""}
  goalHours.value = 0
  goalMins.value = 0
  showGoalForm.value = true
}

function openEditGoal(goal) {
  editGoal.value = goal
  goalForm.value = {
    startDate: goal.startDate,
    endDate: goal.endDate,
    goalDetail: goal.goalDetail,
    goalMinutes: goal.goalMinutes,
    changeDetail: goal.changeDetail
  }
  goalHours.value = Math.floor(goal.goalMinutes / 60)
  goalMins.value = goal.goalMinutes % 60
  showGoalForm.value = true
}

async function submitGoal() {
  goalForm.value.goalMinutes = goalHours.value * 60 + goalMins.value
  const msg = editGoal.value ? '수정하시겠습니까?' : '등록하시겠습니까?'
  if (!confirm(msg)) return

  const data = {...goalForm.value, songId: route.params.id}
  if (editGoal.value) {
    await updateGoal(editGoal.value.id, data)
  } else {
    await addGoal(data)
  }
  showGoalForm.value = false
  loadGoals()
}

async function removeGoal(id) {
  if (!confirm('삭제하시겠습니까?')) return
  await deleteGoal(id)
  loadGoals()
}

const route = useRoute()
const router = useRouter()
const song = ref(null)

const levelDesc = {
  0: '숙련도가 없습니다.',
  1: '1단계 - 입문',
  2: '2단계 - 초반 연주 가능',
  3: '3단계 - 중반 연주 가능',
  4: '4단계 - 완곡 가능(자잘한 실수)',
  5: '5단계 - 완벽하게 완곡 가능'
}

const records = ref([])

async function loadRecords() {
  const res = await getSongRecord(route.params.id)
  records.value = res.data
}


async function removeRecord(id) {
  if (!confirm('정말 삭제할까요?')) return
  await deleteRecord(id)
  loadRecords()
}

function formatDuration(start, end) {
  const sec = Math.floor((new Date(end) - new Date(start)) / 1000)
  const h = Math.floor(sec / 3600)
  const m = Math.floor((sec % 3600) / 60)
  const s = sec % 60
  return `${h}시간 ${m}분 ${s}초`
}

function formatMinutes(minutes) {
  const h = Math.floor(minutes / 60)
  const m = minutes % 60
  if (h===0) return `${m}분`
  if (m===0) return `${h}시간`
  return `${h}시간 ${m}분`
}

// flatpickr (달력)
watch(showGoalForm, (val) => {
  if (val) {
    setTimeout(() => {
      flatpickr("#start-date", {dateFormat: 'Y-m-d'})
      flatpickr("#end-date", {dateFormat: 'Y-m-d'})
    },50)
  }
})

onMounted(async () => {
  const res = await getSongById(route.params.id)
  song.value = res.data
  loadRecords()
  loadGoals()
})
</script>

<template>
  <div v-if="song" class="detail-card">
    <div class="detail-row">
      <span class="label">곡명</span>
      <span>{{song.title}}</span>
    </div>
    <div class="detail-row">
      <span class="label">아티스트</span>
      <span>{{song.artist}}</span>
    </div>
    <div class="detail-row">
      <span class="label">악기</span>
      <span>{{song.instrument}}</span>
    </div>
    <div class="detail-row">
      <span class="label">등록일</span>
      <span>{{song.createdDay?.slice(0, 10)}}</span>
    </div>

    <div class="divider"></div>

    <div class="section">
    <div class="detail-row">
      <span class="label">숙련도</span>
      <span class="value stars">{{ '★'.repeat(song.level) }}{{ '☆'.repeat(5 - song.level) }}</span>
    </div>
      <div class="level-desc">{{levelDesc[song.level]}}</div>
    </div>

    <div class="divider"></div>

    <div class="section">
      <p class="section-label">연습 기록</p>
      <table class="record-table">
        <thead>
          <tr>
            <th>시작</th>
            <th>종료</th>
            <th>연습 시간</th>
            <th>기능</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="records.length===0">
            <td colspan="4" class="empty">연습 기록이 없습니다.</td>
          </tr>

          <tr v-for="record in records" :key="record.id">
            <td>{{record.startTime?.slice(0, 16).replace('T', ' ')}}</td>
            <td>{{record.endTime?.slice(0, 16).replace('T', ' ')}}</td>
            <td>{{formatDuration(record.startTime, record.endTime)}}</td>
            <td>
              <button class="btn-sm btn-del" @click="removeRecord(record.id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="section">
      <div class="section-header">
        <p class="section-label">연습 목표</p>
        <button class="btn-add" @click="openAddGoal">+ 목표 등록</button>
      </div>
      <table class="record-table">
        <thead>
          <tr>
            <th>기간</th>
            <th>목표 내용</th>
            <th>목표 시간</th>
            <th>상태</th>
            <th>기능</th>
          </tr>
        </thead>
        <tbody>
          <tr v-if="goals.length===0">
            <td colspan="5" class="empty">등록된 목표가 없습니다.</td>
          </tr>
          <tr v-for="goal in goals" :key="goal.id">
            <td>{{goal.startDate}} ~ {{goal.endDate}}</td>
            <td>{{goal.goalDetail}}</td>
            <td>{{formatMinutes(goal.goalMinutes)}}</td>
            <td><span :class="'status-' + goal.status">{{goal.status}}</span></td>
            <td>
              <button class="btn-sm" @click="openEditGoal(goal)">수정</button>
              <button class="btn-sm btn-del" @click="removeGoal(goal.id)">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <div v-if="showGoalForm" class="modal-backdrop">
      <div class="modal">
        <h3>{{editGoal ? '목표 수정' : '목표 등록'}}</h3>
        <input type="text" id="start-date" v-model="goalForm.startDate" placeholder="시작일">
        <input type="text" id="end-date" v-model="goalForm.endDate" placeholder="종료일">
        <input v-model="goalForm.goalDetail" placeholder="목표 내용">
        <div class="time-input">
          <input type="number" v-model="goalHours" min="0" placeholder="0">시간
          <input type="number" v-model="goalMins" min="0" max="59" placeholder="0">분
        </div>
        <input v-model="goalForm.changeDetail" placeholder="개선할 내용 (선택)">
        <div class="modal-buttons">
          <button class="btn" @click="submitGoal">저장</button>
          <button class="btn-cancel" @click="showGoalForm = false">취소</button>
        </div>
      </div>
    </div>

    <button class="btn-back" @click="router.back()">목록으로</button>


  </div>
</template>

<style scoped>
.detail-card {
  background: #f9f9f9;
  border: 1px solid #E5E5E5;
  border-radius: 8px;
  padding: 24px;
  max-width: 900px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.record-table th:nth-child(1),
.record-table td:nth-child(1) { width: 200px; }

.record-table th:nth-child(2),
.record-table td:nth-child(2) { width: 160px; }

.record-table th:nth-child(3),
.record-table td:nth-child(3) { width: 160px; }

.record-table th:nth-child(4),
.record-table td:nth-child(4) { width: 70px; }


.detail-row {
  display: flex;
  gap: 16px;
  font-size: 14px;
  color: #444;
}
.section {
  display: flex;
  flex-direction: column;
  gap: 18px;
}
.section-label {
  font-size: 13px;
  color: #888888;
  margin-bottom: 12px;
}
.record-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}
.record-table th {
  text-align: left;
  padding: 10px 12px;
  border-bottom: 2px solid #E5E5E5;
  color: #888888;
  font-weight: 600;
}
.record-table td {
  padding: 12px;
  border-bottom: 1px solid #E5E5E5;
  color: #444444;
}
.empty {
  text-align: center;
  padding: 32px;
  color: #888888;
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
.btn-sm:hover {background: #f5f5f5}
.btn-del {
  color: #ff6b35;
  border-color: #ff6b35;
}
.btn-del:hover {background: #fff3ef}

.modal h3 {
  font-size: 16px;
  color: #444444;
}
.modal label {
  font-size: 13px;
  color: #888888;
}
.modal input {
  padding: 8px 12px;
  border: 1px solid #E5E5E5;
  border-radius: 6px;
  font-size: 14px;
  outline: none;
}
.modal input:focus {border-color: #20a9e8}

.label {
  width: 80px;
  color: #888888;
  font-weight: 600;
}
.btn-back {
  margin-top: 8px;
  background: none;
  border: 1px solid #E5E5E5;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  color: #444444;
  width: fit-content;
}
.btn-back:hover {
  background: #f5f5f5;
}

.value {
  color: #444444;
}

.stars {
  font-size: 18px;
  color: #20a9e8;
  letter-spacing: 2px;
}

.level-desc {
  margin-left: 106px;
  font-size: 13px;
  color: #888888;
}

.divider {
  border-top: 1px solid #E5E5E5;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}
.btn-add {
  background: none;
  color: #20a9e8;
  border: 1px solid #20a9e8;
  padding: 5px 12px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
}
.btn-add:hover {background: #f0f9ff;}

.status-진행전 {color: #888888;}
.status-진행중 {color: #20a9e8;}
.status-완료 {color: #52c41a;}
.status-미완료 {color: #ff6b35;}

.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.3);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
}

.modal {
  background: white;
  padding: 24px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  width: 360px;
}
.modal h3 {font-size: 16px;color: #444444;}
.modal input {
  border: 1px solid #E5E5E5;
  padding: 8px 12px;
  border-radius: 6px;
  font-size: 14px;
  outline: none;
}
.modal input:focus {border-color: #20a9e8;}
.modal-buttons {display: flex; gap: 8px;}
.btn {
  background: #20a9e8;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
}
.btn:hover {background: #1890c8;}
.btn-cancel {
  background: none;
  border: 1px solid #E5E5E5;
  padding: 8px 16px;
  border-radius: 6px;
  cursor: pointer;
  font-size: 14px;
  color: #444444;
}
.btn-cancel:hover {
  background: #f5f5f5;
}

.time-input {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #444444;
}
.time-input input {
  width: 60px;
}
</style>