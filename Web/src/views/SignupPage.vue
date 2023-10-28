<template>
  <div class="signup">
    <picture>
      <img src="/TaskBuddy_logo.svg" alt="TaskBuddy logo" height="300" width="300" />
    </picture>

    <form class="login-form" @submit="submitSignup">
      <div class="input-box">
        <input type="text" name="username" id="username" v-model="formData.username" required />
        <label for="username">Username</label>
      </div>
      <div class="input-box">
        <input type="text" name="email" id="email" v-model="formData.email" required />
        <label for="email">Email</label>
      </div>
      <div class="input-box">
        <input type="text" name="phone" id="phone" v-model="formData.phone" required />
        <label for="phone">Phone</label>
      </div>
      <div class="input-box">
        <input type="password" name="password" id="password" v-model="formData.password" required />
        <label for="password">Password</label>
      </div>
      <div class="input-box">
        <input type="password" name="confirmPassword" id="confirmPassword" v-model="formData.confirmPassword" required />
        <label for="confirmPassword">Confirm Password</label>
      </div>
      <div class="input-box" style="margin-top: 10px;"> <!-- Added a margin for alignment -->
        <select name="gender" id="gender" v-model="formData.gender" required style="margin-bottom: 30px;">
          <option value="Male">Male</option>
          <option value="Female">Female</option>
          <option value="Other">Other</option>
        </select>
        <label for="gender" style="top: 0px;">gender</label>
      </div>
      <div class="input-box" style="margin-top: 10px;"> <!-- Added a margin for alignment -->
        <select name="user_type" id="user_type" v-model="formData.user_type" required style="margin-bottom: 30px;">
          <option value="Regular">Regular</option>
          <option value="Editor">Editor</option>
          <option value="Autism">Autism</option>
        </select>
        <label for="user_type" style="top: 0px;">User Type</label>
      </div>
      <div style="height: 20px;"></div>
      <button ref="submit_btn" type="submit">Sign Up</button>
    </form>

    <div class="signup-link">
      <p>Already have an account?</p>
      <router-link to="/login">Login</router-link>
    </div>
  </div>
</template>
  
<script lang="ts">
export default {
  data() {
    return {
      formData: {
        username: '',
        email: '',
        password: '',
        confirmPassword: '',
        phone: '',
        gender: 'Male',
        user_type: 'Regular'
      }
    };
  },
  methods: {
    submitSignup(event:Event) {
      event.preventDefault();

      if (this.formData.password !== this.formData.confirmPassword) {
          alert("Passwords do not match!");
          return;
      }

      const userData = new FormData();
      userData.append('username', this.formData.username);
      userData.append('email', this.formData.email);
      userData.append('password', this.formData.password);
      userData.append('phone', this.formData.phone);      
      userData.append('gender', this.formData.gender);
      userData.append('user_type', this.formData.user_type);

      fetch("http://localhost:8082/api/register", {
          method: "POST",
          body: userData
      })
      .then(response => response.json())
      .then(data => {
          if (data.success) {
              alert("Registration successful!");
          } else {
              alert(data.message || "Registration failed!");
          }
      })
      .catch(error => {
          console.error("Error:", error);
          alert("An error occurred during registration.");
      });
    }
  },
};
</script>
  
<style scoped>
.signup {
  background: var(--background-color);
  box-sizing: border-box;
  width: 100%;
  align-self: stretch;
  display: flex;
  justify-content: space-between;
  /* changed from center to space-between */
  align-items: center;
  flex-direction: column;
  padding: 1em 2em;
  max-height: 100vh;
  /* sets the maximum height to the view height */
  overflow-y: auto;
  /* adds a scrollbar if content overflows */
}

picture {
  display: flex;
  justify-content: center;
  margin-bottom: 1em;
  /* added some bottom margin */
}

.login-form {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 300px;
  margin-bottom: 1.5em;
  /* added some bottom margin */
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

.signup-link {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.signup-link p {
  font-size: 0.9em;
  margin: 0;
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

  
}</style>
  