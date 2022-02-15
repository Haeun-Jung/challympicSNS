<template>
  <v-container>
    <!-- PC -->
    <v-row v-if="!isMobile()" class="profile-wrapper">
      <v-avatar v-if="userInfo.file_no == 0" size="150">
        <v-icon size="150">mdi-account-circle</v-icon>
      </v-avatar>
      <v-avatar v-else size="150">
        <img
          :src="
            'http://d3iu4sf4n4i2qf.cloudfront.net/' +
            this.userInfo.file_path +
            '/' +
            this.userInfo.file_savedname
          "
          alt="John"
        />
      </v-avatar>
      <v-col align-self="center">
        <v-container>
          <!-- 이름 -->
          <v-row>
            <v-col class="d-flex align-center name-wrapper">
              <v-list-item-title class="title-wrapper">
                <!-- 타이틀이 있을 때만 -->
                <img
                  class="medal-icon"
                  src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
                />
                <div class="header-title font-weight">
                  {{ userInfo.user_title }}
                </div>
                <div class="user-name font-weight">
                  {{ userInfo.user_nickname }}
                </div>
                <v-col v-if="this.who_no != this.login_user" class="pt-1">
                  <!-- 상대 프로필일 때 -->
                  <v-btn
                    v-if="isFollower"
                    @click="follow"
                    color="#3396F4"
                    class="white--text rounded-xl"
                    small
                  >
                    언팔로우
                  </v-btn>
                  <v-btn
                    v-else
                    @click="follow"
                    color="#3396F4"
                    class="rounded-xl"
                    small
                    outlined
                  >
                    팔로우
                  </v-btn>
                </v-col>
              </v-list-item-title>
            </v-col>
            <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
            <v-col md="2" class="follow-wrapper" align-self="center">
              <div class="font-weight">팔로워</div>
              <div class="show-folllow-modal" @click="openFollowerDialog">
                {{ computedFollowerCnt }}
              </div>
              <!-- <follow-like-modal
                v-if="follower"
                @close-modal="follower = false"
                type="follower"
                :login_user="this.login_user"
                :who_no="who_no"
                @decrementFollowerCnt="decrementFollowerCnt"
                @incrementFollowerCnt="incrementFollowerCnt"
              ></follow-like-modal> -->
            </v-col>
            <v-col md="2" class="follow-wrapper" align-self="center">
              <div class="font-weight">팔로잉</div>
              <div class="show-folllow-modal" @click="openFollowingDialog">
                {{ computedFollowingCnt }}
              </div>
              <!-- <follow-like-modal
                v-if="following"
                @close-modal="following = false"
                type="following"
                :login_user="this.login_user"
                :who_no="who_no"
                @decrementFollowingCnt="decrementFollowingCnt"
                @incrementFollowingCnt="incrementFollowingCnt"
              ></follow-like-modal> -->
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <!-- Mobile -->
    <v-row v-else>
      <!-- 이름 -->
        <v-col md="6" class="profile-setting-avatar-container">
          <v-avatar v-if="userInfo.file_no == 0" size="150">
            <v-icon size="150">mdi-account-circle-outline</v-icon>
          </v-avatar>
          <v-avatar v-else size="150">
            <img
              :src="
                'http://d3iu4sf4n4i2qf.cloudfront.net/' +
                this.userInfo.file_path +
                '/' +
                this.userInfo.file_savedname
              "
              alt="John"
            />
          </v-avatar>
        </v-col>
      <v-row>
        <v-col>
          <v-container>
            <!-- 타이틀과 이름 -->
            <v-row class="name-content">
              <v-col>
                <v-list-item-title class="title-wrapper">
                  <img
                    class="medal-icon"
                    src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
                  />
                  <div class="header-title font-weight">
                    {{ userInfo.user_title }}
                  </div>
                </v-list-item-title>
                <div class="user-name bottom-blank font-weight">
                  {{ userInfo.user_nickname }}
                </div>
                <v-col v-if="this.who_no != this.login_user">
                  <!-- 상대 프로필일 때 -->
                  <v-btn
                    v-if="isFollower"
                    @click="follow"
                    color="#3396F4"
                    class="white--text rounded-xl"
                    small
                  >
                    언팔로우
                  </v-btn>
                  <v-btn
                    v-else
                    @click="follow"
                    color="#3396F4"
                    class="rounded-xl"
                    small
                    outlined
                  >
                    팔로우
                  </v-btn>
                </v-col>
              </v-col>
            </v-row>
            <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
            <v-row>
              <v-col md="2" class="follow-wrapper" align-self="center">
                <div class="font-weight">팔로워</div>
                <div class="show-folllow-modal" @click="openFollowerDialog">
                  {{ computedFollowerCnt }}
                </div>
                <!-- <follow-like-modal
                  v-if="follower"
                  @close-modal="follower = false"
                  type="follower"
                  :login_user="this.login_user"
                  :who_no="who_no"
                  @decrementFollowerCnt="decrementFollowerCnt"
                  @incrementFollowerCnt="incrementFollowerCnt"
                ></follow-like-modal> -->
              </v-col>
              <v-col md="2" class="follow-wrapper" align-self="center">
                <div class="font-weight">팔로잉</div>
                <div class="show-folllow-modal" @click="openFollowingDialog">
                  {{ computedFollowingCnt }}
                </div>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-row>
    <follow-like-modal
      :dialog="dialog"
      :type="type"
      :login_user="login_user"
      :who_no="who_no"
      @decrementFollowingCnt="decrementFollowingCnt"
      @incrementFollowingCnt="incrementFollowingCnt"
      @close-dialog="dialog = false"
    ></follow-like-modal>
  </v-container>
</template>

<script>
import FollowLikeModal from "@/components/common/FollowLikeModal.vue";
import { checkFollow, setFollow, getFollowCnt } from "@/api/feed.js";
export default {
  name: "UserProfile",
  components: {
    FollowLikeModal,
  },
  props: {
    who_no: Number,
    userInfo: Object,
  },
  data() {
    return {
      login_user: this.$store.state.userStore.userInfo.user_no,
      dialog: false,
      isFollower: false,
      profileUrl: "",
      follower: false,
      following: false,
      followerCnt: 0,
      followingCnt: 0,
    };
  },
  computed: {
    type() {
      let type = "";
      if (this.follower) {
        type = "follower";
      } else if (this.following) {
        type = "following";
      }
      return type;
    },
    computedFollowerCnt() {
      return this.followerCnt;
    },
    computedFollowingCnt() {
      return this.followingCnt;
    }
  },
  created() {
    console.log("this.login_user");
    console.log(this.login_user);
    if(this.$store.state.userStore.userInfo.user_no > 0){
      // 유저 번호와 로그인 한 사람의 팔로우 관계
      checkFollow(this.login_user, this.who_no, (response) => {
        this.isFollower = response.data.following;
      })
    }
    // 유저의 팔로우, 팔로잉 cnt
    getFollowCnt(this.who_no, (response) => {
      this.followerCnt = response.data.data.followerCnt;
      this.followingCnt = response.data.data.followingCnt;
    });
  },
  methods: {
    isMobile() {
      if (
        /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
          navigator.userAgent
        )
      ) {
        return true;
      } else {
        return false;
      }
    },
    moveToProfile() {
      this.$router.push("/user/account/:userNo/");
    },
    openFollowerDialog() {
      this.follower = true;
      this.following = false;
      this.dialog = true;
    },
    openFollowingDialog() {
      this.following = true;
      this.follower = false;
      this.dialog = true;
    },
    follow() {
      // 팔로우 취소 혹은 저장
      setFollow(this.login_user, this.who_no, (response) => {
        this.isFollower = response.data.following;
      });
      if (this.isFollower) {
        this.followerCnt -= 1;
      } else {
        this.followerCnt += 1;
      }
    },
    // decrementFollowerCnt() {
    //   console.log("decrementFollowerCnt");
    //   this.followerCnt -= 1;
    // },
    // incrementFollowerCnt() {
    //   console.log("incrementFollowerCnt");
    //   this.followerCnt += 1;
    // },
    decrementFollowingCnt() {
      console.log("decrementFollowingCnt");
      this.followingCnt -= 1;
    },
    incrementFollowingCnt() {
      console.log("incrementFollowingCnt");
      this.followingCnt += 1;
    },
  },
};
</script>

<style scoped>
.profile-wrapper {
  margin-bottom: 30px;
}
.v-avatar {
  width: 130px;
}
.title-wrapper {
  display: inline-flex;
  margin-bottom: 4px;
}
.medal-icon {
  width: 40px;
  height: 40px;
  margin-right: 6px;
}
.header-title {
  font-size: 18px;
  color: rgb(138, 138, 138);
  margin-top: 5px;
}
.name-wrapper {
  padding-left: 30px;
}
.user-name {
  font-size: 20px;
  margin-left: 8px;
  margin-top: 3px;
}
.follow-wrapper {
  text-align: center;
  font-size: 17px;
}
.font-weight {
  font-weight: bold;
}
.show-folllow-modal {
  cursor: pointer;
}

/* 모바일 */
.profile-setting-avatar-container {
  display: table;
}

.avatar-wrapper {
  display: table-cell;
}

.name-content {
  text-align: center;
  margin-bottom: 2px;
}

.bottom-blank {
  margin-bottom: 6px;
}
</style>
