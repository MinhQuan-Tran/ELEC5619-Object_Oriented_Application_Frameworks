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
  padding: 10px;
  height: 4em;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: larger;
  background: #f5f5f5;  /* Light grey background */
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
}

nav ul {
  list-style: none;
  padding: 0;
  display: flex;
  gap: 15px;
}

nav ul li {
  display: inline-block;
}

router-link {
  text-decoration: none;
  color: #333;
  padding: 5px 10px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

router-link:hover {
  background-color: #ddd;  /* Slight darkening on hover */
}

header a {
  display: flex;
  align-items: center;
  justify-self: center;
  color: white;
}
</style>
