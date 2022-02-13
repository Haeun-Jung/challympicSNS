<template>
  <!-- POST 등록 모달 -->
  <v-dialog v-model="dialog" persistent max-width="600px">
    <v-card>
      <v-card-title class="card-header">
        <v-spacer></v-spacer>
        <v-btn
          class="cancel-btn"
          icon
          @click="
            $emit('close-modal');
            $store.commit('challengeStore/RESET_POSSIBLE_STATUS');
          "
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>
      <v-card-text>
        <v-row class="row-area">
          <v-col class="input-title"># 챌린지 제목(예: 미라클_모닝)</v-col>
        </v-row>
        <v-row>
          <v-col>
            <div v-if="$vuetify.theme.dark">
              <input
                v-model="challenge.challengeName"
                class="dark-mode input-box challenge-input"
                type="text"
                :disabled="possibleChallengeName ? true : false"
              />
            </div>
            <div v-else class="input-group suffix">
              <input
                v-model="challenge.challengeName"
                class="light-mode input-box challenge-input"
                type="text"
                :disabled="possibleChallengeName ? true : false"
              />
              <span class="input-group-addon">챌린지</span>
            </div>
            <div>
              <v-btn
                @click="confirmChallengeName"
                color="#3396F4"
                class="overlap-check"
                :disabled="possibleChallengeName ? true : false"
              >
                <label class="btn">중복확인</label>
              </v-btn>
              <span v-if="possibleChallengeName" class="possible-name"
                >사용 가능한 제목입니다.</span
              >
              <span v-if="confirmedButImpossibleName" class="impossible-name"
                >사용 불가능한 제목입니다.</span
              >
            </div>
          </v-col>
        </v-row>
        <v-row class="row-area">
          <v-col class="input-title"
            ># 챌린지 참여자
            <input
              v-model="challenge.challengers"
              id="selectChallengers"
              @click="inputChallenger"
              type="checkbox"
            />
            <label for="selectChallengers"> 지목 </label>
          </v-col>
          <!--
                <v-checkbox
                    v-model="checkbox"
                    :label="`지목: ${checkbox.toString()}`">
                </v-checkbox>
                -->
        </v-row>
        <v-row>
          <v-col>
            <input
              v-if="$vuetify.theme.dark"
              class="dark-mode input-box challenger-input"
              type="text"
              v-model="challenge.challengers"
              :disabled="!selectChallenger"
              :class="{ disabled: !selectChallenger }"
              placeholder="@박싸피"
            />
            <input
              v-else
              class="light-mode input-box challenger-input"
              type="text"
              v-model="challenge.challengers"
              :disabled="!selectChallenger"
              :class="{ disabled: !selectChallenger }"
              placeholder="@박싸피"
            />
          </v-col>
        </v-row>
        <v-row class="row-area">
          <v-col class="input-title"># 타이틀</v-col>
        </v-row>
        <v-row>
          <v-col>
            <input
              v-if="$vuetify.theme.dark"
              v-model="challenge.titleName"
              class="dark-mode input-box"
              type="text"
              placeholder="ex) 밥 잘먹는, 스쿼트 왕, ..."
            />
            <input
              v-else
              v-model="challenge.titleName"
              class="light-mode input-box"
              type="text"
              placeholder="ex) 밥 잘먹는, 스쿼트 왕, ..."
            />
          </v-col>
        </v-row>
        <v-row class="row-area">
          <v-col class="input-title"># 기간</v-col>
        </v-row>
        <v-radio-group v-model="challenge.endDate" row>
          <v-radio label="하루" value="1" @click="DatePickCancel"></v-radio>
          <v-radio label="일주일" value="7" @click="DatePickCancel"></v-radio>
          <v-radio label="한달" value="30" @click="DatePickCancel"></v-radio>
          <v-radio label="종료일" :value="date" @click="DatePickMenu"></v-radio>
          <end-date-picker @endDate="saveDate" v-if="EndDateMenu" />
        </v-radio-group>
        <v-row class="row-area">
          <v-col class="input-title"># 참여파일형식</v-col>
        </v-row>
        <v-radio-group v-model="challenge.fileType" row>
          <v-radio label="사진" value="IMAGE"></v-radio>
          <v-radio label="영상" value="VIDEO"></v-radio>
        </v-radio-group>
        <v-row>
          <v-col class="input-title"># 설명(최대 255자)</v-col>
        </v-row>
        <v-row class="row-area">
          <v-col>
            <v-textarea
              v-model="challenge.description"
              outlined
              placeholder="운동하니까 힘들다 #운동 @김싸피"
            ></v-textarea>
          </v-col>
        </v-row>
        <div class="error-text" v-if="error">{{ error }}</div>
        <div class="error-text" v-if="pleaseCheckName">
          챌린지 제목 중복 여부를 확인해주세요.
        </div>
        <v-row>
          <v-spacer></v-spacer>
          <v-btn class="ma-2" color="#3396F4">
            <label class="btn" @click="uploadPost">다음으로</label>
          </v-btn>
        </v-row>
      </v-card-text>
    </v-card>
    <post-upload
      v-if="postDialog"
      :propChallenge="challenge"
      @close-modal="postDialog = false"
      @close-challenge-modal="$emit('close-modal')"
      type="register"
    ></post-upload>
  </v-dialog>
</template>

<script>
import PostUpload from "./PostUpload.vue";
import EndDatePicker from "./EndDatePicker.vue";
export default {
  name: "ChallengeUpload",
  components: {
    PostUpload,
    EndDatePicker,
  },
  data: () => ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘
    postDialog: false, //true: PostDialog열림, false: PostDialog닫힘
    selectChallenger: false, //true : 챌린저 지정, false : 챌린저 미지정
    EndDateMenu: false,
    date: new Date(),
    clickNextBtn: false,
    error: false,
    challenge: {
      challengeName: "",
      challengers: [],
      titleName: "",
      endDate: "",
      fileType: "",
      description: "",
    },
  }),
  computed: {
    pleaseCheckName() {
      if (
        this.clickNextBtn &&
        !this.$store.state.challengeStore.possibleChallengeName
      ) {
        return true;
      }
      return false;
    },
    possibleChallengeName() {
      return this.$store.state.challengeStore.possibleChallengeName;
    },
    confirmedButImpossibleName() {
      return this.$store.state.challengeStore.confirmedButImpossibleName;
    },
  },
  methods: {
    confirmChallengeName() {
      this.$store.dispatch(
        "challengeStore/confirmChallengeName",
        this.challenge.challengeName
      );
    },
    inputChallenger() {
      // 챌린저 지목 체크 로직
      // 지목 false
      if (this.selectChallenger == false) {
        this.selectChallenger = !this.selectChallenger;
        document
          .querySelector("input.challenger-input")
          .removeAttribute("disabled");
      }
      // 지목 true
      else {
        this.selectChallenger = !this.selectChallenger;
        document
          .querySelector("input.challenger-input")
          .setAttribute("disabled", this.selectChallenger);
      }
    },
    uploadPost() {
      if (!this.possibleChallengeName) {
        this.clickNextBtn = true;
        return;
      }
      if (
        !this.challenge.titleName ||
        !this.challenge.fileType ||
        !this.challenge.description
      ) {
        this.error = "입력되지 않은 필수 항목이 있습니다.";
        return;
      }
      this.dialog = false;
      this.postDialog = true;
    },
    DatePickMenu() {
      this.EndDateMenu = true;
    },
    DatePickCancel() {
      this.EndDateMenu = false;
      this.error = false;
    },
    /* 종료일 받아오기 */
    saveDate(endDate) {
      /* 오늘 날짜 */
      const today = new Date();
      const year = today.getFullYear();
      const month = parseInt(("0" + (today.getMonth() + 1)).slice(-2));
      const day = parseInt(("0" + today.getDate()).slice(-2));

      /* 사용자가 선택한 종료날짜 */
      const endYear = endDate.slice(0, 4);
      const endMonth = endDate.slice(5, 7);
      const endDay = parseInt(endDate.slice(8, 10));

      /* 종료일을 오늘보다 이른 날짜에 했다면 오류 메세지 출력 */
      if (endYear < year) {
        this.error = "이전 날짜는 선택이 불가능합니다.";
      } else if (endMonth < month) {
        this.error = "이전 날짜는 선택이 불가능합니다.";
      } else if (endDay <= day) {
        this.error = "이전 날짜는 선택이 불가능합니다.";
      } else {
        /* 유효한 종료일일 때, 값 저장 */
        this.challenge.endDate = endDate;
        this.error = false;
        console.log(this.challenge.endDate);
      }
    },
  },
};
</script>

<style scoped>
.card-header {
  position: sticky;
  top: 0;
  z-index: 1;
}
.cancel-btn {
  margin-right: -10px;
}
.input-title {
  color: #3396f4;
}
.input-box {
  width: 100%;
  height: 36px;
  border: 1px solid #9e9e9e;
  border-radius: 4px;
  padding-left: 10px;
  margin-bottom: 10px;
}
.input-box:focus {
  outline: none;
}
.input-group {
  position: relative;
  display: table;
  border-collapse: separate;
  margin-bottom: 0.5rem;
}
.challenge-input {
  position: relative;
  z-index: 2;
  float: left;
  width: 100%;
  display: table-cell;
  padding: 6px 12px;
  border: 1px solid #ccc;
  margin: 0;
}
.input-group-addon {
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 400;
  line-height: 1;
  color: #555;
  text-align: center;
  background-color: #eee;
  border: 1px solid #ccc;
  border-radius: 0px 4px 4px 0px;
  border-left: 0;
  width: 1%;
  white-space: nowrap;
  vertical-align: middle;
  display: table-cell;
}
.overlap-check {
  margin-top: -2px;
  margin-bottom: 8px;
}
#selectChallengers {
  vertical-align: middle;
  margin-left: 10px;
  margin-right: 4px;
}
.challenger-input:disabled {
  background-color: #a5a5a5;
}
.row-area {
  margin-top: -16px;
  margin-bottom: -30px;
}
.media-form {
  text-align: right;
}
.ma-2 {
  margin-right: 12px !important;
}
.btn {
  color: #fff;
  cursor: pointer;
}
.error-text {
  color: rgb(235, 38, 38);
  margin-bottom: 10px;
}
.possible-name {
  color: #3396f4;
  margin-left: 1rem;
}
.impossible-name {
  color: red;
  margin-left: 1rem;
}
/* 다크모드, 라이트모드 input색 변경 */
.light-mode {
  color: #121212;
}
.dark-mode {
  color: #fff;
}
</style>