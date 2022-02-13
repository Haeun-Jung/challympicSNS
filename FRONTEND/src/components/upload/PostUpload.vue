<template>
  <!-- POST 등록 모달 -->
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="card-header" > 
          <v-spacer></v-spacer>
          <v-btn class="cancel-btn" icon @click="$emit('close-modal')"> <v-icon>mdi-close</v-icon> </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row>
            <!-- 업로드 공간 -->
            <v-col cols="12" sm="12" md="12" class="upload-area">
              <div class="example-drag">
              <div v-show="$refs.upload && $refs.upload.dropActive" class="drop-active">
              </div>
              <template v-if="post.file.length">
                <v-data-table
                  dense
                  :headers="headers"
                  :items="post.file"
                  item-key="name"
                  class="elevation-1"
                  hide-default-footer
                  hide-default-header
                >
                </v-data-table>
              </template>
              <template v-else>
                <FileUpload
                  class="btn btn-primary"
                  :multiple="true"
                  :drop="true"
                  :drop-directory="true"
                  v-model="post.file"
                  ref="upload"
                  @change="onDrop"
                >
                </FileUpload>
                <v-row>
                  <v-col cols="12" sm="12" md="12">
                    <div class="text-center p-5">
                      <div
                        v-if="this.type === 'register'"
                        class="start-challenge-head"
                      >
                        🎊챌린지에 처음으로 도전하세요🎊
                      </div>
                      <v-btn class="ma-2" color="#3396F4">
                        <label for="file" class="upload-btn"
                          >파일 업로드하기</label
                        >
                      </v-btn>
                      <h5>최대 업로드 파일 크기 : 50MB</h5>
                      <h5>사진 : JPG, JPEG, PNG</h5>
                      <h5>영상 : MP4, AVI</h5>
                    </div>
                  </v-col>
                </v-row>
              </template>
            </div>
          </v-col>
        </v-row>
        <!-- 본문 -->
        <v-row>
          <v-col class="input-title"># 챌린지 선택</v-col>
        </v-row>
        <v-row class="row-area">
          <v-col class="d-flex">
            <v-text-field
              v-if="propChallengeName"
              :value="`${propChallengeName.challengeName}`"
              disabled
              outlined
            ></v-text-field>
            <v-text-field
              v-else-if="propChallenge"
              :value="`${propChallenge.challengeName}`"
              disabled
              outlined
            ></v-text-field>
            <v-select
              v-else
              v-model="post.challengeName"
              :items="challenges"
              dense
              outlined
            ></v-select>
          </v-col>
        </v-row>
        <v-row class="row-area">
          <v-col class="input-title"># 참여파일형식</v-col>
          <v-col v-model="form" class="media-form">사진/동영상</v-col>
        </v-row>
        <v-row>
          <v-col class="input-title"># 내용(최대 255자)</v-col>
        </v-row>
        <v-row class="row-area">
          <v-col>
            <v-textarea
              v-model="post.description"
              outlined
              name="input-7-4"
              placeholder="운동하니까 힘들다 #운동 @김싸피"
            ></v-textarea>
          </v-col>
        </v-row>
        <div class="error-text" v-if="error">{{ error }}</div>
          <v-row>
            <v-spacer></v-spacer>
            <v-btn
                class="ma-2"
                color="#3396F4"
              >
                <label v-if="type === 'register'" class="upload-btn" @click="uploadStart()">챌린지 등록하기</label>
                <label v-else class="upload-btn" @click="uploadStart()">참여하기</label>
              </v-btn>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
</template>

<script>
import FileUpload from "vue-upload-component";

export default {
  name: "PostUpload",
  components: {
    FileUpload,
  },
  props: {
    type: { type: String },
    postDialog: {type: Boolean},
    propChallenge: { type: Object },
    propChallengeName: { type: Object },
  },
  data: () => ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘
    // TODO: 임시 챌린지 목록 바꿔야 함
    challenges: ["미라클_모닝_챌린지", "싸피_챌린지"],
    post: {
      file: [],
      challengeName: "",
      description: "",
    },
    form: "",
    error: false,
    headers: [
      { text: "name", value: "name" },
      { text: "size", value: "size" },
    ],
  }),
  methods: {
    onDrop(item) {
      console.log(item);
    },
    getEndDate(period) {
      const intPeriod = parseInt(period);
      const today = new Date();
      let year = today.getFullYear();
      let month = today.getMonth() + 1;
      let day = today.getDate();
      const dayCount = new Date(year, month, 0).getDate();
      if (day + intPeriod > dayCount) {
        day = day + intPeriod - dayCount;
        month += 1;
        if (month > 12) {
          month -= 12;
          year += 1;
        }
      } else {
        day = day + intPeriod;
      }
      return `${year}-${('0' + month).slice(-2)}-${('0' + day).slice(-2)}`;
    },
    uploadStart() {
      // 업로드할 파일 형식과 참여파일형식이 맞지 않을 때 확인(file, form)

      // 챌린지명이 prop으로 넘어왔을 경우 처리
      if (this.post.challengeName.length == 0) {
        if (this.propChallengeName) {
          this.post.challengeName = this.propChallengeName;
        } else if (this.propChallenge) {
          this.post.challengeName = this.propChallenge.challengeName;
        }
      } 
      // 항목들 입력 여부 확인
      if (
        !this.post.file[0] ||
        this.post.challengeName.length == 0 ||
        this.post.description.length == 0
      ) {
        this.error = "입력되지 않은 항목이 있습니다.";
      } else {
        this.error = false;
      }
      // formData 생성
      let formData = new FormData();
      console.log(this.post.file.length);
      formData.append("file", this.post.file[0]);
      console.log(this.post.file);
      formData.append("user_no", this.$store.state.userStore.userInfo.user_no);
      formData.append("post_content", this.post.description);
      console.log(formData.get("file"));
      for (var pair of formData.entries()) {
            console.log(pair[0]+ ', ' + pair[1]);
          }

      // 업로드 로직
      // 챌린지 등록에서 넘어왔을 경우
      if (this.propChallenge) {
        // 챌린저 처리
        if (this.propChallenge.challengers.length > 1) {
          this.propChallenge.challengers = this.propChallenge.challengers.split();
        }
        // 종료일 계산
        const propEndDate = this.propChallenge.endDate;
        if (propEndDate.length < 3) {
          const endDate = this.getEndDate(propEndDate);
          const challenge = {
            ...this.propChallenge,
            endDate
          }
          this.$store.dispatch('challengeStore/createChallengeWithPost', { challenge, post:formData });
        } else {
          this.$store.dispatch('challengeStore/createChallengeWithPost', { challenge: this.propChallenge, post: formData });
        }
      } else {
        // 포스트 업로드
        const challengeNo = this.propChallenge.challengeNo || this.propChallengeName.challengeNo;
        this.$store.dispatch('postStore/createPost', { challengeNo, post: formData });
      }
      this.$store.commit('challengeStore/RESET_POSSIBLE_STATUS');
      this.dialog = false;
      this.$emit('close-challenge-modal');
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
.example-drag .drop-active {
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  position: fixed;
  z-index: 9999;
  opacity: 0.6;
  text-align: center;
  width: 100%;
  background: #000;
}
.example-drag .drop-active h3 {
  margin: -0.5em 0 0;
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  -webkit-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
  font-size: 40px;
  color: #fff;
  padding: 0;
}
.cancel-btn {
  margin-right: -10px;
}
.upload-area {
  position: relative;
  border-bottom: 1px solid #000;
  padding-bottom: 60px;
  margin-bottom: 10px;
}
.upload-btn {
  color: #fff;
  cursor: pointer;
}
.start-challenge-head {
  color: #3396f4;
  margin-bottom: 20px;
}
.input-title {
  color: #3396f4;
}
.row-area {
  margin-top: -20px;
  margin-bottom: -16px;
}
.media-form {
  text-align: right;
}
.error-text {
    color: rgb(235, 38, 38);
    text-align: right;
    margin-bottom: 10px;
}
.ma-2 {
  margin-right: 12px !important;
}
</style>