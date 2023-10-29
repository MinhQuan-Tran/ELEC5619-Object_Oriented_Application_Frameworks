import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store'

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
      path: '/signup',
      name: 'Signup',
      component: () => import('@/views/SignupPage.vue')
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/LoginPage.vue')
    },
    {
      path: '/forgot-password',
      name: 'ForgotPassword',
      component: () => import('@/views/ForgotPasswordPage.vue')
    },
    {
      // add "/" to the end of the path to prevent 404 error due to the dot in the token
      path: '/reset-password/:resetPasswordToken/',
      name: 'ResetPassword',
      component: () => import('@/views/ResetPasswordPage.vue')
    },
    {
      path: '/emotionalrecord',
      name: 'EmotionalRecord',
      component: () => import('@/views/EmotionalRecordPage.vue')
    },
    {
      path: '/emotion-records',
      name: 'EmotionRecords',
      component: () => import('@/views/EmotionRecordsPage.vue')
    },
    {
      path: '/forum',
      name: 'Forum',
      component: () => import('@/views/ForumPage.vue')
    },
    {
      path: '/forum/posts/:postId',
      name: 'ForumDetail',
      component: () => import('@/views/ForumDetailPage.vue'),
      meta: { requiresAuth: true }
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  // Check if the user is authenticated
  if (window.$cookies.isKey('auth_token')) {
    console.log('has auth_token')
    // Check if the user info is already loaded
    if (store.state.user.user_id == null) {
      console.log('user_id is null')
      // User info is not loaded
      await fetch(`${import.meta.env.VITE_ROOT_API}/users/me`, {
        method: 'GET',
        headers: {
          'X-CSRFToken': window.$cookies.get('csrftoken'),
          Authorization: window.$cookies.get('auth_token')
        }
      })
        .then((response) => {
          if (!response.ok) {
            return response.json().then((data) => {
              throw new Error(data.message)
            })
          }
          if (response.headers.get('Authorization') !== null) {
            window.$cookies.set('auth_token', response.headers.get('Authorization'))
          }

          return response.json()
        })
        .then((data) => {
          console.log(data)
          store.commit('user/setUser', data.data.user)
        })
        .catch((error) => {
          console.error(error)
          window.$cookies.remove('auth_token')
        })
    }
    // Check if the route requires authentication
    if (to.matched.some((route) => route.meta.requiresAuth)) {
      console.log(
        'requiresAuth ' + window.$cookies.isKey('auth_token') + ' ' + store.state.user.user_id
      )
      // the route requires authentication, check if the user is authenticated
      if (window.$cookies.isKey('auth_token') && store.state.user.user_id !== null) {
        // the user is authenticated, proceed as usual
        next()
      } else {
        // the user is not authenticated, redirect to login
        next({ path: '/login' })
      }
    } else {
      // The route does not require authentication, proceed as usual
      next()
    }
  } else {
    // If the route does not require authentication, proceed as usual
    next()
  }
})

export default router
