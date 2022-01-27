<template>
  <v-app>
      <router-view />
      <fab-button />
  </v-app>
</template>

<script>
import FabButton from "./components/button/FabButton.vue";
export default {
  name: "App",
  components : {FabButton}
};
</script>
