<template>
	<v-card height="85vh" elevation="0">
		<v-card-title>챌린지 관리</v-card-title>
		<v-divider />
		<challenge-table />
	</v-card>
</template>

<script>
	import ChallengeTable from "@/components/admin/util/ChallengeTable.vue";
	export default {
		name: "ChallengeManagement",
		components: { ChallengeTable },
	};
</script>

<style></style>
