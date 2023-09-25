import { createRouter, createWebHistory } from 'vue-router'

import HomePage from '@/views/HomePage.vue'
import Signup from '@/views/Signup.vue' 
import ResetPassword from '@/views/ResetPassword.vue' 

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
      path: '/signup',
      name: 'Signup',
      component: Signup  // <-- Add this new route
    },
    {
      path: '/resetpassword',
      name: 'ResetPassword',
      component: ResetPassword  // <-- Add this new route
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/LoginPage.vue')
    }
  ]
})

export default router
