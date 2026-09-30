<script setup>
  import { useRoute } from 'vue-router'

  const route = useRoute()
</script>

<template>
  <div class="layout">
    <aside class="sidebar">
      <div class="sidebar-title">MusicPractice</div>
      <nav>
        <router-link to="/">홈</router-link>
        <router-link to="/songs">곡 관리</router-link>
        <router-link to="/practice">연습하기</router-link>
        <router-link to="/stats">통계</router-link>
      </nav>
    </aside>

    <main class="content">
      <h2 class="page-title">
        {{route.meta.title}}
      </h2>
      <router-view />
    </main>
  </div>
</template>

<style scoped>
  * {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
  }
  html, body {
    height: 100%;
    overflow: hidden;
    background: #fff;
    color: #444444;
  }
  .layout {
    display: flex;
    height: 100vh;
    overflow: hidden;
  }

  .sidebar {
    width: 200px;
    background: #20A9E8;
    color: white;
    display: flex;
    flex-direction: column;
    padding: 24px 16px;
    gap: 8px;
    flex-shrink: 0;
  }
  .sidebar-title {
    font-size: 16px;
    font-weight: bold;
    margin-bottom: 24px;
  }
  .sidebar nav {
    display: flex;
    flex-direction: column;
    gap: 4px;
  }
  .sidebar nav a {
    color: white;
    text-decoration: none;
    padding: 10px 12px;
    border-radius: 6px;
    font-size: 14px;
  }
  .sidebar nav a:hover,
  .sidebar nav a.router-link-active {
    background: rgba(255, 255, 255, 0.2);
  }

  .content {
    flex: 1;
    padding: 32px 40px;
  }
  .page-title {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 24px;
    color: #444444;
  }
</style>
