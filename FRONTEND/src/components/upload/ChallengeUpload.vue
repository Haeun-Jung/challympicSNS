<template>
  <!-- POST 등록 모달 -->
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="card-header">
          <v-spacer></v-spacer>
          <v-btn class="cancel-btn" icon @click="$emit('close-modal')"> 
            <img class="cancel-icon" src="https://img.icons8.com/external-becris-lineal-becris/64/000000/external-cancel-mintab-for-ios-becris-lineal-becris.png"/>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row class="row-area">
            <v-col class="input-title"># 챌린지 제목</v-col>
          </v-row>
          <v-row>
              <v-col>
                <input class="input-box challenge-input" type="text" placeholder="챌린지"/>
                <v-btn color="#3396F4" class="overlap-check">
                  <label class="btn">중복확인</label>
                </v-btn>
              </v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 챌린지 참여자 <input id="selectChallengers" @click="inputChallenger" type="checkbox" /><label for="selectChallengers">지목</label></v-col>
                         <!--
                <v-checkbox
                    v-model="checkbox"
                    :label="`지목: ${checkbox.toString()}`">
                </v-checkbox>
                -->
          </v-row>
          <v-row>
              <v-col><input class="input-box challenger-input" type="text" :disabled="!selectChallenger" :class="{disabled : !selectChallenger}" placeholder="@박싸피"/></v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 타이틀</v-col>
          </v-row>
          <v-row>
              <v-col><input class="input-box" type="text" placeholder="ex) 밥 잘먹는, 스쿼트 왕, ..."/></v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 기간</v-col>
          </v-row>
            <v-radio-group v-model="row" row>
              <v-radio label="하루" value="1day"></v-radio>
              <v-radio label="일주일" value="1week"></v-radio>
              <v-radio label="한달" value="1month"></v-radio>
              <v-radio label="종료일" value="radio-2"></v-radio>
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
          <v-row>
              <v-spacer></v-spacer>
            <div class="error-text" v-if="error">{{ error }}</div>
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
export default {
  name: "ChallengeUpload",
  components: {
    PostUpload
  },
  data: ()=> ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘
    selectChallenger: false, //true : 챌린저 지정, false : 챌린저 미지정
    postDialog: false, //true: PostDialog열림, false: PostDialog닫힘
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
    }
  }
}
</script>

<style scoped>
.card-header {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 1;
}
.cancel-btn {
    margin-top: -10px;
    margin-right: -20px;
}
.cancel-icon {
    width: 16px;
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
  background-color: #dfdede;
}
.row-area {
  margin-top: -16px;
  margin-bottom: -30px;
}
.media-form {
  text-align: right;
}
.btn {
  color: #fff;
  cursor: pointer;
}
.error-text {
    display: flex;
    color: rgb(235, 38, 38);
    align-items: center;
    margin-right: 14px;
}
</style>