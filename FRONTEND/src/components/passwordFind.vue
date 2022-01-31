<template>
  <v-card class="pa-1">
    <v-container>
      <v-card-title class="text-h5 title-text">비밀번호 찾기</v-card-title>
      <v-spacer></v-spacer>
      <v-btn icon absolute top right @click="closeDialog">
        <v-icon>mdi-close</v-icon>
      </v-btn>
    </v-container>
    <v-card-text>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="email"
              label="이메일"
              :rules="emailRules"
              outlined
              required
            ></v-text-field>
          </v-col>
          <v-col v-if="isSended" cols="12" class="py-0">
            <span class="send-text">임시 비밀번호가 전송되었습니다.</span>
          </v-col>
          <v-col cols="12">
            <v-btn
              @click="sendEmail"
              color="#3396F4"
              class="col-12"
              height="50"
            >
              <span class="white--text">이메일 전송</span>
            </v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  name: "passwordFind",
  data() {
    return {
      dialog: true,
      email: "",
      isSended: false,
      emailRules: [
        (v) => !!v || "이메일을 입력해주세요.",
        (v) => /.+@.+/.test(v) || "올바른 이메일 형식이 아닙니다.",
      ],
    };
  },
  methods: {
    closeDialog() {
      this.email = "";
      this.isSended = false;
      this.$emit("close-dialog");
    },
    sendEmail() {
      if (!this.email || !/.+@.+/.test(this.email)) {
        return;
      } else {
        this.isSended = true;
      }
    },
  },
};
</script>

<style scoped>
.title-text {
  color: #3396f4;
  font-weight: bold;
}
.send-text {
  color: #3396f4;
}
</style>