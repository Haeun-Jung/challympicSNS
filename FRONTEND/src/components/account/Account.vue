<template>
	<v-main>
		<v-row class="hidden-md-and-up">
			<mobile-menu />
		</v-row>
		<v-card class="hidden-sm-and-down">
			<v-row>
				<v-col md="3">
					<side />
				</v-col>
				<v-col md="8">
					<router-view />
				</v-col>
			</v-row>
		</v-card>
	</v-main>
</template>

<script>
	import Side from "./layout/desktop/Side.vue";
	import MobileMenu from "./layout/mobile/ExpensibleMenu.vue";
	export default {
		name: "Account",
		components: {
			Side,
			MobileMenu,
		},
	};
</script>

<style></style>
