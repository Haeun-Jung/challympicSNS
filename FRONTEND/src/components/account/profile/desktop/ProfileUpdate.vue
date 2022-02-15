<template>
  <v-card height="88vh" elevation="0">
    <v-card-title>개인 정보 수정</v-card-title>
    <v-divider />
    <v-container>
      <v-row>
        <v-col class="profile-setting-avatar-container">
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
          </v-container>
        </v-col>
        <v-col>
          <v-container>
            <v-row class="row-bottom">
              <!-- 타이틀 -->
              <v-col cols="3">
                <v-list-item-title>대표 타이틀</v-list-item-title>
              </v-col>
              <v-col cols="7">
                <!--label에는 후에 selected 에 user.title로 가져오기-->
                <v-select
                  :items="userInfo.titles"
                  item-text="title_name"
                  item-value="title_name"
                  :label="userInfo.user_title"
                  dense
                  outlined
                  v-model=title
                ></v-select>
              </v-col>
            </v-row>
            <!-- 닉네임 -->
            <v-row class="row-bottom">
              <v-col cols="3">
                <v-list-item-title>닉네임</v-list-item-title>
              </v-col>
              <v-col cols="7">
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
              <v-col cols="2">
                <v-btn text color="primary" width="45px" @click="checkNickname"
                  >중복확인</v-btn
                >
              </v-col>
            </v-row>
            <!-- 이메일 -->
            <v-row>
              <v-col cols="3">
                <v-list-item-title>이메일</v-list-item-title>
              </v-col>
              <v-col>
                <v-list-item-title>{{ userInfo.user_email }}</v-list-item-title>
              </v-col>
            </v-row>
            <!-- 내 관심사 -->
            <v-row>
              <v-col cols="3">
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
                  class="tag-one"
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
      </v-row>
    </v-container>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { getSearchList } from "@/api/search.js";
import { save } from "@/api/user.js";
import { getInterest } from "@/api/user.js"

const userStore = "userStore";

export default {
  computed: {
    ...mapState(userStore, ["userInfo"]),
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
      title: null,
      dialog: false,
      search: "",
      AllTags: [], //전체 태그
      model: [],
      file: {}, //업로드용 파일
      changeFile: {}, //업로드용 파일
      filePreview: {},
      formData: new FormData(),
      alertMsg: "",
      selectedAllTags: "",
      listInterest:[""],
    };
  },
  /* 프로필 이미지 설정 */
  mounted() {
    /* 이미지 없을 때 */
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
    ...mapActions(userStore, ["getUserInfo", "modifyUser"]),
    onSubmit() {
      if (this.nickname == null && this.title == null && this.profile === `http://d3iu4sf4n4i2qf.cloudfront.net/${this.$store.state.userStore.filePath}/${this.$store.state.userStore.fileSavedName}`) {
        this.alertMsg = "변경사항이 없습니다.";
        return;
      }
      if (this.nickname == null && this.title == null && !this.profile) {
        this.alertMsg = "변경사항이 없습니다.";
        return;
      }
      if (this.nickname != null && !this.duplicateNicknameCheck) {
        this.alertMsg = "닉네임 중복체크를 해주세요.";
        return;
      }
      if (!this.possibleNickname && this.nickname != null) {
        this.alertMsg = "현재 사용중인 닉네임입니다.";
        return;
      }
      if (this.nickname == null) {
        this.nickname = this.userInfo.user_nickname;
      }
      else this.formData.set("user_nickname", this.nickname);
      if (this.title == null) {
        this.title = this.userInfo.user_title;
      }
      else this.formData.set("user_title", this.title);
      this.modifyUser({
        file: this.formData,
        token: sessionStorage.getItem("Authorization"),
      });
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
      }
    },
    saveInterest() {
      //테그 추가 api 연결!
      console.log("저장되었습니다");
      this.dialog = false;
      save(this.userInfo.user_no, this.selectedAllTags.tag_no);
      location.reload();

      //console.log(this.selectedAllTags.tag_no); -> 괄호 안에 들어간게 tagNo 입니다 !
    },
    imageUpload() {
      this.formData.append("file", this.$refs.file.files[0]);
      this.profile = "changed";
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
  created() {
    getSearchList(
      (response) => {
        this.AllTags = response.data.data.tagList;
      },
      (error) => {
        console.log(error);
      }
    );
    getInterest(this.$store.state.userStore.userInfo.user_no, (response) => {
      this.listInterest = response.data.data;
    });
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
div.alert-message {
  color: #db3425;
  font-weight: bold;
  margin-top: 4px;
  margin-right: 10px;
}
.tag-one {
  margin: 3px;
}
</style>
