<template>
  <v-dialog v-model="dialog" persistent scrollable max-width="400px">
    <v-card>
      <v-card-title class="lighten-2 card-title-align">
        <h3>좋아요</h3>
        <v-btn
          class="cancel-btn"
          absolute
          top
          right
          icon
          @click="$emit('close-dialog')"
        >
          <v-icon>mdi-close</v-icon>
        </v-btn>
      </v-card-title>

      <v-divider />

      <div>
        <v-list class="overflow-y-auto">
          <div
            style="text-align: center"
            v-if="!this.$store.state.userStore.userInfo"
          >
            <h4>로그인하셔야 이용할 수 있는 서비스입니다</h4>
          </div>
          <div style="text-align: center" v-else-if="likeUserList.length == 0">
            <h4>첫 좋아요를 눌러주세요!</h4>
          </div>

          <v-list-item
            v-else
            v-for="(user, idx) in likeUserList"
            :key="user.user_no"
          >
            <img
              v-if="user.user_title != '도전자'"
              class="medal-icon"
              src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
            />
            <v-list-item-avatar class="user-image">
              <v-img
                :alt="`${user.user_nickname} avatar`"
                :src="imageUrl(user.file_path, user.file_savedname)"
              ></v-img>
            </v-list-item-avatar>

            <v-list-item-content>
              <!-- 배지.. -->
              <!-- <v-img v-if="user.title" max-width="20" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/> -->
              <v-list-item-subtitle
                v-text="user.user_title"
                src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
              ></v-list-item-subtitle>
              <a
                :href="`/feed/${user.user_no}`"
                :class="[
                  'text-decoration-none',
                  [$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
                ]"
              >
                <v-list-item-title
                  v-text="user.user_nickname"
                ></v-list-item-title>
              </a>
              <!-- 클릭 간격 조절 -->
            </v-list-item-content>
            <!-- 리스트에 있는 유저와 내(=로그인 유저)가 다를 때 -->
            <v-list-item-icon
              v-if="(login_user ? login_user.user_no : 0) != user.user_no"
            >
              <v-btn
                v-if="!user.isFollowing"
                @click="follow(user.user_no, idx)"
                color="#3396F4"
                class="white--text rounded-xl"
                small
              >
                팔로우
              </v-btn>
              <v-btn
                v-else
                @click="follow(user.user_no, idx)"
                color="#3396F4"
                class="rounded-xl"
                small
                outlined
              >
                팔로잉
              </v-btn>
            </v-list-item-icon>
          </v-list-item>
        </v-list>
      </div>
		<v-snackbar
			v-model="snackbar"
			:timeout="timeout"
			color="error"
			outlined
			style="font-weight: bold; border: 2px solid; color: transparent"
		>
			{{ text }}

			<template v-slot:action="{ attrs }">
				<v-btn color="red" text v-bind="attrs" @click="snackbar = false">
					Close
				</v-btn>
			</template>
		</v-snackbar>
    </v-card>
  </v-dialog>
</template>

<script>
// import { getFollowerList, getFollowingList, setFollow } from "@/api/feed.js";
import { setFollow } from "@/api/feed.js";

export default {
  name: "FollowLikeModal",
  props: {
    dialog: Boolean,
    likeUserList: Array,
    login_user: Number,
  },
  data() {
    return {
      likeList: null,
      snackbar: false,
			text: "로그인이 필요한 서비스입니다.",
			timeout: 1500,
    };
  },
  methods: {
    imageUrl(file_path, file_savedname) {
      if (!file_path) return require("@/assets/profile.png");

      return this.defaultPath + file_path + "/" + file_savedname;
    },
    follow(userNo, idx) {
				if (!this.$store.state.userStore.userInfo) {
					this.snackbar = true;
					return;
				}
    console.log("좋아요한 애들");
    console.log( this.likeUserList);
      // 팔로우 API 요청 보내기
      // 해당 유저에 대한 isFollowing 값 변경
      setFollow(this.login_user, userNo, (response) => {
        console.log("setFollow 호출 반환값");
        console.log(response);
        this.likeUserList[idx].isFollowing = !this.likeUserList[idx].isFollowing;
      });
    },
  },
};
</script>

<style scoped>
.dark-mode-text {
  color: rgb(255, 255, 255, 0.6);
}
.black-text {
  color: black;
}
.card-title-align {
  display: flex;
  justify-content: center;
}
.cancel-btn {
  margin-top: -4px;
  margin-right: -4px;
}
.cancel-icon {
  width: 16px;
}
.follow-btn {
  color: #fff;
}
.user-image {
  margin-right: 16px;
}
.medal-icon {
  position: absolute;
  top: 0.5rem;
  left: 2.3rem;
  width: 20px;
  height: 20px;
  z-index: 1;
}
</style>
