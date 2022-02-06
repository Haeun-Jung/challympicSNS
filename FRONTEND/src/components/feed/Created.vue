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
                    <div class="participated-info"><i class='fas fa-user-plus icon'></i>{{ post.participated }}<v-icon icon class="icon bookmark-icon">mdi-bookmark</v-icon>{{ post.subscribe }}</div>
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
  name: "Created",
  data: ()=> ({
    posts: [
      {
        challenge: '운동_챌린지',
        isVideo: true,
        participated: 20,
        subscribe: 10,
        src: 'https://randomuser.me/api/portraits/men/1.jpg'
      },
      {
        challenge: '코딩_챌린지',
        isVideo: true,
        participated: 25,
        subscribe: 13,
        src: 'https://randomuser.me/api/portraits/men/2.jpg'
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
  margin-bottom: 4px;
}
.participated-info {
  font-size: 18px;
}
.icon {
  margin-top: 8px;
  margin-left: 6px;
  margin-right: 4px;
}
.bookmark-icon {
  margin-top: -2px;
}
</style>