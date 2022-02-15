<template>
  <v-app class="app">
    <tool-bar />
    <router-view />
  </v-app>
</template>

<script>
import ToolBar from "./components/layout/ToolBar.vue";

export default {
  name: "App",
  components: { ToolBar },
  data: () => ({}),
};
</script>

<style scoped>
/* font */
@import url("https://fonts.googleapis.com/css2?family=Gothic+A1:wght@300&display=swap");

* {
  font-family: "Gothic A1", sans-serif;
}
</style>
