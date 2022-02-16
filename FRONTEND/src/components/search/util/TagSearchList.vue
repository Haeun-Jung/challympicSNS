<template>
  <v-skeleton-loader
    v-if="!loaded"
    max-width="444px"
    height="360px"
    type="image, list-item-two-line"
    class="mx-auto"
  ></v-skeleton-loader>
  <v-card v-else elevation="2" height="auto" width="444px" class="mx-auto">
    <div class="holder">
      <video-player
        v-if="!mediaType"
        ref="videoPlayer"
        height="280!important"
        :playsinline="true"
        :options="playerOptions"
        class="vjs-big-play-centered"
      >
      </video-player>
      <v-img
        v-else
        max-width="100%"
        max-height="100%"
        :src="imageUrl"
        height="282;!important"
      />
      <!-- 포스트 정보 -->
      <div class="bar">
        <v-card-title>
          <router-link
            :to="{ path: `/feed/${post.user_no}` }"
            style="text-decoration: none; color: white"
          >
            <h3
              style="
                text-shadow: 1px 1px 3px #424242;
                -webkit-text-stroke-width: 0.1px;
                -webkit-text-stroke-color: #424242;
              "
            >
              {{ post.user_nickname }}
            </h3>
          </router-link>
          <v-spacer />
        </v-card-title>

        <v-card-subtitle style="color: white">
          <strong
            style="
              text-shadow: 1px 1px 3px #424242;
              -webkit-text-stroke-width: 0.1px;
              -webkit-text-stroke-color: #424242;
            "
          >
            좋아요 {{ post.post_like_count }} 개 댓글
            {{ post.comment_count }}개</strong
          >
        </v-card-subtitle>
      </div>
      <!-- 좋아요-->
      <div class="bar-heart">
        <v-btn @click="pushLike" icon>
          <v-icon :color="post.isLike ? 'red' : 'grey lighten-3'" size="32">
            mdi-heart-outline
          </v-icon>
        </v-btn>
      </div>
    </div>
    <v-list two-line>
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title>
            <h3 class="title-block" @click="goChallenge">
              {{ post.chalenge_title }}
            </h3>
          </v-list-item-title>
          <v-list-item-subtitle>
            {{ splitContents }}
          </v-list-item-subtitle>
          <v-list-item-subtitle> {{ splitTags }}</v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
import { postLikeList } from "@/api/post.js";

export default {
  name: "TagSearchList",
  props: {
    post: Object,
  },
  created() {
    this.mediaType = this.post.file_savedname.split(".")[1];
    console.log(this.mediaType);
    this.playerOptions = {
      preload: "auto",
      //autoplay: true,
      muted: true,
      //loop: true,
      aspectRatio: "4:3",
      sources: [
        {
          type: "application/x-mpegURL",
          src:
            "https://d3iu4sf4n4i2qf.cloudfront.net/" +
            this.post.file_path +
            "/video/" +
            this.post.file_savedname +
            ".m3u8",
        },
      ],
    };

    this.user = this.$store.state.userStore.userInfo;
  },
  data() {
    return {
      //http://d3iu4sf4n4i2qf.cloudfront.net/output/media/20220213/175935/undefined
      imageUrl:
        "https://d3iu4sf4n4i2qf.cloudfront.net/" +
        this.post.file_path +
        "/" +
        this.post.file_savedname,
      loaded: false,
      playerOptions: [],
      postLike: this.post.isLike, //유저테이블에서 가져오기
      mediaType: "",
      user: null,
    };
  },
  computed: {
    /*백앤드에서 태그는 내용에 포함된다고 함 */
    splitTags() {
      for (var i = 0; i < this.post.post_content.length; i++) {
        if (this.post.post_content.charAt(i) === "#") break;
      }
      return this.post.post_content.substring(i);
    },
    splitContents() {
      for (var i = 0; i < this.post.post_content.length; i++) {
        if (this.post.post_content.charAt(i) === "#") break;
      }
      if (this.post.post_content.substring(0, i) === "")
        return "포스트 내용이 없습니다.";
      else return this.post.post_content.substring(0, i);
    },
  },
  mounted: function () {
    var that = this;
    if (document.readyState === "complete") this.$set(that, "loaded", true);
    document.addEventListener("readystatechange", function () {
      if (document.readyState === "complete") that.$set(that, "loaded", true);
    });
    //	this.playerOptions.sources.src = this.post.content;
    //console.log(this.playerOptions.sources.src);
  },
  methods: {
    goPost() {
      alert("상세 페이지로 이동 where postno = " + this.post.post_no);
    },
    goChallenge() {
      const path = "/challenge/" + this.post.challenge_no;
      this.$router.push(path);
      this.$router.go();
    },
    pushLike() {
      if (!this.user) {
        alert("로그인이 필요한 서비스입니다.");
        return;
      }

      // 좋아요 API 요청
      if (this.post.isLike && this.post.post_like_count > 0) {
        // 클릭된 상태
        this.post.post_like_count -= 1;
      } else {
        this.post.post_like_count += 1;
      }

      this.post.isLike = !this.post.isLike;

      postLikeList(
        this.post.post_no,
        this.user.user_no,
        (response) => {
          console.log(response);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    pushSubscribe() {
      alert("???");
      //	alert(this.challenge.challenge_subscribe);
      //해당 챌린지 challenge.challenge_no로 구독 취소
      this.challenge.challenge_subscribe = !this.challenge.challenge_subscribe;
    },
  },
};
</script>

<style>
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
.responsive-media {
  position: absolute;
  margin: auto;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
</style>
