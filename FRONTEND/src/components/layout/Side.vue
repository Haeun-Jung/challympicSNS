<template>
	<v-card class="hidden-sm-and-down" flat elevation="100">
		<side-contents />
	</v-card>
</template>

<script>
	import SideContents from "../layout/SideContents.vue";

	export default {
		name: "Side",
		components: { SideContents },

		data() {
			return {
				user: false,
			};
		},
	};
</script>

<style scoped>
	.sticky-card {
		position: fixed;
	}
</style>
