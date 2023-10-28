<script lang="ts">
import HeaderComponent from "@/components/HeaderComponent.vue";

export default {
  components: {
    HeaderComponent,
  },
  data() {
    return {
      taskName: "TaskBuddy"
    }
  },
  computed: {
    pageName() {
      if (this.$route.name === "Task") {
        return this.taskName;
      }
      return this.$route.name;
    }
  },
  mounted() {
  }
};
</script>

<template>
  <HeaderComponent>{{ pageName }}</HeaderComponent>
  <router-view class="router-view" @pageName="(name: string) => taskName = name"></router-view>
</template>

<style scoped>
.router-view {
  height: 100%;
}

@media (prefers-color-scheme: dark) {}
</style>
