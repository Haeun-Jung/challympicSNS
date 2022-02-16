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
  mounted() {
    const theme = localStorage.getItem("dark_theme");
    if (theme) {
      if (theme === "true") {
        this.$vuetify.theme.dark = true;
        this.darkSwitch = true;
      } else {
        this.$vuetify.theme.dark = false;
        this.darkSwitch = false;
      }
    } else if (
      window.matchMedia &&
      window.matchMedia("(prefers-color-scheme: dark)").matches
    ) {
      this.$vuetify.theme.dark = true;
      this.darkSwitch = true;
      localStorage.setItem("dark_theme", this.$vuetify.theme.dark.toString());
    }
  },
};
</script>

<style scoped>
/* font */
@import url("https://fonts.googleapis.com/css2?family=Gothic+A1:wght@300&display=swap");

* {
  font-family: "Gothic A1", sans-serif;
}
</style>
