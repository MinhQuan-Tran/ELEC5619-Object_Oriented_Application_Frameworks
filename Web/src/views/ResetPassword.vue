<template>
  <div class="reset-password">
    <picture>
      <img src="/TaskBuddy_logo.svg" alt="TaskBuddy logo" height="300" width="300" />
    </picture>

    <form class="reset-form" @submit="submitResetRequest">
      <div class="input-box">
        <input type="text" name="email" id="email" v-model="formData.email" required />
        <label for="email">Email</label>
      </div>
      <button ref="submit_btn" type="submit">Request Password Reset</button>
    </form>

    <div class="back-link">
      <router-link to="/login">Back to Login</router-link>
    </div>
  </div>
</template>
  
<script lang="ts">
export default {
  data() {
    return {
      formData: {
        email: ''
      }
    };
  },
  methods: {
    submitResetRequest(event: Event) {
      event.preventDefault();
      // Logic to send the reset password request to your backend server.
    }
  },
};
</script>
  
<style scoped>
.reset-password {
  background: var(--background-color);
  box-sizing: border-box;
  width: 100%;
  align-self: stretch;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
  padding: 2em;
}

picture {
  display: flex;
  justify-content: center;
}

.reset-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 300px;
}

.input-box {
  position: relative;
  width: 100%;
}

.input-box input {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: none;
  border-bottom: 1px solid black;
  outline: none;
  margin-bottom: 1.5em;
}

.input-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px;
  pointer-events: none;
  transition: all 0.3s ease-in-out;
}

.input-box input:focus~label,
.input-box input:valid~label {
  font-size: 0.8em;
  transform: translate(-5px, calc(-1 * (1em + 10px + 5px)));
}

.reset-form button {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  border: none;
  outline: none;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 1.5em;
  transition: all 0.3s ease-in-out;
}

.reset-form button:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
}

.back-link {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: large;
  text-align: center;
}

@media (prefers-color-scheme: dark) {
  .input-box input {
    border-bottom: 1px solid white;
  }

  .input-box input:focus {
    border-bottom: 1px solid yellow;
  }

  .input-box input:focus~label,
  .input-box input:valid~label {
    color: var(--primary-color);
  }
}
</style>
  