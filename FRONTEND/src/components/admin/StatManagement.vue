<template>
	<v-card height="85vh" elevation="0">
		<v-card-title> 현황 · 통계</v-card-title>
		<v-divider />

		<v-conatiner fluid>
			<v-layout row>
				<v-flex xs5>
					<!-- 왼쪽 콜륨 -->
					<v-layout column>
						<v-container fluid>
							<v-list-item-title class="ma-2">
								<v-icon color="#3396F4">mdi-trophy-variant</v-icon>인기 챌린지
								목록
							</v-list-item-title>
							<v-layout row>
								<popular-challenge-table />
							</v-layout>
							<v-layout row>
								<v-list-item-title class="ma-2">
									<v-icon color="#3396F4">mdi-comment-question</v-icon>
									답변이 필요한 문의
								</v-list-item-title>
								<popular-challenge-table />
							</v-layout>
						</v-container>
					</v-layout>
				</v-flex>

				<!-- 오른쪽 콜륨 -->
				<v-flex xs6>
					<v-layout column>
						<v-list-item-title class="ma-2">
							<v-icon color="#3396F4">mdi-comment-question</v-icon>
							답변이 필요한 문의
						</v-list-item-title>
						<unanswered-table />
					</v-layout>
				</v-flex> </v-layout
			><!-- end of row -->
		</v-conatiner>
	</v-card>
</template>

<script>
	import PopularChallengeTable from "./util/PopularChallengeTable.vue";
	import UnansweredTable from "./util/UnansweredTable.vue";
	export default {
		name: "StatManagement",
		components: { PopularChallengeTable, UnansweredTable },
	};
</script>

<style></style>
