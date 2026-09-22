<script setup>
import {ref,onMounted} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {getSongById} from '../api/song'

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

onMounted(async () => {
  const res = await getSongById(route.params.id)
  song.value = res.data
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
    <button class="btn-back" @click="router.back()">목록으로</button>
  </div>
</template>

<style scoped>
.detail-card {
  background: #f9f9f9;
  border: 1px solid #E5E5E5;
  border-radius: 8px;
  padding: 24px;
  max-width: 480px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}
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


</style>