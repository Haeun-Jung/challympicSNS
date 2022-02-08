<template>
	<v-card height="85vh" elevation="0">
		<v-card-title> 현황 · 통계</v-card-title>
		<v-divider />
		<stat-table />
	</v-card>
</template>

<script>
	import StatTable from "./util/StatTable.vue";
	export default {
		name: "StatManagement",
		components: { StatTable },
	};
</script>

<style></style>
