<template>
  <div class="container">
    <img class="profile-photo" src="../assets/profile-picture.jpg" alt="Profile Photo">
    <div class="details">
      <div class="user-details">
        <h2>Details</h2>
        <p><strong>Name:</strong> John Doe</p>
        <p><strong>Age:</strong> 24</p>
        <p><strong>Location:</strong> Mascot, NSW</p>
        <p><strong>Email:</strong> zz123@gmail.com</p>
        <p><strong>Phone:</strong> 0423456788</p>
      </div>
      <div class="about-me">
        <h2>About Me</h2>
        <p>I'm a passionate software engineer with over 10 years of experience. I believe in continuous learning and
          always strive to improve my skills and expertise.</p>
      </div>
    </div>
    <div class="buttons">
      <button class="button edit-profile" @click="editProfile">Edit Profile</button>
      <button class="button change-password" @click="changePassword">Change Password</button>
      <button class="button logout" @click="logout">Logout</button>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Profile',
  methods: {
    editProfile() {
      console.log('Editing profile...');
      // ... your code for editing profile
    },
    changePassword() {
      console.log('Changing password...');
      // ... your code for changing password
    },
    logout() {
      console.log('Logging out...');
      // ... your code for logging out
      this.$cookies.remove("auth_token");
      this.$router.push({ name: "Login" });
    }
  }
}
</script>

<style scoped>
h2 {
  text-align: center;
}

.profile {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url('../assets/van-gogh.jpg');
  background-size: cover;
  height: 100vh;
}

.user-details {
  text-align: left;
}

.about-me {
  width: 100%;
  max-width: 40%;
  /* Adjust this value to make the text narrower or wider */
  text-align: justify;
  word-wrap: break-word;
  margin-left: 22%;
  margin-right: auto;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 10px;
  max-width: 700px;
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: 10px;
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
  margin: 0 auto;
}

.profile-photo {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 30px;
}

.details {
  display: flex;
  justify-content: space-between;
  width: 70%;
  margin-bottom: 30px;
}


.buttons {
  display: flex;
  gap: 20px;
}

.button {
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.button:hover {
  background-color: #ddd;
}
</style>
