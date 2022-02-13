<template>
	<v-card height="85vh" elevation="0">
		<v-card-title>푸시 알림</v-card-title>
		<v-divider />
		<v-container>
			<v-row md="1">
				<v-col md="1"> </v-col>
				<v-col md="8">
					<v-list-item-title class="pt-5">알림 허용</v-list-item-title>
				</v-col>
				<v-spacer />
				<v-col>
					<v-switch v-model="switch1" inset></v-switch>
				</v-col>
			</v-row>
		</v-container>
	</v-card>
</template>

<script>
	export default {
		name: "PasswordUpdate",
		data() {
			return {
				switch1: this.$store.state.userStore.pushAlert,
			};
		},
		methods: {},
	};
</script>

<style></style>
