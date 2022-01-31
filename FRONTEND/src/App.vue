<template>
	<v-app class="app">
		<tool-bar />
		<router-view />
	</v-app>
</template>

<script>
	import ToolBar from "./components/layout/ToolBar.vue";

	export default {
		name: "App",
		components: { ToolBar },
		data: () => ({}),
	};
</script>

<style scoped></style>
