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
      path: '/Profile',
      name: 'Profile',
      component: () => import('@/views/ProfilePage.vue')
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
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Check if the route requires authentication
  if (to.matched.some((route) => route.meta.requiresAuth)) {
    // Check if the user is authenticated
    if (window.$cookies.isKey('auth_token')) {
      // Check if the user info is already loaded
      if (store.state.user.user_id === null) {
        // User info is not loaded
        fetch(`${import.meta.env.VITE_ROOT_API}/users/me`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
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
            store.commit('user/setUser', data.data)
          })
          .catch((error) => {
            console.error(error)
            window.$cookies.remove('auth_token')
            next('/login')
          })
      }
      next() // User is authenticated, proceed to the route
    } else {
      // User is not authenticated, redirect to the login page
      console.log('here')
      next('/login')
    }
  } else {
    // If the route does not require authentication, proceed as usual
    next()
  }
})

export default router
