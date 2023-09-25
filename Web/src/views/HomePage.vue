<script lang="ts">
export default {
  data() {
    return {

    };
  },
  methods: {
  }
}
</script>

<template>
  <div class="home">
    <div class="greeting">
      <span>
        Welcome to TaskBuddy!
      </span>
      <span class="username">
        <slot>Please login or register to continue.</slot>
      </span>
    </div>

    <main>

    </main>
  </div>
</template>

<style scoped>
.home {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
  min-height: 100%;
  padding: 15px;
  background: url(../assets/background.jpg) no-repeat center center fixed;
  background-size: cover;
}

.greeting {
  align-self: flex-start;
  text-align: left;
  color: white;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  font-size: 1.25em;
}

.username {
  font-weight: bold;
}

main {
  box-sizing: border-box;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  gap: 10px;
}

main>* {
  background: var(--secondary);
  border-radius: 10px;
  padding: 10px;
  box-sizing: border-box;
  width: 100%;
}

.group-card {
  text-align: left;
  color: initial;
}

.group-name {
  font-weight: bold;
}

.group-members {
  opacity: 0.7;
}
</style>
