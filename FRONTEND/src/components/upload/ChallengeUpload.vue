<template>
  <!-- POST 등록 모달 -->
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="card-header">
          <v-spacer></v-spacer>
          <v-btn class="cancel-btn" icon @click="$emit('close-modal')"> <v-icon>mdi-close</v-icon> </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row class="row-area">
            <v-col class="input-title"># 챌린지 제목(예: 미라클_모닝)</v-col>
          </v-row>
          <v-row>
              <v-col>
                <input v-if="$vuetify.theme.dark" class="dark-mode input-box challenge-input" type="text" placeholder="챌린지"/>
                <input v-else class="light-mode input-box challenge-input" type="text" placeholder="챌린지"/>
                <v-btn color="#3396F4" class="overlap-check">
                  <label class="btn">중복확인</label>
                </v-btn>
              </v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 챌린지 참여자 
              <input id="selectChallengers" @click="inputChallenger" type="checkbox" />
              <label for="selectChallengers">
                지목
              </label>
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
                <input v-if="$vuetify.theme.dark" class="dark-mode input-box challenger-input" type="text" :disabled="!selectChallenger" :class="{disabled : !selectChallenger}" placeholder="@박싸피"/>
                <input v-else class="light-mode input-box challenger-input" type="text" :disabled="!selectChallenger" :class="{disabled : !selectChallenger}" placeholder="@박싸피"/>
              </v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 타이틀</v-col>
          </v-row>
          <v-row>
              <v-col>
                <input v-if="$vuetify.theme.dark" class="dark-mode input-box" type="text" placeholder="ex) 밥 잘먹는, 스쿼트 왕, ..."/>
                <input v-else class="light-mode input-box" type="text" placeholder="ex) 밥 잘먹는, 스쿼트 왕, ..."/>
              </v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 기간</v-col>
          </v-row>
            <v-radio-group v-model="row" row>
              <v-radio label="하루" value="1day" @click="DatePickCancel"></v-radio>
              <v-radio label="일주일" value="1week" @click="DatePickCancel"></v-radio>
              <v-radio label="한달" value="1month" @click="DatePickCancel"></v-radio>
              <v-radio label="종료일" :value="date" @click="DatePickMenu"></v-radio>
                <end-date-picker @endDate="saveDate" v-if="EndDateMenu"/>
            </v-radio-group>
          <v-row class="row-area">
            <v-col class="input-title"># 참여파일형식</v-col>
          </v-row>
            <v-radio-group v-model="row" row>
              <v-radio label="사진" value="photo"></v-radio>
              <v-radio label="영상" value="video"></v-radio>
            </v-radio-group>
          <v-row>
            <v-col class="input-title"># 설명(최대 255자)</v-col>
          </v-row>
          <v-row class="row-area">
            <v-col>
              <v-textarea
                v-model="description"
                outlined
                placeholder="운동하니까 힘들다 #운동 @김싸피"
              ></v-textarea>
            </v-col>
          </v-row>
          <div class="error-text" v-if="error">{{ error }}</div>
          <v-row>
              <v-spacer></v-spacer>
              <v-btn class="ma-2" color="#3396F4">
                <label class="btn" @click="uploadPost">다음으로</label>
              </v-btn>
          </v-row>
        </v-card-text>
      </v-card>
      <post-upload v-if="postDialog" @close-modal="postDialog=false" type="register"></post-upload>
    </v-dialog>
</template>

<script>
import PostUpload from "./PostUpload.vue";
import EndDatePicker from "./EndDatePicker.vue"
export default {
  name: "ChallengeUpload",
  components: {
    PostUpload,
    EndDatePicker,
  },
  data: ()=> ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘
    selectChallenger: false, //true : 챌린저 지정, false : 챌린저 미지정
    postDialog: false, //true: PostDialog열림, false: PostDialog닫힘
    EndDateMenu: false,
    endDate: '',
    error: false,
  }),
  methods: {
    inputChallenger() { // 챌린저 지목 체크 로직
      // 지목 false
      if (this.selectChallenger == false) {
        this.selectChallenger = !this.selectChallenger;
        document.querySelector('input.challenger-input').removeAttribute('disabled');
      }
      // 지목 true
      else {
        this.selectChallenger = !this.selectChallenger;
        document.querySelector('input.challenger-input').setAttribute('disabled', this.selectChallenger);
      }
    },
    uploadPost() {
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
      const month = ('0' + (today.getMonth() + 1)).slice(-2);
      const day = parseInt(('0' + today.getDate()).slice(-2));

      /* 사용자가 선택한 종료날짜 */
      const endYear = endDate.slice(0, 4);
      const endMonth = endDate.slice(5, 7);
      const endDay = parseInt(endDate.slice(8, 10));
      
      /* 종료일을 오늘보다 이른 날짜에 했다면 오류 메세지 출력 */
      if (endYear < year) {
        this.error = '이전 날짜는 선택이 불가능합니다.';
      } else if (endMonth < month) {
        this.error = '이전 날짜는 선택이 불가능합니다.';
      } else if (endDay <= day) {
        this.error = '이전 날짜는 선택이 불가능합니다.';
      } else {
        /* 유효한 종료일일 때, 값 저장 */
        this.endDate = endDate;
        this.error = false;
        console.log(this.endDate);
      }
    }
  }
}
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
  color: #3396F4;
}
.input-box {
    width: 100%;
    height: 36px;
    border: 1px solid #9E9E9E;
    border-radius: 4px;
    padding-left: 10px;
    margin-bottom: 10px;
}
.input-box:focus {
  outline: none;
}
.challenge-input::-webkit-input-placeholder {
    text-align: right;
    padding-right: 10px;
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
    text-align: right;
    margin-bottom: 10px;
}

/* 다크모드, 라이트모드 input색 변경 */
.light-mode {
  color: #121212;
}
.dark-mode {
  color: #fff;
}
</style>