<template>
  <div>
    <!-- 게이지바 -->
    <div class="gauge-items">
      <strong class="ml-auto nickname">{{ postList[0].postInfo.nickname }}</strong>
      <v-progress-linear
        background-color="#3396F4"
        color="pink lighten-1"
        :value="value"
        height="20px"
      >
        <strong>{{ Math.ceil(value) }}</strong>
        <strong>{{ 100 - Math.ceil(value) }}</strong>
      </v-progress-linear>
      <strong class="mr-auto nickname">{{ postList[1].postInfo.nickname }}</strong>
    </div>
    <v-row>
      <v-col>
        <post-item :post="postList[0]" :type="type" />
      </v-col>
      <v-col>
        <post-item :post="postList[1]" :type="type" />
      </v-col>
    </v-row>
  </div>
</template>

<script>
import PostItem from "@/components/post/PostItem.vue";
export default {
  name: "BattleItem",
  components: { PostItem },
  props: {
    type: String,
    postList: Array,
  },
  computed: {
    value() {
      let all = this.postList[0].postInfo.likeCnt + this.postList[1].postInfo.likeCnt;
      let post1 = this.postList[0].postInfo.likeCnt;
      return (post1 / all) * 100;
    },
  },
};
</script>

<style scoped>
.gauge-items {
  display: flex;
  align-items: center;
}
.nickname {
  font-size: 20px;
}
.v-progress-linear {
  display: block;
  width: 400px;
  margin: 0 auto;
  border-radius: 10px;
}
::v-deep .v-progress-linear__content {
  justify-content: space-between;
  padding: 0 1rem;
  color: white;
}
</style>