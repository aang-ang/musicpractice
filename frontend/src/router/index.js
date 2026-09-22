import { createRouter, createWebHistory } from 'vue-router'
import HomeView from "@/views/HomeView.vue";
import SongView from "@/views/SongView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
      { path: '/', component: HomeView, meta: {title: '홈'}},
      { path: '/songs', component: SongView, meta: {title: '곡 관리'}},
      { path: '/songs/id', component: SongDetailView, meta: {title: '곡 상세'}}
  ],
})

export default router
