<template>
	<v-card v-if="isMobile()">
		<v-container fluid>
			<v-layout row class="mt-10">
				<mobile-menu />
			</v-layout>
		</v-container>
	</v-card>
	<v-card v-else height="100%">
		<v-container fluid>
			<v-layout row class="mt-14">
				<v-col md="1" />
				<v-col md="2">
					<side />
				</v-col>
				<v-col>
					<router-view />
				</v-col>
				<v-col md="2" />
			</v-layout>
		</v-container>
	</v-card>
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
		methods: {
			isMobile() {
				if (
					/Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(
						navigator.userAgent
					)
				) {
					return true;
				} else {
					return false;
				}
			},
		},
	};
</script>

<style scoped>
	.mobile-profile-gap {
		height: 300px;
		background-color: pink;
	}
</style>
