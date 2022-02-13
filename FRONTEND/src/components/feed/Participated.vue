<template>
    <v-container fluid>
      <v-row dense>
        <v-col
          v-for="post in posts"
          :key="post.challenge"
          :cols="4"
        >
          <v-hover>
            <template v-slot:default="{ hover }">
              <v-card
                class="mx-auto img-frame"
                max-width="344"
              >
                <v-img :src="post.src">
                  <!-- 비디오일때만 -->
                  <v-icon v-if="post.isVideo" icon class="play-btn">mdi-play</v-icon>
                </v-img>
                <v-fade-transition>
                  <v-overlay
                    v-if="hover"
                    absolute
                    color="#2E2E2E"
                  >
                  <div class="info-wrapper">
                    <div class="challenge-info"><v-icon icon class="icon hashtag-icon">mdi-pound</v-icon>{{ post.challenge }}</div>
                    <div><v-icon icon class="icon">mdi-heart</v-icon>{{ post.like }} <v-icon icon class="icon comment-icon">mdi-comment</v-icon> {{ post.comment }}</div>
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
import { getUserMadePost } from '@/api/feed.js';

export default {
  name: "Participated",
  data: ()=> ({
    posts: [
      {
        challenge: '운동_챌린지',
        isVideo: true,
        like: 20,
        comment: 130,
        src: 'https://randomuser.me/api/portraits/women/1.jpg'
      },
      {
        challenge: '코딩_챌린지',
        isVideo: true,
        like: 25,
        comment: 100,
        src: 'https://randomuser.me/api/portraits/women/2.jpg'
      },
      {
        challenge: '음식_챌린지',
        isVideo: false,
        like: 30,
        comment: 40,
        src: 'https://randomuser.me/api/portraits/women/3.jpg'
      },
      {
        challenge: '술_챌린지',
        isVideo: false,
        like: 10,
        comment: 30,
        src: 'https://randomuser.me/api/portraits/women/4.jpg'
      },
      {
        challenge: '아무노래_챌린지',
        isVideo: true,
        like: 13,
        comment: 220,
        src: 'https://randomuser.me/api/portraits/women/5.jpg'
      }
    ],
  }),
  computed: {
    who_no() {
      const temp = decodeURIComponent(this.$router.currentRoute.path);
      const chars = temp.split("/");
        return chars[2];
      },
  },
  created() {
    // 유저가 만든 포스트 목록
    getUserMadePost(
      this.who_no,
      (response) => {
        console.log("포스트목록");
        console.log(response.data);
      }
    )
  }
}
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