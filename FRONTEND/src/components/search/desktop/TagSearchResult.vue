<template>
	<v-container>
		<!--
		프롭스로 {{ search }} 태그-> 해당 태그를 포함하는 포스트 가져오기
		 api(/challenge/{challengeNo}/post) 로
		
		-->
		<v-data-iterator
			:items="posts"
			:pagination.sync="pagination"
			:footer-props="{
				'items-per-page-options': [5, 10, 15, 20],
			}"
			:items-per-page="6"
		>
			<template v-slot:default="props">
				<v-row>
					<v-col
						v-for="item in props.items"
						:key="item.name"
						cols="12"
						sm="6"
						md="6"
						lg="4"
					>
						<!--
						<div id="moew">
							:challenge="challenges[`{{ item.challenge_no }}`]"
							{{ item.challenge_no }}dd
						</div>
						-->
						<tag-search-list :post="item" />
					</v-col>
				</v-row>
			</template>
		</v-data-iterator>
	</v-container>
</template>

<script>
	import TagSearchList from "../util/TagSearchList.vue";
	export default {
		name: "TagSearchResult",
		components: {
			TagSearchList,
		},
		props: {
			search: String,
		},
		data() {
			return {
				itemsPerPageArray: [3, 6, 9],
				searchProp: "",
				filter: {},
				sortDesc: false,
				page: 1,

				itemsPerPage: 6,
				posts: [
					{
						post_no: 1,
						post_like: true,
						challenge_title: "챌린지 제목 1",
						post_content: "내용에 태그가 포함??#프론트에서#분리해서뿌리나요?",
						post_likes: 300,
						post_comments: 3,
						challenger: "김싸피",
						content:
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
					},
					{
						post_no: 2,
						post_like: false,
						challenge_title: "아이스버킷챌린지",
						post_content: "저도..참여해봤어요..#차가움#공익#공식",
						post_likes: 0,
						post_comments: 3,
						challenger: "이싸피",
						content:
							"https://caiogondim.github.io/vertical-video-with-side-blur/example/vertical-video-1.mp4",
					},
					{
						post_no: 3,
						post_like: false,
						challenge_title: "미라클모닝",
						post_content: "#미이라크을#아침#ㅜㅜ",
						post_likes: 15,
						post_comments: 30,
						challenger: "박싸피",
						content:
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"https://caiogondim.github.io/vertical-video-with-side-blur/example/vertical-video.mov",
					},
					{
						post_no: 4,
						post_like: false,
						challenge_title: "김싸피덤벼",
						post_content: "얍얍#1대1",
						post_likes: 3521565,
						post_comments: 345465,
						challenger: "오싸피",
						content:
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
					},
				],
			};
		},
	};
</script>

<style>
	#app iframe {
		/*width: 60%;
		height: 100%;
		margin-left: 8%;
		height: 500px;
        */
	}
	#moew {
		background-color: pink;
		height: 600px;
	}
</style>
