<template>
	<v-card height="85vh" elevation="0">
		<v-card-title> 현황 · 통계</v-card-title>
		<v-divider />

		<v-conatiner fluid>
			<v-layout row>
				<v-flex xs6>
					<v-layout column>
						<v-container>
							<v-list-item-title class="ma-2; mt-5">
								<v-icon color="#3396F4">mdi-comment-question</v-icon>
								현황
							</v-list-item-title>
							<graph />
						</v-container>
						<v-container>
							<v-list-item-title class="ma-2; mt-5">
								<v-icon color="#3396F4">mdi-alert</v-icon>
								신고 챌린지 목록
							</v-list-item-title>
							<reported-challenge />
						</v-container>
					</v-layout>
				</v-flex>
				<v-flex xs6>
					<!-- 왼쪽 콜륨 -->

					<v-layout column class="mt-5">
						<v-container>
							<v-flex>
								<v-list-item-title class="ma-2;mt-5">
									<v-icon color="#3396F4">mdi-trophy-variant</v-icon>인기 챌린지
									목록
								</v-list-item-title>
								<popular-challenge-table />
							</v-flex>
						</v-container>
					</v-layout>
				</v-flex>

				<!-- 오른쪽 콜륨 --> </v-layout
			><!-- end of row -->
		</v-conatiner>
	</v-card>
</template>

<script>
	import PopularChallengeTable from "./util/PopularChallengeTable.vue";
	//	import UnansweredTable from "./util/UnansweredTable.vue";
	import Graph from "./util/Graph.vue";
	import ReportedChallenge from "./util/ReportedChallenge.vue";
	export default {
		name: "StatManagement",
		components: { PopularChallengeTable, Graph, ReportedChallenge },
	};
</script>

<style></style>
