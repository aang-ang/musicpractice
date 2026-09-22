<script setup>
import {ref,onMounted} from 'vue'
import {useRoute, useRouter} from "vue-router";
import {getSongById} from '../api/song'

const route = useRoute()
const router = useRouter()
const song = ref(null)

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
      <span class="label">숙련도</span>
      <span>{{ '★'.repeat(song.level) }}{{ '☆'.repeat(5 - song.level) }}</span>
    </div>
    <div class="detail-row">
      <span class="label">등록일</span>
      <span>{{song.createdDay?.slice(0, 10)}}</span>
    </div>

    <button class="btn-back" @click="router.back()"><- 목록으로</button>
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
</style>