<template>
  <v-card height="85vh" elevation="0">
    <v-card-title>회원 관리</v-card-title>
    <v-divider />
    <user-table />
  </v-card>
</template>

<script>
import UserTable from "@/components/admin/util/UserTable.vue";
export default {
  name: "UserManagement",
  components: { UserTable },
};
</script>

<style></style>
