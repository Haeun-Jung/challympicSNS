<template>
	<v-main>
		<tool-bar />
		<tag-bar />
		<side />
		<!-- 리스트 공간 라우터 뷰 필요 
  -->
	</v-main>
</template>

<script>
	import ToolBar from "../components/layout/ToolBar.vue";
	import TagBar from "../components/layout/TagBar.vue";
	import Side from "../components/layout/Side.vue";

	export default {
		components: { ToolBar, TagBar, Side },
	};
</script>

<style></style>
