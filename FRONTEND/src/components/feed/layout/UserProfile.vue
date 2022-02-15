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
            <v-col align-self="center" class="name-wrapper">
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
                <v-col v-if="this.who_no != this.login_user">
                  <!-- 상대 프로필일 때 -->
                  <v-btn
                    v-if="isFollower"
                    @click="follow"
                    color="#3396F4"
                    class="white--text rounded-xl"
                    small
                  >
                    팔로잉취소
                  </v-btn>
                  <v-btn
                    v-else
                    @click="follow"
                    color="#3396F4"
                    class="rounded-xl"
                    small
                    outlined
                  >
                    팔로잉하기
                  </v-btn>
                </v-col>
              </v-list-item-title>
            </v-col>
            <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
            <v-col md="2" class="follow-wrapper" align-self="center">
              <div class="font-weight">팔로워</div>
              <div class="show-folllow-modal" @click="openFollowerDialog">
                {{ followerCnt }}
              </div>
              <follow-like-modal
                v-if="follower"
                @close-modal="follower = false"
                type="follower"
                :login_user="this.login_user"
                :who_no="who_no"
              ></follow-like-modal>
            </v-col>
            <v-col md="2" class="follow-wrapper" align-self="center">
              <div class="font-weight">팔로잉</div>
              <div class="show-folllow-modal" @click="openFollowingDialog">
                {{ followingCnt }}
              </div>
              <follow-like-modal
                v-if="following"
                @close-modal="following = false"
                type="following"
                :login_user="this.login_user"
                :who_no="who_no"
              ></follow-like-modal>
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
                    팔로잉취소
                  </v-btn>
                  <v-btn
                    v-else
                    @click="follow"
                    color="#3396F4"
                    class="rounded-xl"
                    small
                    outlined
                  >
                    팔로잉하기
                  </v-btn>
                </v-col>
              </v-col>
            </v-row>
            <!-- 팔로잉, 팔로우 명단 props로 잡기(post/FollowLikeModal) -->
            <v-row>
              <v-col md="2" class="follow-wrapper" align-self="center">
                <div class="font-weight">팔로워</div>
                <div class="show-folllow-modal" @click="openFollowerDialog">
                  {{ followerCnt }}
                </div>
                <follow-like-modal
                  v-if="follower"
                  @close-modal="
                    follower = false;
                    followerCnt = 'followerCnt';
                  "
                  type="follower"
                  :login_user="this.login_user"
                  :who_no="who_no"
                  v-on:decrementFollowerCnt="decrementFollowerCnt"
                  v-on:incrementFollowerCnt="incrementFollowerCnt"
                ></follow-like-modal>
              </v-col>
              <v-col md="2" class="follow-wrapper" align-self="center">
                <div class="font-weight">팔로잉</div>
                <div class="show-folllow-modal" @click="openFollowingDialog">
                  {{ followingCnt }}
                </div>
                <follow-like-modal
                  v-if="following"
                  @close-modal="
                    following = false;
                    followingCnt = 'followingCnt';
                  "
                  type="following"
                  :login_user="this.login_user"
                  :who_no="who_no"
                  v-on:decrementFollowingCnt="decrementFollowingCnt"
                  v-on:incrementFollowingCnt="incrementFollowingCnt"
                ></follow-like-modal>
              </v-col>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-row>
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
    type: String,
    who_no: Number,
    userInfo: Object,
  },
  data() {
    return {
      login_user: this.$store.state.userStore.userInfo.user_no,
      isFollower: false,
      profileUrl: "",
      follower: false,
      following: false,
      followerCnt: 0,
      followingCnt: 0,
    };
  },
  created() {
    console.log("this.login_user");
    console.log(this.login_user);
    if(!this.$store.state.userStore.userInfo){
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
      this.follower = !this.follower;
    },
    openFollowingDialog() {
      this.following = !this.following;
    },
    follow() {
      // 팔로우 취소 혹은 저장
      setFollow(this.login_user, this.who_no, (response) => {
        this.isFollower = response.data.following;
      });
      if (this.isFollower) {
        this.followerCnt--;
      } else {
        this.followerCnt++;
      }
    },
    decrementFollowerCnt(followerCnt) {
      console.log("decrementFollowerCnt");
      console.log(followerCnt);
      this.followerCnt = --followerCnt;
    },
    incrementFollowerCnt(followerCnt) {
      console.log("incrementFollowerCnt");
      console.log(followerCnt);
      this.followerCnt = ++followerCnt;
    },
    decrementFollowingCnt(followingCnt) {
      console.log("decrementFollowingCnt");
      console.log(followingCnt);
      this.followingCnt = --followingCnt;
    },
    incrementFollowingCnt(followingCnt) {
      console.log("incrementFollowingCnt");
      console.log(followingCnt);
      this.followingCnt = ++followingCnt;
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
  width: 30px;
  margin-right: 6px;
}
.header-title {
  font-size: 18px;
  color: rgb(138, 138, 138);
  margin-top: 3px;
}
.name-wrapper {
  padding-left: 30px;
}
.user-name {
  font-size: 20px;
  margin-left: 8px;
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
