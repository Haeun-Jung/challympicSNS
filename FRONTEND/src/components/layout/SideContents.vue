<template>
	<v-list dense nav :color="$vuetify.theme.dark ? '#121212' : 'white'">
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
</template>

<script>
	import PopularSearch from "./PopularSearch.vue";
	import MyInterest from "./MyInterest.vue";
	import MySubscription from "./MySubscription.vue";
	import Ranking from "./Ranking.vue";
	import DarkTheme from "./DarkTheme.vue";

	export default {
		name: "SideContents",
		components: {
			PopularSearch,
			MyInterest,
			MySubscription,
			Ranking,
			DarkTheme,
		},
	};
</script>
<style scoped>
	.side-empty-container {
		height: 300px; /**side를 sticky로 만드는게 나을지..만약 그러면 앱바도 sticky -> 스크롤할때 별로 안이쁨 */
		overflow: hidden;
		/*background-color: pink;*/
	}
</style>
