<template>
  <v-card height="88vh" elevation="0">
    <v-card-title>개인 정보 수정</v-card-title>
    <v-divider />
    <v-container>
      <v-row md="2">
        <v-col md="4" class="profile-setting-avatar-container">
          <v-avatar size="150">
            <img v-if="profile" :src="profile" alt="profile" />
            <div v-else>이미지를 <br />업로드하세요!</div>
          </v-avatar>
          <v-container>
            <v-btn color="primary" class="text-none" depressed>
              <label for="file">이미지 선택</label>
            </v-btn>
            <input
              type="file"
              id="file"
              ref="file"
              @change="imageUpload"
              multiple
            />
            <!--<profile-upload-button />-->
          </v-container>
        </v-col>
        <v-col>
          <v-container>
            <v-row class="row-bottom">
              <!-- 타이틀 -->
              <v-col md="4">
                <v-list-item-title>대표 타이틀</v-list-item-title>
              </v-col>
              <v-col>
                <!--label에는 후에 selected 에 user.title로 가져오기-->
                <v-select
                  :items="userInfo.titles"
                  :label="userInfo.user_title"
                  dense
                  outlined
                  @click="titleChange(item)"
                ></v-select>
              </v-col>
              <v-col md="2" />
            </v-row>
            <!-- 닉네임 -->
            <v-row class="row-bottom">
              <v-col md="4">
                <v-list-item-title>닉네임</v-list-item-title>
              </v-col>
              <v-col>
                <v-text-field
                  :rules="nicknameRules"
                  :success-messages="nicknameSuccess"
                  :disabled="
                    duplicateNicknameCheck && possibleNickname ? true : false
                  "
                  :label="userInfo.user_nickname"
                  v-model="nickname"
                  dense
                  outlined
                  clearable
                ></v-text-field>
              </v-col>
              <v-col md="2">
                <v-btn text color="primary" width="45px" @click="checkNickname"
                  >중복확인</v-btn
                >
              </v-col>
            </v-row>
            <!-- 이메일 -->
            <v-row>
              <v-col md="4">
                <v-list-item-title>이메일</v-list-item-title>
              </v-col>
              <v-col>
                <v-list-item-title>{{ userInfo.user_email }}</v-list-item-title>
              </v-col>
            </v-row>
            <!-- 내 관심사 -->
            <v-row>
              <v-col md="4">
                <v-list-item-title
                  >내 관심사
                  <!-- icon 누를 시 모달 창 pop up-->
                  <v-dialog v-model="dialog" width="500">
                    <template v-slot:activator="{ on, attrs }">
                      <v-btn icon v-bind="attrs" v-on="on"
                        ><v-icon>mdi-heart-plus-outline</v-icon></v-btn
                      >
                    </template>

                    <v-card>
                      <v-card-title
                        class="text-h6"
                        :color="$vuetify.theme.dark ? '#424242' : '#FAFAFA'"
                      >
                        관심사 추가
                      </v-card-title>

                      <v-card-text>
                        <v-container fluid>
                          <v-autocomplete
                            v-model="selectedAllTags"
                            :items="AllTags"
                            :search-input.sync="search"
                            hide-selected
                            label=""
                            persistent-hint
                            small-chips
                            item-text="tag_content"
                            item-value="tag_content"
                            return-object
                          >
                            <template v-slot:no-data>
                              <v-list-item>
                                <v-list-item-content>
                                  <v-list-item-title>
                                    입력된 "<kbd>{{ search }}</kbd
                                    >" 태그가 존재하지 않습니다.
                                  </v-list-item-title>
                                </v-list-item-content>
                              </v-list-item>
                            </template>
                            <template v-slot:selection="data">
                              <v-chip
                                v-bind="data.attrs"
                                :search="data.selected"
                                @click="data.select"
                              >
                                {{ data.item.tag_content }}
                              </v-chip>
                            </template>
                          </v-autocomplete>
                        </v-container>
                      </v-card-text>

                      <v-divider></v-divider>

                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="primary" text @click="saveInterest">
                          저장
                        </v-btn>
                        <v-btn color="primary" text @click="dialog = false">
                          취소
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-list-item-title>
              </v-col>
              <v-col>
                <!-- 템플릿으로 chips 뿌림-->
                <v-chip
                  v-for="tag in listInterest"
                  :key="tag.tag_no"
                  :value="tag.tag_content"
                  v-model="tag.isOpen"
                  color="primary"
                  outlined
                  close
                  @click:close="remove(tag.tag_no)"
                >
                  {{ tag.tag_content }}
                </v-chip>
              </v-col>
              <v-col md="1"> </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
      <v-row>
        <v-spacer />
        <div class="alert-message">{{ this.alertMsg }}</div>
        <v-btn class="text-none" depressed color="primary" @click="onSubmit">
          회원 정보 수정</v-btn
        >
        <v-col md="1" />
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import ProfileUploadButton from "@/components/button/ProfilelUploadButton.vue";
import { getSearchList } from "@/api/search.js";
import { save } from "@/api/user.js";

const userStore = "userStore";

export default {
  // components: { ProfileUploadButton },
  computed: {
    ...mapState(userStore, ["userInfo", "listInterest"]),
    possibleNickname() {
      return this.$store.state.userStore.possibleNickname;
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
    nicknameSuccess() {
      if (!this.duplicateNicknameCheck || !this.possibleNickname) {
        return;
      }
      return "사용 가능한 닉네임입니다.";
    },
  },
  //props: [selectedFile],
  data() {
    return {
      nickname: null,
      nicknameValidation: false,
      duplicateNicknameCheck: false,
      profile: null,
      titles: ["밥 잘먹는", "타이틀2"],
      title: null,
      dialog: false,
      search: "",
      disabledTrue: false,
      AllTags: [], //전체 태그
      model: [],
      file: {}, //업로드용 파일
      changeFile: {}, //업로드용 파일
      filePreview: {},
      formData: "",
      alertMsg: "",
      selectedAllTags: "",
    };
  },
  /* 프로필 이미지 설정 */
  mounted() {
    if (this.$store.state.userStore.filePath == null) {
      this.profile = false;
      return;
    }
    this.profile =
      "http://d3iu4sf4n4i2qf.cloudfront.net/" +
      this.$store.state.userStore.filePath +
      "/" +
      this.$store.state.userStore.fileSavedName;
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo", "getInterest", "modifyUser"]),
    onSubmit() {
      if (this.nickname != null && !this.duplicateNicknameCheck) {
        this.alertMsg = "닉네임 중복체크를 해주세요.";
        return;
      }
      if (!this.possibleNickname && this.nickname != null) {
        this.alertMsg = "현재 사용중인 닉네임입니다.";
        return;
      }
      /* 사진 변경 X */
      if (
        this.profile ==
        "http://d3iu4sf4n4i2qf.cloudfront.net/" +
          this.$store.state.userStore.filePath +
          "/" +
          this.$store.state.userStore.fileSavedName
      ) {
        if (this.nickname == null && this.title == null) {
          this.profile =
            "http://d3iu4sf4n4i2qf.cloudfront.net/" +
            this.$store.state.userStore.filePath +
            "/" +
            this.$store.state.userStore.fileSavedName;
          return;
        }
        if (this.nickname == null) this.nickname = this.userInfo.user_nickname;
        if (this.title == null) this.title = this.userInfo.user_title;
        let formData = new FormData();
        formData.append("user_nickname", this.nickname);
        formData.append("user_title", this.title);
        this.modifyUser({
          file: formData,
          token: sessionStorage.getItem("Authorization"),
        });
      } else {
        /* 사진 변경 O */
        if (this.nickname == null) this.nickname = this.userInfo.user_nickname;
        if (this.title == null) this.title = this.userInfo.user_title;
        this.formData.append("user_nickname", this.nickname);
        this.formData.append("user_title", this.title);
        this.modifyUser({
          file: this.formData,
          token: sessionStorage.getItem("Authorization"),
        });
      }
      this.duplicateNicknameCheck = true;
      this.alertMsg = "회원정보가 변경되었습니다.";
      // setTimeout(() => {
      // 	window.location.reload();
      // }, 300);
    },
    remove(no) {
      this.$store.dispatch("userStore/deleteInterest", {
        no,
        token: sessionStorage.getItem("Authorization"),
      });
      setTimeout(() => {
        this.getInterest(sessionStorage.getItem("Authorization"));
      }, 300);
    },
    changed() {
      this.$emit("changed", this.attrArr);
    },
    checkNickname() {
      if (this.nickname == null) return;
      if (/^[가-힣a-zA-Z0-9].{1,10}$/.test(this.nickname))
        this.nicknameValidation = true;
      if (this.nicknameValidation) {
        this.$store.dispatch("userStore/confirmNickname", this.nickname);
        this.duplicateNicknameCheck = true;
        //if (this.possibleNickname) // this.disabledTrue = false;
      }
    },
    saveInterest() {
      //테그 추가 api 연결!
      alert("저장되었습니다");
      this.dialog = false;
      this.disabledTrue = false;
      save(this.userInfo.user_no, this.selectedAllTags.tag_no);
      location.reload();

      //console.log(this.selectedAllTags.tag_no); -> 괄호 안에 들어간게 tagNo 입니다 !
    },
    titleChange(title) {
      this.title = title;
      // this.disabledTrue = false;
    },
    imageUpload() {
      let formData = new FormData();
      formData.append("file", this.$refs.file.files[0]);
      this.formData = formData;
      this.changeFile = {
        //실제 파일
        file: this.$refs.file.files,
        //이미지 프리뷰
        preview: URL.createObjectURL(this.$refs.file.files[0]),
      };
      // 이미지 업로드용 프리뷰
      this.filePreview = {
        file: URL.createObjectURL(this.$refs.file.files[0]),
      };
      console.log(this.changeFile.preview);
      this.profile = this.changeFile.preview;
      // console.log(this.filesPreview);
    },
  },
  watch: {
    model(val) {
      if (val.length > 5) {
        this.$nextTick(() => this.model.pop());
      }
    },
  },
  created() {
    getSearchList(
      (response) => {
        this.AllTags = response.data.data.tagList;
      },
      (error) => {
        console.log(error);
      }
    );
  },
};
</script>

<style>
.profile-setting-avatar-container {
  justify-content: center !important;
  flex-direction: row !important;
  text-align: center !important;
  align-items: center !important;
}
input[type="file"] {
  position: absolute;
  width: 0;
  height: 0;
  padding: 0;
  overflow: hidden;
  border: 0;
}
.alert-message {
  color: #db3425;
  font-weight: bold;
  padding-top: 10px;
  margin-right: 20px;
}
</style>
