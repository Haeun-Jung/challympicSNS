<template>
<<<<<<< HEAD
	<div>
		<v-list dense nav class="hidden-sm-and-up">
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
			<v-list-item>
				<dark-theme />
			</v-list-item>
			<!-- 여백 용 -> 후에 광고라던가.. -->
			<v-list-item class="side-empty-container"></v-list-item>
		</v-list>
		<v-list
			dense
			nav
			class="hidden-md-and-down"
			:color="$vuetify.theme.dark ? '#121212' : 'white'"
		>
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
			<v-list-item>
				<dark-theme />
			</v-list-item>
			<!-- 여백 용 -> 후에 광고라던가.. -->
			<v-list-item class="side-empty-container"></v-list-item>
		</v-list>
	</div>
=======
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
>>>>>>> 59b10cbf8e03668b5e7e2f3b3eda90cb0aab7a68
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
