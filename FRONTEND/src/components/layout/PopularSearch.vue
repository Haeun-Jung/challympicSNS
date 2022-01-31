<template>
  <div>
    <v-card-subtitle>
        <h2>인기 급상승</h2>
    </v-card-subtitle>
    <v-list-item-content class = "side-contents-trend-container">
      <div v-for="keyword in keywords" class="search-item" :key="keyword.id">
        <span>{{ keywords.indexOf(keyword) + 1 }}</span>
        <span>{{ keyword }}</span>
      </div>
    </v-list-item-content>
    <v-icon color="red">mdi-chevron-up</v-icon>
    <v-icon color="blue">mdi-chevron-down</v-icon>
    <v-icon>mdi-minus</v-icon>
  </div>
</template>

<script>
export default {
  name: 'PopularSearch',
  data() {
    return {
      keywords: ["아이스버킷챌린지", "미라클 모닝", "요리"]
    }
  }
}
</script>

<style scoped>
.search-item {
  display: flex;
  justify-content: space-between;
  border: solid 0.5px;
  border-radius: 20px;
  margin: 0.5rem;
  padding: 0.5rem;
}
.side-contents-trend-container{
  margin: 0 auto;
  width: 90%;
}

</style>