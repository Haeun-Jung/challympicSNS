<template>
	<v-app class="search-result-container">
		<!-- 모바일은 사이드 바가 없기 때문에 패딩 조절해줘야함 & span주면 키워드 길때 내려감-->
		<v-container fluid class="search-control-box-mobile hidden-sm-and-up">
			<v-list-item-title id="search-keyword">
				{{ keyword }}
			</v-list-item-title>
			<v-radio-group v-model="row" row>
				<v-radio label="챌린지" value="challenge" :disabled="isChallenge"></v-radio>
				<v-radio label="포스트" value="post"></v-radio>
			</v-radio-group>
		</v-container>
		<!--desktop-->
		<v-container fluid class="search-control-box-desktop hidden-md-and-down">
			<v-radio-group v-model="row" row>
				<span>
					<v-list-item-title id="search-keyword">
						{{ keyword }}
					</v-list-item-title>
				</span>
				<v-radio label="챌린지" value="challenge" :disabled="isChallenge"></v-radio>
				<v-radio label="포스트" value="post"></v-radio>
			</v-radio-group>
		</v-container>
		<div v-if="row === `challenge`">
			<challenge-search-result :search="keyword" />
		</div>
		<div v-else-if="row === `post`">
			<tag-search-result :search="keyword" />
		</div>
	</v-app>
</template>

<script>
import ChallengeSearchResult from "./desktop/ChallengeSearchResult.vue";
import TagSearchResult from "./desktop/TagSearchResult.vue";

export default {
	components: {
		ChallengeSearchResult,
		TagSearchResult,
	},
	name: "Search",
	props: { search: String },
	data() {
		return {
			row: "challenge",
			//keyword: "#오늘의요리",
			keyword: "#" + this.$router.currentRoute.path.substring(8),
			isChallenge: false,
		};
	},
	watch: {
		$route(to, from) {
			if (to.path != from.path) {
				/* router path가 변경될 때마다 텍스트 리프레쉬. */
				this.keyword = "#" + this.$router.currentRoute.path.substring(8);
			}
		},
		keyword: function() {
			this.onlyChallenge();
			this.keyword = "#" + this.$router.currentRoute.path.substring(8);
		}
	},
	/* 페이지가 로딩되자마자 keyword 확인하기 */
	mounted() {
		/* 챌린지는 포스트 목록만 보이기 */
		if (this.keyword.substring(1).includes('챌린지')) {
			this.isChallenge = true;
			this.row = 'post';
		} else {
			this.isChallenge = false;
		}
	},
	methods: {
		/* 챌린지는 포스트 목록만 보이기 */
		onlyChallenge() {
			if (this.keyword.substring(1).includes('챌린지')) {
				this.isChallenge = true;
				this.row = 'post';
			} else {
				this.isChallenge = false;
			}
		}
	}
};
</script>

<style scoped>
.search-result-container {
	padding-left: 0%;
}
.search-control-box-mobile {
	font-weight: bold;
}
.search-control-box-desktop {
	font-weight: bold;
}
.search-control-box-mobile #search-keyword {
	color: #3396f4;
	margin-right: 1.25em;
	font-size: 1.5rem !important;
}
.search-control-box-desktop #search-keyword {
	color: #3396f4;
	margin-right: 1.25em;
	font-size: 1.5rem !important;
}
</style>
