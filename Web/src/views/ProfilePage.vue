<template>
  <div class="container">
    <img class="profile-photo" src="../assets/profile-picture.jpg" alt="Profile Photo">
    <div class="details">
      <div class="user-details">
        <h2>Details</h2>
        <p><strong>Name:</strong> {{ formData.name }}</p>
        <p><strong>Gender:</strong> {{ formData.gender }}</p>
        <p><strong>Email:</strong> {{ formData.email }}</p>
        <p><strong>Phone:</strong> {{ formData.phone }}</p>
      </div>
      <div class="about-me">
        <h2>About Me</h2>
        <p>{{ formData.aboutMe }}</p>
      </div>
    </div>
    <div class="buttons">
      <button class="button edit-profile" @click="openModal">Edit Profile</button>
      <button class="button change-password" @click="changePassword">Change Password</button>
      <button class="button logout" @click="logout">Logout</button>
    </div>

    <!-- Edit Profile Modal -->
    <div v-if="showEditModal" class="modal">
      <div class="modal-content">
        <span @click="closeModal" class="close">&times;</span>
        <h2>Edit Profile</h2>
        <form @submit.prevent="updateProfile">
          <div class="form-group">
            <label for="name">Name:</label>
            <input type="text" id="name" v-model="formData.name">
          </div>
          <div class="form-group">
            <label for="gender">Gender:</label>
            <input type="text" id="gender" v-model="formData.gender">
          </div>
          <div class="form-group">
            <label for="email">Email:</label>
            <input type="email" id="email" v-model="formData.email">
          </div>
          <div class="form-group">
            <label for="phone">Phone:</label>
            <input type="tel" id="phone" v-model="formData.phone">
          </div>
          <div class="form-group">
            <label for="aboutMe">About Me:</label>
            <textarea id="aboutMe" v-model="formData.aboutMe"></textarea>
          </div>
          <button type="submit">Update</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { mapState } from 'vuex';

export default {
  data() {
    return {
      showEditModal: false,
      formData: {
        name: 'John Doe', // Temporary placeholder until the real data is fetched
        gender: 'Male',
        email: 'zz123@gmail.com',
        phone: '0423456788',
        aboutMe: "I'm a passionate software engineer with over 10 years of experience..."
      }
    };
  },
  methods: {
    openModal() {
      this.showEditModal = true;
    },
    closeModal() {
      this.showEditModal = false;
    },
    fetchUserData() {
      fetch(`/api/users/${this.uid}`, {
        headers: {
          'Authorization': `Bearer ${this.$cookies.get("auth_token")}`
        }
      })
      .then(response => response.json())
      .then(data => {
        if (data.code === 0) {
          this.formData = data.data.user;
        } else {
          console.log("Error fetching user data:", data.message);
        }
      });
    },
    updateProfile() {
      fetch(`/api/users/${this.uid}`, {
        method: 'PUT',
        headers: {
          'Authorization': `Bearer ${this.$cookies.get("auth_token")}`,
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(this.formData)
      })
      .then(response => response.json())
      .then(data => {
        if (data.code === 0) {
          this.formData = data.data.user;
          this.closeModal();
        } else {
          console.log("Error updating user data:", data.message);
        }
      });
    },
    changePassword() {
      // Change password logic remains unchanged
    },
    logout() {
      this.$cookies.remove("auth_token");
      this.$router.push({ name: "Login" });
    }
  },
  mounted() {
    this.fetchUserData();
  },
  computed: {
  ...mapState('user', {
    uid: state => state.user_id
  })
}
}
</script>

<style scoped>
h2 {
  text-align: center;
  color: #333;
  font-weight: 600;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 40px 20px;
  max-width: 700px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin: 50px auto;
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
  flex-direction: column;
  width: 100%;
  margin-bottom: 30px;
}

.user-details, .about-me {
  background-color: #ffffff;
  padding: 20px;
  border-radius: 5px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.buttons {
  display: flex;
  gap: 20px;
  width: 100%;
}

.button {
  flex: 1;
  padding: 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
  background-color: #007BFF;
  color: #ffffff;
  font-weight: 600;
}

.button:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}

/* Modal styles */
.modal {
  display: block;
  position: fixed;
  z-index: 10;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 10% auto;
  padding: 20px;
  border: 1px solid #888;
  border-radius: 10px;
  width: 80%;
  max-width: 500px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
  cursor: pointer;
}

.close:hover {
  color: black;
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
  color: #555;
  font-weight: 600;
}

input[type="text"], input[type="email"], input[type="tel"], textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button[type="submit"] {
  background-color: #007BFF;
  color: #ffffff;
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button[type="submit"]:hover {
  background-color: #0056b3;
  transform: translateY(-2px);
}
</style>
