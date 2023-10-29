<template>
  <div class="emotion-recorder-wrapper">
    <div class="emotion-recorder-container">
      <!-- Title -->
      <h2>Record Your Emotion</h2>
  
      <!-- Form for recording emotion -->
      <form @submit.prevent="submitEmotion">
        <!-- Input for emotion -->
        <div>
          <input type="text" id="emotion" v-model="emotion" placeholder="emotion" class="centered-input" maxlength="255" required>
        </div>
  
        <!-- Submit button -->
        <button type="submit" class="centered-button">Submit</button>
      </form>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
data() {
  return {
    emotion: ''  // This will capture the selected emotion
  };
},
computed: {
  ...mapState({
    uid: state => state.user.user_id
  })
},
methods: {
  async submitEmotion() {
    try {
      // Get current date
      const currentDate = new Date();

      // Construct the DTO object
      const emotionRecordDTO = {
        emotion: this.emotion,
        uid: this.uid,
        emotionRecordDate: currentDate.toISOString()  // Convert to ISO string format
      };

      // Specify the complete URL for the backend endpoint
      const backendUrl = "http://13.236.138.98:8082/api/emotion/add";

      // Make API call to add emotion record
      const response = await fetch(backendUrl, {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(emotionRecordDTO)
      });

      if (!response.ok) {
        throw new Error("Error recording emotion");
      }

      const responseData = await response.json();
      // Handle the response data as needed

    } catch (error) {
      console.error("Error recording emotion:", error);
    }
  }
}
};
</script>

<style scoped>
.emotion-recorder-wrapper {
display: flex;
justify-content: center;
align-items: center;
height: 100vh;
}

.emotion-recorder-container {
display: flex;
flex-direction: column;
align-items: center;
gap: 20px;
box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
padding: 30px;
border-radius: 5px;
}

.centered-input {
width: 500px; /* Increased width */
padding: 15px; /* Increased padding */
font-size: 2em; /* Increased font size */
text-align: center;
}

.centered-button {
padding: 10px 20px; /* Increased padding */
font-size: 1.2em; /* Increased font size */
margin-top: 20px;
}
</style>
