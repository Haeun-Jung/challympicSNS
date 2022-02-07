<template>
	<v-container fluid>
		<v-layout row wrap>
			<v-flex>
				<div class="dark-theme-desktop hidden-md-and-down"></div>
				<v-card-subtitle><h3>다크모드</h3></v-card-subtitle>
			</v-flex>
			<v-flex>
				<div class="dark-theme-mobile hidden-sm-and-up"></div>
				<v-switch
					v-model="darkSwitch"
					color="#3396F4"
					class="pl-6 dark-theme-switch"
					inset
					@click="toggle_dark_mode"
				></v-switch>
			</v-flex>
		</v-layout>
	</v-container>
</template>

<script>
	export default {
		name: "DarkTheme",
		data() {
			return {
				darkSwitch: true,
			};
		},
		methods: {
			toggle_dark_mode: function () {
				this.$vuetify.theme.dark = !this.$vuetify.theme.dark;
				localStorage.setItem("dark_theme", this.$vuetify.theme.dark.toString());
			},
		},
		mounted() {
			const theme = localStorage.getItem("dark_theme");
			if (theme) {
				if (theme === "true") {
					this.$vuetify.theme.dark = true;
					this.darkSwitch = true;
				} else {
					this.$vuetify.theme.dark = false;
					this.darkSwitch = false;
				}
			} else if (
				window.matchMedia &&
				window.matchMedia("(prefers-color-scheme: dark)").matches
			) {
				this.$vuetify.theme.dark = true;
				this.darkSwitch = true;
				localStorage.setItem("dark_theme", this.$vuetify.theme.dark.toString());
			}
		},
	};
</script>

<style>
	.dark-theme-mobile {
		padding-bottom: 15%;
	}
	.dark-theme-desktop {
		padding-bottom: 3%;
	}
</style>
