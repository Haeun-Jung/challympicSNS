<template>
  <div class="container ma-auto">
    <v-form ref="form">
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <h1 class="left-align mb-5">로그인</h1>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-text-field
            v-model="email"
            :rules="emailRules"
            type="email"
            label="이메일"
            outlined
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-text-field
            v-model="password"
            :append-icon="pwShow ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="pwRules"
            :type="pwShow ? 'text' : 'password'"
            @click:append="pwShow = !pwShow"
            @keyup.enter="login"
            label="비밀번호"
            outlined
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="mt-0" justify="center">
        <v-col cols="10" sm="6" md="3" class="email-save-and-pw-find pa-0">
          <v-checkbox v-model="emailSave" label="이메일 저장"></v-checkbox>
          <v-btn @click="openPwDialog" class="pr-0" plain>비밀번호 찾기</v-btn>
        </v-col>
      </v-row>
      <v-row v-if="loginFailed" justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <p class="left-align red--text">사용자 정보가 일치하지 않습니다.</p>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-btn @click="login" color="#3396F4" class="col-12 mb-3" height="50">
            <span class="white--text">로그인</span>
          </v-btn>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-btn
            @click="clickJoinBtn"
            color="#3396F4"
            class="col-12"
            height="50"
            outlined
            >회원가입</v-btn
          >
        </v-col>
        <v-dialog v-model="dialog" max-width="600px">
          <password-find @close-dialog="closePwDialog"></password-find>
        </v-dialog>
      </v-row>
    </v-form>
  </div>
</template>

<script>
import { mapActions } from "vuex";
import PasswordFind from "@/components/login/PasswordFind.vue";

const userStore = "userStore";

export default {
  name: "Login",
  components: { PasswordFind },
  data() {
    return {
      dialog: false,
      email: this.$cookies.get("emailCookie"),
      password: "",
      pwShow: false,
      emailRules: [(v) => !!v || "이메일을 입력해주세요."],
      pwRules: [(v) => !!v || "비밀번호를 입력해주세요."],
      emailSave: false,
    };
  },
  computed: {
    loginFailed() {
      return this.$store.state.userStore.loginFailed;
    }
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    async login(event) {
      event.preventDefault();

      const validation = this.$refs.form.validate();
      if (!validation) {
        return;
      }

      if (this.emailSave) {
        this.$cookies.set("emailCookie", this.email);
      }

      await this.$store.dispatch('userStore/login', { user_email: this.email, user_pwd: this.password });
      this.getUserInfo(localStorage.getItem("Authorization"));
    },
    clickJoinBtn() {
      this.$router.push("/join");
    },
    openPwDialog() {
      this.dialog = true;
    },
    closePwDialog() {
      this.dialog = false;
    },
  },
};
</script>

<style scoped>
.left-align {
  text-align: left;
}
.email-save-and-pw-find {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 14px;
}
</style>