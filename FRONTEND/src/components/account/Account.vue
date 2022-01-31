<template>
	<v-main>
		<tool-bar />
		<!-- 데스크탑 웹 화면  class="hidden-sm-and-down"-->
		<v-card app>
			<v-row class="hidden-sm-and-down">
				<v-col md="3">
					<side />
				</v-col>
				<v-col md="8">
					<router-view />
				</v-col>
			</v-row>
			<!--mobile-->
			<v-row class="hidden-md-and-up">
				<mobile-menu />
			</v-row>
		</v-card>
	</v-main>
</template>

<script>
	import ToolBar from "../layout/ToolBar.vue";
	import Side from "./layout/desktop/Side.vue";
	import MobileMenu from "./layout/mobile/ExpensibleMenu.vue";
	export default {
		name: "Account",
		components: {
			Side,
			ToolBar,
			MobileMenu,
		},
	};
</script>

<style></style>
