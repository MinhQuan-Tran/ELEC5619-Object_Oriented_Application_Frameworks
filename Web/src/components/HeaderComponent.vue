<template>
  <header>
    <router-link to="/">
      <img src="/Image_20231029232417.jpg" alt="Home" height="60" width="60">
    </router-link>
    <router-link to="/emotion-records">Emotion Tracker</router-link>
    <router-link to="/emotionalrecord">Add Emotion</router-link>
    <router-link to="/forum">Community Forum</router-link>

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

header a {
  display: flex;
  align-items: center;
  justify-self: center;
  color: white;
}
</style>
