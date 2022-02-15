<template>
  <div>
    <v-card-subtitle>
      <h2>이달의 도전왕</h2>
    </v-card-subtitle>
    <v-list-item-content>
      <div v-for="champion in champions" class="rank-item" :key="champion.id">
        <span class="number black--text">{{
          champions.indexOf(champion) + 1
        }}</span>
        <span class="nickname" @click="moveToFeed(champion)">{{
          champion
        }}</span>
      </div>
    </v-list-item-content>
  </div>
</template>

<script>
export default {
  name: "Ranking",
  data() {
    return {
      champions: ["ox_zung", "official_kep1er", "enhypen"],
    };
  },
  methods: {
    moveToFeed(champion) {
      this.$router.push("/feed/" + champion);
    },
  },
};
</script>

<style scoped>
.rank-item {
  display: flex;
  align-items: center;
  margin: 0.5rem 0;
}
.number {
  text-align: center;
  line-height: 1.3;
  background: rgb(255, 243, 74);
  width: 20px;
  height: 20px;
  border-radius: 50%;
  margin: 0 0.5rem;
}
.nickname {
  cursor: pointer;
}
.main-side-menu-contents {
  margin: 1%;
  padding-left: 7%;
  padding-right: 7%;
}
</style>
