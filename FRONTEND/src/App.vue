<template>
  <v-app class="app">
    <tool-bar />
      <fab-button />
    <router-view />
  </v-app>
</template>

<script>
import ToolBar from "./components/layout/ToolBar.vue";
import FabButton from "@/components/button/FabButton.vue";

export default {
  name: "App",
  components: { ToolBar, FabButton },
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
