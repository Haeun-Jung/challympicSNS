<template>
  <v-dialog v-model="confirmReportDialog" persistent max-width="450px">
    <v-card>
      <v-card-title>신고하기</v-card-title>
      <v-btn icon absolute top right @click="closeDialog">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <v-divider />
      <v-card-text class="mt-3">
        신고 수가 누적될 경우 관리자 검토 후 해당 글/댓글이 삭제될 수 있습니다.
        신고하시겠습니까?
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="closeDialog"> 취소 </v-btn>
        <v-btn color="red darken-1" text @click="reported"> 신고 </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "ConfirmReport",
  props: {
    confirmReportDialog: Boolean,
  },
  methods: {
    closeDialog() {
      this.$emit("close-dialog");
    },
    reported() {
      this.$emit("reported");
      this.$emit("close-dialog");
    },
  },
};
</script>

<style></style>
