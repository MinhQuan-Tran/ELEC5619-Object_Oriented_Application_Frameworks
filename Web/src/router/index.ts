import { createRouter, createWebHistory } from 'vue-router'

import HomePage from '@/views/HomePage.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: { path: '/login' }
    },
    {
      path: '/home',
      name: 'Home',
      component: HomePage,
      meta: { requiresAuth: true }
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/LoginPage.vue')
    }
  ]
})

export default router
