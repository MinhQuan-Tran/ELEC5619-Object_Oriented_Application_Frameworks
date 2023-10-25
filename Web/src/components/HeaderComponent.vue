<template>
  <header>
    <router-link to="/text-input">Text Input</router-link>
    <router-link to="/home">Home</router-link>
    <router-link to="/signup">Signup</router-link>
    <router-link to="/login">Login</router-link>
    <router-link to="/forgot-password">Forgot Password</router-link>
    <router-link to="/emotion-record">Emotion Record</router-link>
  </header>
</template>

<style scoped>
header {
  position: relative;
  left: -1px;
  right: -1px;
  width: auto;
  padding: 10px;
  height: 2em;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: xx-large;
  background: rgba(var(--primary-color));
  /* box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.5); */
}
</style>
