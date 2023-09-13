<script lang="ts">
export default {
  data() {
    return {
      formData: {
        email: '',
        password: '',
        // Add other form fields here
      }
    };
  },
  methods: {
    submitPasswordLogin(event: Event) {
      event.preventDefault();

      fetch('https://localhost:7163/api/Account/PasswordLogin', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          email: this.formData.email,
          password: this.formData.password
        })
      })
        .then(response => response.json())
        .then(data => {
          console.log(data);
        })
        .catch(error => {
          console.error(error);
        });
    }
  },
  mounted() {
  },
};
</script>

<template>
  <div class="login">
    <picture>
      <!-- <source srcset="/TaskBuddy_logo_dark.svg" media="(prefers-color-scheme: dark)" /> -->
      <img src="/TaskBuddy_logo.svg" alt="TaskBuddy logo" height="300" width="300" />
    </picture>

    <form class="login-form" @submit="submitPasswordLogin">
      <div class="input-box">
        <input type="text" name="email" id="email" v-model="formData.email" required />
        <label for="username">Email</label>
      </div>
      <div class="input-box">
        <input type="password" name="password" id="password" v-model="formData.password" required />
        <label for="password">Password</label>
      </div>
      <button ref="submit_btn" type="submit">Login</button>
    </form>

    <div id="google_btn">
    </div>

    <div class="signup">
      <p>New to TaskBuddy?</p>
      <router-link to="/signup">Sign up</router-link>
    </div>
  </div>
</template>

<style scoped>
.login {
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

.login-form {
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
  /* font size + padding + extra space */
}

.login-form button {
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

.login-form button:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.2);
}

.signup {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-size: large;
  text-align: center;
}

.signup p {
  margin-bottom: 0.2em;
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
