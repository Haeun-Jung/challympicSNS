<template>
  <div>
    <v-card-subtitle>
        <h2>인기 급상승<v-icon icon class="chart-icon">mdi-chart-line</v-icon></h2>
    </v-card-subtitle>
    <v-list-item-content class="side-contents-trend-container">
      <div v-for="(keyword, idx) in keywords" class="search-item" :key="keyword.id"  @click="goChallengePage(keyword.challenge_no)">
        <span class="word-blank">{{ idx+ 1 }}</span>
        <span>{{ keyword.challenge_title }}</span>
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
import { getTrend } from '@/api/side.js';
export default {
  name: 'PopularSearch',
  data() {
    return {
      keywords: []
    }
  },
  methods: {
    goChallengePage(challengeNo) {
      this.$router.push(`/challenge/${challengeNo}`);
    }
  },
  created(){
    getTrend(
      (response) => {
        this.keywords = response.data.data;
      }
    )
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