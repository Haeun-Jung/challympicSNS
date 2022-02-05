<template>
  <v-main>
    <tool-bar />
    <tag-bar />
    <side />
    <fab-button />
    <!-- 리스트 공간
  -->
  </v-main>
</template>

<script>
import ToolBar from "../components/layout/ToolBar.vue";
import TagBar from "../components/layout/TagBar.vue";
import Side from "../components/layout/Side.vue";
import FabButton from "../components/button/FabButton.vue";
export default {
  components: { ToolBar, TagBar, Side, FabButton },
};
</script>

<style></style>
