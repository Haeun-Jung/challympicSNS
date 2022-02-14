<template>
  <v-card height="85vh" elevation="0">
    <v-card-title>문 의</v-card-title>
    <v-divider />
    <qn-a-table />
  </v-card>
</template>

<script>
import QnATable from "./util/QnATable.vue";
export default {
  name: "QnAManagement",
  components: { QnATable },
};
</script>

<style></style>
