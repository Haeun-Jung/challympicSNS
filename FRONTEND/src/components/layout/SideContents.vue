<template>
  <v-list dense nav>
    <v-list-item>
      <popular-search></popular-search>
    </v-list-item>
    <v-divider />
    <v-list-item>
      <my-interest></my-interest>
    </v-list-item>
    <v-divider />
    <v-list-item>
      <my-subscription></my-subscription>
    </v-list-item>
    <v-divider />
    <v-list-item>
      <ranking></ranking>
    </v-list-item>
    <v-divider />
    <div class="main-side-switch-container">
      <v-list-item>
        <v-card-subtitle>
          <h3>다크모드</h3>
        </v-card-subtitle>
        <v-switch
          v-model="dark"
          color="#3396F4"
          class="dark-theme-switch"
          @click="$vuetify.theme.dark = !$vuetify.theme.dark"
        ></v-switch>
      </v-list-item>
    </div>
  </v-list>
</template>

<script>
import PopularSearch from './PopularSearch.vue'
import MyInterest from './MyInterest.vue'
import MySubscription from './MySubscription.vue'
import Ranking from './Ranking.vue'

export default {
  name: "SideContents",
  components: {
    PopularSearch,
    MyInterest,
    MySubscription,
    Ranking
  },
  data() {
    return {
      dark: false,
    };
  },
};
</script>

<style scoped>
.main-side-switch-container {
  display: flex;
  justify-content: space-between;
}
</style>
