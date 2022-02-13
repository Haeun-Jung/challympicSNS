<template>
  <div>
    <v-card-subtitle>
        <h2>인기 급상승<v-icon icon class="chart-icon">mdi-chart-line</v-icon></h2>
    </v-card-subtitle>
    <v-list-item-content class="side-contents-trend-container">
      <div v-for="keyword in keywords" class="search-item" :key="keyword.id"  @click="goChallengePage(keyword.challengeNo)">
        <span class="word-blank">{{ keywords.indexOf(keyword) + 1 }}</span>
        <span>{{ keyword.title }}</span>
      </div>
    </v-list-item-content>
    <!-- 급상승 아이콘
    <v-icon color="red">mdi-chevron-up</v-icon>
    <v-icon color="blue">mdi-chevron-down</v-icon>
    <v-icon>mdi-minus</v-icon>
    -->
  </div>
</template>

<script>
export default {
  name: 'PopularSearch',
  data() {
    return {
      keywords: [
        {challengeNo: 1, title: "아이스_버킷_챌린지"},
        {challengeNo: 2, title: "미라클_모닝"},
        {challengeNo: 3, title: "요리"},
      ]
    }
  },
  methods: {
    goChallengePage(challengeNo) {
      this.$router.push(`/challenge/${challengeNo}`);
    }
  }
}
</script>

<style scoped>
.search-item {
  /*
  border: solid 0.5px;
  border-radius: 20px;
  */
  margin: 0.5rem 0rem;
  padding: 0.5rem;
}
.chart-icon {
  margin-left: 6px;
  margin-bottom: 2px;
}
.chart-icon::before {
  color: #DC143C;
}
.side-contents-trend-container{
  margin: 0 auto;
  width: 86%;
  cursor: pointer;
}
.word-blank {
  font-weight: bold;
  margin-right: 10px;
}
</style>