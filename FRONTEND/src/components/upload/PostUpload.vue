<template>
  <!-- POST 등록 모달 -->
  <v-app>
    <!-- v-app 태그 추가 -->
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="card-header">
          <v-spacer></v-spacer>
          <v-btn class="cancel-btn" icon @click="$emit('close-modal')">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row class="upload-area">
            <!-- 업로드 공간 -->
            <v-col
              cols="12"
              sm="12"
              md="12"
              style="
                position: relative;
                border: 1px dashed #3396f4;
                border-style: dashed;
              "
            >
              <div class="example-drag">
                <div
                  v-show="$refs.upload && $refs.upload.dropActive"
                  class="drop-active"
                >
                  <h3>Drop files to upload</h3>
                </div>
                <!-- 파일 업로드 후 -->
                <template v-if="post.file.length">
                  <v-data-table
                    dense
                    :headers="headers"
                    :items="post.file"
                    item-key="name"
                    class="elevation-1"
                    hide-default-footer
                  >
                  </v-data-table>
                  <div class="text-center mt-3 p-5">
                    <label @click="removeFile()" id="upload-text"
                      >Remove File</label
                    >
                  </div>
                </template>
                <!-- 파일 업로드 전 -->
                <template v-else>
                  <FileUpload
                    class="btn btn-primary"
                    :drop="true"
                    :drop-directory="true"
                    v-model="post.file"
                    ref="upload"
                    @input="onDrop()"
                  >
                  </FileUpload>
                  <v-row>
                    <v-col cols="12" sm="12" md="12">
                      <div class="text-center p-5">
                        <div
                          v-if="this.type === 'register'"
                          class="start-challenge-head"
                        >
                          <h2>🎊챌린지에 처음으로 도전하세요!🎊</h2>
                        </div>
                        <h3>
                          <v-icon style="margin-right: 5px"
                            >mdi-arrow-up-bold-box-outline</v-icon
                          >Drop or
                          <label for="file" id="upload-text">upload</label> file
                          to attach
                        </h3>
                        <h5>최대 업로드 파일 크기 : 50MB</h5>
                        <h5>사진 : JPG, JPEG, PNG / 영상 : MP4, AVI</h5>
                      </div>
                    </v-col>
                  </v-row>
                </template>
              </div>
            </v-col>
          </v-row>
          <hr />
          <br />
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
              <!-- 챌린지를 만들면서 넘어올 때 -->
              <v-text-field
                v-else-if="propChallenge"
                :value="`${propChallenge.challengeName}`"
                disabled
                outlined
              ></v-text-field>
              <v-select
                v-else
                v-model="selectedChallenge"
                :items="challengeList"
                dense
                outlined
                return-object
                label="챌린지를 선택해주세요."
                @change="onChange()"
              ></v-select>
            </v-col>
          </v-row>
          <v-row class="row-area">
            <v-col class="input-title"># 참여파일형식</v-col>
            <v-col v-model="form" class="media-form">{{ fileType }}</v-col>
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
            <v-btn class="ma-2" color="#3396F4">
              <label
                v-if="type === 'register'"
                class="upload-btn"
                @click="uploadStart()"
                >챌린지 등록하기</label
              >
              <label v-else class="upload-btn" @click="uploadStart()"
                >참여하기</label
              >
            </v-btn>
          </v-row>
        </v-card-text>
      </v-card>
    </v-dialog>
  </v-app>
</template>

<script>
import { getChallengeList } from "@/api/challenge.js";
import FileUpload from "vue-upload-component";

export default {
  name: "PostUpload",
  components: {
    FileUpload,
  },
  props: {
    type: { type: String },
    postDialog: { type: Boolean },
    propChallenge: { type: Object },
    propChallengeName: { type: Object },
  },
  data: () => ({
    dialog: true,
    // TODO: 임시 챌린지 목록 바꿔야 함
    challengeList: [],
    fileType: "",
    selectedChallenge: {
      challengeNo: "",
      challengeType: "",
      challengeName: "",
    },
    post: {
      file: [],
      challengeNo: "",
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
      return `${year}-${("0" + month).slice(-2)}-${("0" + day).slice(-2)}`;
    },
    removeFile() {
      this.post.file = [];
    },
    onChange() {
      // 챌린지가 선택되었을 때 파일을 가져와서 타입 비교
      if (this.post.file.length == 0) {
        this.fileType = this.selectedChallenge.challengeType;
        return;
      }

      let extension = this.post.file[0].file.name.split(".")[1];
      let type = "IMAGE";

      if (
        extension === "mp4" ||
        extension === "MP4" ||
        extension === "AVI" ||
        extension === "avi"
      )
        type = "VIDEO";

      if (type !== this.selectedChallenge.challengeType) {
        alert(
          "해당 챌린지는 " +
            this.selectedChallenge.challengeType +
            "타입 챌린지입니다."
        );
        this.selectedChallenge = -1;
        this.post.file = [];
      } else {
        this.fileType = type;
      }
    },
    onDrop() {
      // 업로드할 파일 형식과 참여파일형식이 맞지 않을 때 확인(file, form)
      let extension = this.post.file[0].file.name.split(".")[1];
      let type = "IMAGE";

      if (
        extension === "mp4" ||
        extension === "MP4" ||
        extension === "AVI" ||
        extension === "avi"
      )
        type = "VIDEO";

      if (this.propChallenge) {
        // 챌린지 등록에서 넘어온 경우
        if (type !== this.propChallenge.fileType) {
          alert(
            "해당 챌린지는 " +
              this.propChallenge.fileType +
              "타입 챌린지입니다."
          );
          this.post.file = [];
        } else {
          this.fileType = type;
        }
      } else if (this.propChallengeName) {
        // 챌린지에 바로 참여하기
        if (type !== this.propChallengeName.challangeType) {
          alert(
            "해당 챌린지는 " +
              this.propChallengeName.challangeType +
              "타입 챌린지입니다."
          );
          this.post.file = [];
        } else {
          this.fileType = type;
        }
      } else if (this.selectedChallenge.challengeType) {
        if (type !== this.selectedChallenge.challengeType) {
          alert(
            "해당 챌린지는 " +
              this.selectedChallenge.challengeType +
              "타입 챌린지입니다."
          );
          this.post.file = [];
          this.selectedChallenge = -1;
        } else {
          this.fileType = type;
        }
      }
    },
    uploadStart() {
      // 챌린지명이 prop으로 넘어왔을 경우 처리
      if (this.post.challengeName.length == 0) {
        if (this.propChallengeName) {
          this.post.challengeName = this.propChallengeName;
        } else if (this.propChallenge) {
          this.post.challengeName = this.propChallenge.challengeName;
        } else {
          this.post.challengeName = this.selectedChallenge.challengeName;
        }
      }

      console.log("챌린지 네임"+this.post.challengeName);

      // 항목들 입력 여부 확인
      if (
        !this.post.file[0] ||
        this.post.challengeNo < 0 ||
        this.post.description.length == 0
      ) {
        this.error = "입력되지 않은 항목이 있습니다.";
        return;
      } else {
        this.error = false;
      }

      // formData 생성
      let formData = new FormData();
      formData.append("file", this.post.file[0].file);
      formData.append("user_no", this.$store.state.userStore.userInfo.user_no);
      formData.append("post_content", this.post.description);

      // 업로드 로직
      // 챌린지 등록에서 넘어왔을 경우
      if (this.propChallenge) {
        // 챌린저 처리
        if (this.propChallenge.challengers.length > 1) {
          this.propChallenge.challengers =
            this.propChallenge.challengers.split();
        }
        // 종료일 계산
        const propEndDate = this.propChallenge.endDate;
        if (propEndDate.length < 3) {
          const endDate = this.getEndDate(propEndDate);
          const challenge = {
            ...this.propChallenge,
            endDate,
          };
          this.$store.dispatch("challengeStore/createChallengeWithPost", {
            challenge,
            post: formData,
          });
        } else {
          this.$store.dispatch("challengeStore/createChallengeWithPost", {
            challenge: this.propChallenge,
            post: formData,
          });
        }
      } else {
        // 바로 포스트 등록
        // 포스트 업로드: challenge name만 받았거나 모달에서 챌린지를 선택했을 경우
        let challengeNo = -1;
        if (this.propChallenge) {
          challengeNo = this.propChallenge.challengeNo;
        }

        if (this.selectedChallenge) {
          challengeNo = this.selectedChallenge.challengeNo;
        }
        this.$store.dispatch("postStore/createPost", {
          challengeNo,
          post: formData,
        });
      }

      this.$store.commit("challengeStore/RESET_POSSIBLE_STATUS");
      this.dialog = false;
      this.$emit("close-challenge-modal");
      this.$emit("close-modal");
    },
  },
  created() {
    if (!this.propChallenge && !this.propChallengeName) {
      getChallengeList(
        (response) => {
          const { data } = response;
          this.challengeList = data.data.map((challenge) => {
            return {
              text: challenge.challenge_title,
              value: challenge.challenge_no,
              challengeNo: challenge.challenge_no,
              challengeType: challenge.challenge_type,
            };
          });
        },
        () => {
          console.log("챌린지 목록 가져오기 실패");
        }
      );
    } else {
      // 챌린지 정보를 가지고 들어올 때
      if (this.propChallenge) {
        this.fileType = this.propChallenge.fileType;
      } else if (!this.propChallengeName) {
        this.fileType = this.propChallengeName.challangeType;
      }
    }
  },
};
</script>

<style scoped>
hr {
  border: 3px solid #e3e3e3;
  background: #e3e3e3;
}

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
  padding: 12px;
  /* padding-bottom: 60px; */
  margin-bottom: 10px;
}

#upload-text {
  color: #3396f4;
}

#upload-text:hover {
  font-weight: bold;
  text-decoration: underline;
  cursor: pointer;
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
