<template>
	<v-main>
		<v-card height="100%" v-if="!isMobile()">
			<v-container fluid>
				<v-layout> </v-layout>
				<v-row>
					<v-col md="1" />
					<v-col md="2">
						<admin-side-bar />
					</v-col>
					<v-col>
						<router-view />
					</v-col>
					<v-col md="2" />
				</v-row>
			</v-container>
		</v-card>
		<v-card height="100vh" max-width="97%" color="transparent" row v-else>
			<admin-mobile />
		</v-card>
	</v-main>
</template>

<script>
	import AdminSideBar from "@/components/admin/layout/Side.vue";
	import AdminMobile from "@/components/admin/layout/Mobile.vue";
	export default {
		name: "Admin",
		components: { AdminSideBar, AdminMobile },
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

<style></style>
