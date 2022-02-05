<template>
  <!-- POST 등록 모달 -->
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title class="card-header" > 
          <v-spacer></v-spacer>
          <v-btn class="cancel-btn" icon @click="$emit('close-modal')"> 
            <img class="cancel-icon" src="https://img.icons8.com/external-becris-lineal-becris/64/000000/external-cancel-mintab-for-ios-becris-lineal-becris.png"/>
          </v-btn>
        </v-card-title>
        <v-card-text>
          <v-row>
            <!-- 업로드 공간 -->
            <v-col cols="12" sm="12" md="12" class="upload-area">
              <div class="example-drag">
              <div v-show="$refs.upload && $refs.upload.dropActive" class="drop-active">
              </div>
              <template v-if="files.length">
                <v-data-table 
                  dense 
                  :headers="headers" 
                  :items="files" 
                  item-key="name" 
                  class="elevation-1"
                  hide-default-footer
                >
                </v-data-table>
              </template>
              <template v-else>
                <FileUpload
                  class="btn btn-primary"
                  :multiple="true"
                  :drop="true"
                  :drop-directory="true"
                  v-model="files"
                  ref="upload"
                  @input="onDrop()"
                >
                </FileUpload>
                <v-row>
                  <v-col cols="12" sm="12" md="12">
                    <div class="text-center p-5">
                        <div v-if="this.type === 'register'" class="start-challenge-head">🎊챌린지에 처음으로 도전하세요🎊</div>
                        <v-btn
                          class="ma-2"
                          color="#3396F4"
                        >
                          <label for="file" class="upload-btn">파일 업로드하기</label>
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
          <v-row  class="row-area">
            <v-col
              class="d-flex"
            >
              <v-select
                v-model="challenge"
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
            <v-col
                >
              <v-textarea
                v-model="description"
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
                <label v-if="this.type === 'register'" class="upload-btn" @click="uploadStart()">챌린지 등록하기</label>
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
    FileUpload
  },
  props: {
    type: { type: String },
  },
  data: ()=> ({
    dialog: true, //true : Dialog열림, false : Dialog닫힘
    challenges: ['미라클_모닝_챌린지', '싸피_챌린지'],
    file: '',
    challenge: '',
    form: '',
    description: '',
    error: false,
    files: [],
    headers: [
      { text: 'name', value: 'name' },
      { text: 'size', value: 'size' },
    ],
  }),
  methods: {
    onDrop(item){
      console.log(item)
    },
    uploadStart() {
        // 업로드할 파일 형식과 참여파일형식이 맞지 않을 때 확인(file, form)

        // 항목들 입력 여부 확인
        if (this.file.length == 0 || this.challenge.length == 0 || this.description.length == 0) {
            this.error = "입력되지 않은 항목이 있습니다."
        } else {
            this.error = false;
        }

        // 업로드 로직
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
.example-drag .drop-active {
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  position: fixed;
  z-index: 9999;
  opacity: .6;
  text-align: center;
  width:100%;
  background: #000;
}
.example-drag .drop-active h3 {
  margin: -.5em 0 0;
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
    margin-top: -10px;
    margin-right: -20px;
}
.cancel-icon {
    width: 16px;
    -webkit-filter: grayscale(100%); /* Chrome, Safari, Opera */
    filter: grayscale(100%);
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
  color: #3396F4;
  margin-bottom: 20px;
}
.input-title {
  color: #3396F4;
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
</style>