<script lang="ts">
export default {
  data() {
    return {
      formData: {
        emailOrPhone: '',
        password: '',
        // Add other form fields here
      }
    };
  },
  methods: {
    submitPasswordLogin(event: Event): void {
      event.preventDefault();

      fetch(`${import.meta.env.VITE_ROOT_API}/login`, {
        method: 'POST',
        body: new FormData(this.$refs.loginForm as HTMLFormElement)
      })
        .then(response => {
          if (!response.ok) {
            return response.json().then(data => {
              throw new Error(data.message);
            });
          }
          return response.json();
        })
        .then(data => {
          console.log(data);

          this.$refs.loginBtn.setAttribute("data-status", "success");
          this.$refs.loginBtn.innerText = data.message;

          this.$store.commit("user/setUser", data.data);
          this.$cookies.set("auth_token", data.auth_token);

          setTimeout(() => this.$router.push({ name: "Home" }), 2000);
        })
        .catch(error => {
          console.error(error);

          this.$refs.loginBtn.setAttribute("data-status", "error");
          this.$refs.loginBtn.innerText = error + "\n\nClick here to retry";
          this.$refs.loginBtn.disabled = false;
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

    <form ref="loginForm" class="login-form" @submit="submitPasswordLogin">
      <div class="input-box">
        <input type="text" name="emailOrPhone" id="emailOrPhone" v-model="formData.emailOrPhone" required />
        <label for="username">Email</label>
      </div>
      <div class="input-box">
        <input type="password" name="password" id="password" v-model="formData.password" required />
        <label for="password">Password</label>
      </div>
      <button ref="loginBtn" type="submit">Login</button>
    </form>

    <div class="signup">
      <p>New to TaskBuddy?</p>
      <router-link to="/signup">Sign up</router-link>
    </div>
  </div>
</template>

<style scoped>
.login {
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
