<template>
  <div class="emotion-container" :key="componentKey">
    <h2>Your Emotion Records</h2>
    <table class="emotion-table">
      <thead>
        <tr>
          <th>Date</th>
          <th>Emotion</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="record in emotionRecords" :key="record.eid">
          <td>{{ record.emotionRecordDate }}</td>
          <td>{{ record.emotion }}</td>
          <td>
            <button @click="modifyRecord(record)" class="modify-button">Modify</button>
            <button @click="deleteRecord(record.eid)" class="delete-button">Delete</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Edit Modal -->
    <div v-if="isEditing" class="modal">
      <div class="modal-content">
        <h3>Edit Emotion Record</h3>
        <label>Emotion:</label>
        <input v-model="editingRecord.emotion" type="text">
        <label>Record Date:</label>
        <input v-model="editingRecord.emotionRecordDate" type="text">
        <button @click="saveEdits">Save</button>
        <button @click="isEditing = false">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
  data() {
    return {
      emotionRecords: [],
      isEditing: false,
      editingRecord: {},
      componentKey: 0
    };
  },
  computed: {
    ...mapState({
      uid: state => state.user.user_id
    })
  },
  mounted() {
    this.fetchEmotionRecords();
  },
  methods: {
    async fetchEmotionRecords() {
      try {
        const response = await fetch(`http://13.236.138.98:8082/api/emotion/getAllByUid/${this.uid}`);
        const data = await response.json();
        if (Array.isArray(data)) {
          this.emotionRecords = data;
        } else {
          console.error("Error fetching emotion records:", "Response format is not an array.");
        }
      } catch (error) {
        console.error("Error fetching emotion records:", error);
      }
    },
    modifyRecord(record) {
      this.editingRecord = { ...record };
      this.isEditing = true;
    },
    async saveEdits() {
      try {
        const response = await fetch(`http://13.236.138.98:8082/api/emotion/update`, {
          method: "PUT",
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(this.editingRecord)
        });
        const data = await response.json();
        location.reload();

        if (data.success) {
          const index = this.emotionRecords.findIndex(record => record.eid === this.editingRecord.eid);
          if (index !== -1) {
            location.reload();
          }
          this.isEditing = false;
          this.componentKey += 1;  // Trigger a re-render
        } else {
          console.error("Error updating emotion record:", data.message);
        }
      } catch (error) {
        console.error("Error updating emotion record:", error);
      }
    },
    async deleteRecord(eid) {

      try {
        const response = await fetch(`http://13.236.138.98:8082/api/emotion/delete/${eid}`, {
          method: "DELETE"
        });
        const data = await response.json();

        if (data.success) {
          location.reload();
        } else {
          console.error("Error deleting emotion record:", data.message);
        }
      } catch (error) {
        console.error("Error deleting emotion record:", error);
      }
      location.reload();

    }
  }
};
</script>

<style scoped>
.emotion-container {
  width: 80%;
  margin: 0 auto;
  padding: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.emotion-table {
  width: 100%;
  border-collapse: collapse;
}

.emotion-table th,
.emotion-table td {
  border: 1px solid #ddd;
  padding: 8px 12px;
  text-align: left;
}

.emotion-table th {
  background-color: #f2f2f2;
}

.modify-button {
  background-color: #4CAF50;
  color: white;
  padding: 8px 12px;
  border: none;
  cursor: pointer;
}

.delete-button {
  background-color: #f44336;
  color: white;
  padding: 8px 12px;
  border: none;
  cursor: pointer;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 400px;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.modal-content input {
  width: 100%;
  padding: 8px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.modal-content button {
  margin: 10px 5px;
  padding: 8px 12px;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

.modal-content button:hover {
  opacity: 0.9;
}
</style>
