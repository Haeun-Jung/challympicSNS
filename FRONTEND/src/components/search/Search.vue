<template>
	<!--이분 폐기시키는2중입니다-->
	<v-app>
		<router-view />
	</v-app>
</template>

<script>
	//	import ChallengeSearchResult from "./desktop/ChallengeSearchResult.vue";
	//	import TagSearchResult from "./desktop/TagSearchResult.vue";

	export default {
		name: "Search",
		components: {
			//	ChallengeSearchResult,
			//	TagSearchResult,
		},
		props: { search: String },
		data() {
			return {
				row: "challenge",
				//	isChallenge: false,
			};
		},
		watch: {
			$route(to, from) {
				if (to.path != from.path) {
					/* router path가 변경될 때마다 텍스트 리프레쉬. */
					this.keyword = "#" + this.$router.currentRoute.path.substring(8);
					this.$router.go();
				}
			},
			keyword: function () {
				this.onlyChallenge();
				this.keyword = "#" + this.$router.currentRoute.path.substring(8);
			},
		},
		computed: {
			//somehow .... split does not work...
			keyword() {
				const temp = decodeURIComponent(this.$router.currentRoute.path);
				return "#" + temp.substring(18).slice(0, -1);
			},
		},
		/* 페이지가 로딩되자마자 keyword 확인하기 */

		methods: {
			callPostRouter() {
				var pathKey = this.keyword.replace("#", "");
				alert(this.$router.currentRoute.path);
				var newPath = "/search/post/" + pathKey;
				alert(newPath);
				window.location.replace(newPath);
			},
			callchallengeRouter() {
				alert("move to challenge");
			},
		},
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
