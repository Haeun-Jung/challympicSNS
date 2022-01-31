<template>
	<v-main>
		<tag-bar />
		<fab-button />
		<v-row>
			<v-col cols="2">
				<side />
			</v-col>
		</v-row>
		<!-- 리스트 공간
  -->
	</v-main>
</template>

<script>
	import TagBar from "../components/layout/TagBar.vue";
	import Side from "../components/layout/Side.vue";
	import FabButton from "../components/button/FabButton.vue";
	export default {
		components: { TagBar, Side, FabButton },
	};
</script>

<style></style>
