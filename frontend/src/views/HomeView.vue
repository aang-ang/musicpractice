<script setup>
</script>
<template>
  <div>
    <p class="sub-text">최근 연습한 곡 목록이 여기에 표시</p>

    <table class="song-table">
      <thead>
        <tr>
          <th>곡명</th>
          <th>날짜</th>
          <th>시간</th>
          <th>숙련도</th>
        </tr>
      </thead>
      <tbody>
        <tr>
          <td colspan="4" class="empty">등록된 연습 기록이 없습니다.</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.sub-text {
  color: #888888;
  font-size: 14px;
  margin-bottom: 16px;
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
  color: #888888;
  padding: 32px;
}
</style>