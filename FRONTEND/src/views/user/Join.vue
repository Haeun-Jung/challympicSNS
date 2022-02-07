<template>
  <div class="my-sm-3 my-lg-auto">
    <v-form ref="form">
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <h1 class="mb-5">회원가입</h1>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="8" sm="4" md="2" class="pa-0">
          <v-text-field
            v-model="email"
            :rules="emailRules"
            :success-messages="emailSuccess"
            :disabled="duplicateEmailCheck && possibleEmail ? true : false"
            type="email"
            label="이메일"
            required
            outlined
          ></v-text-field>
        </v-col>
        <v-col cols="2" sm="2" md="1" class="pt-0 pr-0">
          <v-btn
            @click="checkEmail"
            color="#3396F4"
            class="col-12 mb-3"
            height="55"
          >
            <span class="join-btn-txt">중복 확인</span>
          </v-btn>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="8" sm="4" md="2" class="pa-0">
          <v-text-field
            v-model="nickname"
            :rules="nicknameRules"
            :success-messages="nicknameSuccess"
            :disabled="
              duplicateNicknameCheck && possibleNickname ? true : false
            "
            type="text"
            label="닉네임"
            maxlength="10"
            required
            outlined
          ></v-text-field>
        </v-col>
        <v-col cols="2" sm="2" md="1" class="pt-0 pr-0">
          <v-btn
            @click="checkNickname"
            color="#3396F4"
            class="col-12 mb-3"
            height="55"
          >
            <span class="join-btn-txt">중복 확인</span>
          </v-btn>
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
            label="비밀번호"
            maxlength="25"
            required
            outlined
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-text-field
            v-model="pwConfirmation"
            :append-icon="pwConfirmationShow ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="pwConfirmationRules"
            :type="pwConfirmationShow ? 'text' : 'password'"
            @click:append="pwConfirmationShow = !pwConfirmationShow"
            label="비밀번호 확인"
            maxlength="25"
            required
            outlined
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="mt-0" justify="center">
        <v-col cols="10" sm="6" md="3">
          <v-checkbox
            v-model="personalDataConsent"
            :rules="personalDataConsentRules"
            label="개인정보 수집•이용에 동의합니다."
          ></v-checkbox>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-col cols="10" sm="6" md="3" class="pa-0">
          <v-btn @click="join" color="#3396F4" class="col-12 mb-3" height="50">
            <span class="join-btn-txt">회원가입</span>
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>
export default {
  name: "Join",
  data() {
    return {
      email: "",
      duplicateEmailCheck: false,
      possibleEmail: false,
      nickname: "",
      duplicateNicknameCheck: false,
      possibleNickname: false,
      password: "",
      pwShow: false,
      pwConfirmation: "",
      pwConfirmationShow: false,
      personalDataConsent: false,

      pwRules: [
        (v) => !!v || "비밀번호를 입력해주세요.",
        (v) =>
          /^(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/.test(v) ||
          "영문,숫자,특수문자를 조합하여 8~25자로 입력해주세요.",
      ],
      pwConfirmationRules: [
        (v) => v === this.password || "비밀번호가 일치하지 않습니다.",
      ],
      personalDataConsentRules: [
        (v) =>
          (v && this.personalDataConsent) || "개인정보 약관에 동의해주세요.",
      ],
    };
  },
  computed: {
    emailRules() {
      return [
        (v) => !!v || "이메일을 입력해주세요.",
        (v) => /.+@.+/.test(v) || "올바른 이메일 형식이 아닙니다.",
        this.duplicateEmailCheck || "중복 여부를 확인해주세요.",
        this.possibleEmail || "사용할 수 없는 이메일입니다.",
      ];
    },
    nicknameRules() {
      return [
        (v) => !!v || "닉네임을 입력해주세요.",
        (v) =>
          /^[가-힣a-zA-Z0-9].{1,10}$/.test(v) ||
          "한글/영문/숫자를 이용하여 2~10자로 입력해주세요.",
        this.duplicateNicknameCheck || "중복 여부를 확인해주세요.",
        this.possibleNickname || "사용할 수 없는 닉네임입니다.",
      ];
    },
    emailSuccess() {
      if (!this.duplicateEmailCheck || !this.possibleEmail) {
        return;
      }
      return "사용 가능한 이메일입니다.";
    },
    nicknameSuccess() {
      if (!this.duplicateNicknameCheck || !this.possibleNickname) {
        return;
      }
      return "사용 가능한 닉네임입니다.";
    },
  },
  methods: {
    checkEmail() {
      // 이메일 중복 확인 API 요청
      this.duplicateEmailCheck = true;
      // 사용 가능한 이메일일 경우
      // this.possibleEmail = true;
      // 중복된 이메일일 경우
      this.possibleEmail = false;
    },
    checkNickname() {
      // 닉네임 중복 확인 API 요청
      this.duplicateNicknameCheck = true;
      // 사용 가능한 닉네임일 경우
      // this.possibleNickname = true;
      // 중복된 닉네임일 경우
      this.possibleNickname = false;
    },
    join() {
      const validation = this.$refs.form.validate();
      if (!validation) {
        return;
      }
      // 회원가입 API 요청
    },
  },
};
</script>

<style scoped>
.theme--light.v-input--is-disabled {
  color: #3396f4;
}
.join-btn-txt {
  color: rgb(255, 255, 255);
}
</style>