<template>
  <v-card height="85vh" elevation="0">
    <v-card-title>댓글 관리</v-card-title>
    <v-divider />
    <comment-table />
  </v-card>
</template>

<script>
import CommentTable from "@/components/admin/util/CommentTable.vue";
export default {
  name: "CommentManagement",
  components: { CommentTable },
};
</script>

<style></style>
