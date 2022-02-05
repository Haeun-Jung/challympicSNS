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
                  <i v-if="post.isVideo" class='fas fa-play play-btn'></i>
                </v-img>
                <v-fade-transition>
                  <v-overlay
                    v-if="hover"
                    absolute
                    color="#2E2E2E"
                  >
                  <div class="info-wrapper">
                    <div class="challenge-info"><i class='fas fa-hashtag icon'></i>{{ post.challenge }}</div>
                    <div><i class='fas fa-heart icon'></i>&nbsp;{{ post.like }}</div>
                    <div><i class='fas fa-comment-alt icon'></i> {{ post.comment }}</div>
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
  width: 22px;
  color: #fff;
  margin-top: 10px;
}
.challenge-info {
  font-size: 20px;
}
.icon {
  margin-top: 8px;
  margin-right: 4px;
}
</style>