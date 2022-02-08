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
                    <div class="challenge-info info-font"><v-icon icon class="icon hashtag-icon">mdi-pound</v-icon>{{ post.challenge }}</div>
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
export default {
  name: "Like",
  data: ()=> ({
    posts: [
      {
        challenge: '운동_챌린지',
        isVideo: true,
        like: 20,
        comment: 130,
        src: 'https://randomuser.me/api/portraits/women/6.jpg'
      },
      {
        challenge: '코딩_챌린지',
        isVideo: true,
        like: 25,
        comment: 100,
        src: 'https://randomuser.me/api/portraits/women/7.jpg'
      },
      {
        challenge: '음식_챌린지',
        isVideo: true,
        like: 30,
        comment: 40,
        src: 'https://randomuser.me/api/portraits/women/8.jpg'
      },
      {
        challenge: '술_챌린지',
        isVideo: false,
        like: 10,
        comment: 30,
        src: 'https://randomuser.me/api/portraits/women/9.jpg'
      },
      {
        challenge: '아무노래_챌린지',
        isVideo: true,
        like: 13,
        comment: 220,
        src: 'https://randomuser.me/api/portraits/women/10.jpg'
      },
      {
        challenge: '노트북_챌린지',
        isVideo: false,
        like: 10,
        comment: 30,
        src: 'https://randomuser.me/api/portraits/women/11.jpg'
      },
      {
        challenge: '귀요미_챌린지',
        isVideo: true,
        like: 13,
        comment: 220,
        src: 'https://randomuser.me/api/portraits/women/12.jpg'
      }
    ],
  }),
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
.info-font {
  font-weight: 500;
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