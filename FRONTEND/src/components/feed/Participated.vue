<template>
  <v-container fluid>
    <v-row dense>
      <v-col v-for="post in posts" :key="post.post_no" :cols="4">
        <v-hover>
          <template v-slot:default="{ hover }">
            <v-card class="mx-auto img-frame" max-width="344">
              <v-img
                :src="
                  'http://d3iu4sf4n4i2qf.cloudfront.net/' +
                  post.file_path +
                  '/' +
                  post.file_savedname
                "
              >
                <!-- 비디오일때만 -->
                <v-icon v-if="post.video" icon class="play-btn"
                  >mdi-play</v-icon
                >
              </v-img>
              <v-fade-transition>
                <v-overlay v-if="hover" absolute color="#2E2E2E">
                  <div class="info-wrapper">
                    <div class="challenge-info">
                      <v-icon icon class="icon hashtag-icon">mdi-pound</v-icon
                      >{{ post.challenge_title }}
                    </div>
                    <div>
                      <v-icon icon class="icon">mdi-heart</v-icon
                      >{{ post.like_cnt }}
                      <v-icon icon class="icon comment-icon"
                        >mdi-comment</v-icon
                      >
                      {{ post.comment_cnt }}
                    </div>
                  </div>
                </v-overlay>
              </v-fade-transition>
            </v-card>
          </template>
        </v-hover>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { getUserMadePost } from "@/api/feed.js";

export default {
  name: "Participated",
  props: {
    who_no: Number,
  },
  data: () => ({
    posts: [],
  }),
  created() {
    // 유저가 만든 포스트 목록
    getUserMadePost(this.who_no, (response) => {
      console.log("포스트목록");
      console.log(response.data.data);
      this.posts = response.data.data;
    });
  },
};
</script>

<style scoped>
.img-frame {
  cursor: pointer;
}
.info-wrapper {
  text-align: center;
}
.play-btn {
  float: right;
  width: 40px;
  color: #fff;
  margin-top: 8px;
}
.challenge-info {
  font-size: 20px;
}
.icon {
  margin-left: 6px;
  margin-right: 4px;
}
.hashtag-icon {
  margin-top: -4px;
}
.comment-icon {
  margin-right: -1px;
}
</style>
