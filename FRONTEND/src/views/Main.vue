<template>
	<v-main>
		<tag-bar />
		<fab-button />
		<v-container fluid>
			<v-layout row>
				<!-- 패딩pr-16 16준게 최선..사이드바 어디에놔도이상함..-->
				<!--데스크탑-->
				<v-flex xs2 class="hidden-md-and-down" />
				<v-flex xs2 class="hidden-md-and-down">
					<v-container fluid>
						<v-layout column>
							<v-flex>
								<side />
							</v-flex>
						</v-layout>
					</v-container>
				</v-flex>
				<!-- 반응형 처리 -->
				<v-flex
					:class="{
						'pa-4': $vuetify.breakpoint.smAndDown,
						'ma-3': $vuetify.breakpoint.mdAndUp,
					}"
					xs12
					sm6
					md6
				>
					<v-layout column>
						<v-flex>
							<router-view />
						</v-flex>
					</v-layout>
				</v-flex>
				<v-flex xs2 class="hidden-md-and-down" />
			</v-layout>
		</v-container>
	</v-main>
</template>

<script>
	import TagBar from "../components/layout/TagBar.vue";
	import Side from "../components/layout/Side.vue";
	import FabButton from "../components/button/FabButton.vue";
	export default {
		components: { TagBar, Side, FabButton },
	};
</script>
