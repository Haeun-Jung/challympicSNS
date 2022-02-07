<template>
	<v-main>
		<v-card height="100%">
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
	</v-main>
</template>

<script>
	import AdminSideBar from "@/components/admin/layout/Side.vue";
	export default {
		name: "Admin",
		components: { AdminSideBar },
	};
</script>

<style></style>
