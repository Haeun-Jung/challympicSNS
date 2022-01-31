<template>
  <div>
    <!-- 오른쪽 하단 챌린지/POST 선택 모달 -->
    <v-menu
          bottom
          offset-y
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              class="ma-2"
              v-bind="attrs"
              v-on="on"
              v-show="!hidden" color="#3396F4" dark fixed bottom right fab
            >
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </template>
          <v-list>
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="chooseDialog(item)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>
    <challenge-upload v-if="challenge" @close-modal="challenge=false"></challenge-upload>
    <post-upload v-if="post" @close-modal="post=false"></post-upload>
  </div>
</template>

<script>
import ChallengeUpload from "../upload/ChallengeUpload.vue";
import PostUpload from "../upload/PostUpload.vue";

export default {
  name: "FabButton",
  components: {
    ChallengeUpload,
    PostUpload
  },
  data: ()=> ({
    challenge: false, //true : ChallengeDialog열림, false : ChallengeDialog닫힘
    post: false, //true : PostDialog열림, false : PostDialog닫힘
    items: [
        {
          title: '챌린지 등록',
        },
        {
          title: '챌린지 참여',
        },
    ],
  }),
  methods: {
    chooseDialog(item) {
      if (item.title == '챌린지 참여') {
        this.post = true;
      } else {
        this.challenge = true;
      }
    }
  }
};
</script>

<style scoped>

</style>
