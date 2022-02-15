<template>
  <v-card
    height="90vh"
    width="100%"
    class="justify-center mobile-profile-delete-user-container"
  >
    <v-card-title class="justify-center">푸쉬 알림 </v-card-title>
    <v-divider />
    <v-container class="mobile-profile-change-user-password-container">
      <v-row>
        <v-col md="1" />
      </v-row>
      <v-row>
        <v-col md="2">
          <v-list-item-title class="pt-5">알림 허용</v-list-item-title>
        </v-col>
        <v-spacer />
        <v-col>
          <v-switch v-model="switch1" inset></v-switch>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
export default {
  name: "PasswordUpdate",
  data() {
    return {
      switch1: true,
    };
  },
  methods: {},
};
</script>

<style></style>
