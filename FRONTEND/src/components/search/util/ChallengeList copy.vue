<template>
  <!-- Post List for each ChallengeList -->
  <v-card elevation="2" max-width="444px" class="mx-auto" ref="videoContainer">
    <v-carousel
      hide-delimiter-background
      delimiter-icon="mdi-minus"
      height="auto"
      width="444px"
    >
      <div class="holder">
        <v-carousel-item v-for="(item, idx) in post" :key="item.post_no">
          <div>
            <!-- v-if="isVideo === 'VIDEO'"-->
            <video-component :video="item.post" />
            <!--
						<v-img
							v-else
							:src="`https://d384sk7z91xokb.cloudfront.net/${item.file_path}/${item.file_savedame}`"
							class="video-player-box"
							height="280!important"
						/>
						-->
          </div>

          <!-- 포스트 정보 -->
          <div class="bar">
            <v-card-title>
              <router-link
                :to="{ path: `/feed/${item.user_no}` }"
                style="text-decoration: none; color: inherit"
              >
                <h3>
                  {{ item.user_nickname }}
                </h3>
              </router-link>
              <v-spacer />
            </v-card-title>

            <v-card-subtitle>
              <!--좋아요 {{ item.post_likes }} 개 댓글 {{ item.post_comments }}개
					-->
            </v-card-subtitle>
          </div>
          <!-- 좋아요-->
          <div class="bar-heart">
            <v-btn @click="pushLike(item.post_no, idx)" icon>
              <!-- <v-icon :class="{ 'show-btns': hover }" :color="transparent">
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시
								:class="{ 'show-btns': hover }"
								-->
              <v-icon
                :color="item.post_like ? 'red' : 'grey lighten-3'"
                size="32"
              >
                mdi-heart-outline
              </v-icon>
            </v-btn>
          </div>
        </v-carousel-item>
      </div>
    </v-carousel>

    <v-list two-line>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>
            <h3 class="title-block" @click="goChallenge">
              {{ challenge.challenge_title }}
            </h3>
          </v-list-item-title>
          <v-list-item-subtitle>
            {{ challenge.challenge_content }}
          </v-list-item-subtitle>
          <v-list-item-subtitle>
            {{ challenge.challenge_tag }}
          </v-list-item-subtitle>
        </v-list-item-content>
        <v-list-item-action>
          <span>
            <!-- v-if user_subscribe == true -> filled 된 애로 보여주기-->
            <v-btn icon @click="pushSubscribe">
              <v-icon
                :color="
                  challenge.challenge_subscribe ? 'blue' : 'grey lighten-3'
                "
                size="32"
                >mdi-bookmark-outline</v-icon
              >
            </v-btn>
          </span>
        </v-list-item-action>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
import VideoComponent from "./VideoComponent.vue";
import { list } from "@/api/search.js";

export default {
  components: { VideoComponent },
  name: "ChallengeList",
  props: {
    challenge: Object,
    challengeNo: Number,
    isVideo: String,
  },
  data() {
    return {
      overlay: false,
      playing: false,
      post: [],
      temp: [],
      loaded: false,
      challenges: {
        user_no: this.$store.state.userStore.userInfo.user_no,
        challenge_no: this.challengeNo,
      },
    };
  },
  created() {
    list(
      this.challenges,
      (response) => {
        this.posts = response.data.data;
        //여기 나중에 중복제거 하나 잇어야할듯..
      },
      (error) => {
        console.log(error);
      }
    );
  },
  mounted: function () {
    var that = this;
    if (document.readyState === "complete") this.$set(that, "loaded", true);
    document.addEventListener("readystatechange", function () {
      if (document.readyState === "complete") that.$set(that, "loaded", true);
    });
  },
  methods: {
    pushLike(postid, arrIdx) {
      //arrInx는 화면 바로 바꾸는용도
      //postid로 좋아요 요청
      this.post[arrIdx].post_like = !this.post[arrIdx].post_like;
      if (this.post[arrIdx].post_like) {
        this.post[arrIdx].post_likes++;
      } else {
        this.post[arrIdx].post_likes--;
      }
    },
    goChallenge() {
      /*alert(
					"챌린지 상세 페이지로 이동 where challenge_no:" +
						this.challenge.challenge_no
				);*/
      const path = "/challenge/" + this.challenge.challenge_no;
      this.$router.push(path);
    },
    pushSubscribe() {
      /*alert(this.challenge.challenge_subscribe);*/
      //해당 챌린지 challenge.challenge_no로 구독 취소나 좋아요 ㅇ ㅛ청 보내기
      this.challenge.challenge_subscribe = !this.challenge.challenge_subscribe;
    },
  },
};
</script>

<style>
video {
  object-fit: fill;
}
.bar {
  position: absolute;
  top: 1%;
  left: 0;
}
.bar-heart {
  position: absolute;
  top: 7%;
  right: 5%;
}
.title-block {
  display: inline-block;
  cursor: pointer;
}
</style>
