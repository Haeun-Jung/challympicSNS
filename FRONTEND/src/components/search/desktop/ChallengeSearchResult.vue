<template>
	<v-container>
		<!--
		프롭스로 {{ search }} 가져옴 -> api (/search/tag/{tag})로 해당 결과 요청 ㄴ
		해당 챌린지 목록이 리스트로 나옴 ㄴ 그리고 지금 가져오려는게 해당 챌린지
		리스트 이름이 들어오는 껍데기 -> api(/challenge/{challengeNo}/post) 로
		포스트정보만 또 가져옴. 그 가져온 리스트는 child component로 껍데기 안에
		동영상으로 넣는다
		-->
		<v-data-iterator
			:items="challenges"
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
						<challenge-list :challenge="item" />
					</v-col>
				</v-row>
			</template>
		</v-data-iterator>
	</v-container>
</template>

<script>
	import ChallengeList from "../util/ChallengeList.vue";
	export default {
		name: "ChallengeSearchResult",
		components: {
			ChallengeList,
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
				challenges: [
					{
						challenge_no: 1,
						challenge_subscribe: true, //해당 유저가 챌린지 구독햇는지안햇는지
						challenge_title: "챌린지 제목 1",
						challenge_content: "챌린지 내용 1",
						challenge_tag: "#챌린지 #테그",
					},
					{
						challenge_no: 2,
						challenge_title: "챌린지 제목 2",
						challenge_content: "챌린지 내용2", //분리되서 오는거 맞나용?
						challenge_tag: "#챌린지 #테그", //아니면 제가 나누는건가용?
						challenge_subscribe: true, //해당 유저가 챌린지 구독햇는지안햇는지
					},
					{
						challenge_no: 3,
						challenge_subscribe: true, //해당 유저가 챌린지 구독햇는지안햇는지
						challenge_title: "챌린지 제목 3",
						challenge_content: "챌린지 내용3",
						challenge_tag: "#챌린지 #테그",
					},
					{
						challenge_no: 4,
						challenge_title: "챌린지 제목 4",
						challenge_content: "챌린지 내용4",
						challenge_tag: "#챌린지 #테그",
						challenge_subscribe: false, //해당 유저가 챌린지 구독햇는지안햇는지
					},
					{
						challenge_no: 5,
						challenge_title: "챌린지 제목 5",
						challenge_tag: "#챌린지 #테그",
						challenge_content: "챌린지 내용5",
						challenge_subscribe: false, //해당 유저가 챌린지 구독햇는지안햇는지
					},
					{
						challenge_no: 6,
						challenge_title: "챌린지 제목 5",
						challenge_tag: "#챌린지 #테그",
						challenge_content: "챌린지 내용5",
						challenge_subscribe: false, //해당 유저가 챌린지 구독햇는지안햇는지
					},
					{
						challenge_no: 7,
						challenge_title: "챌린지 제목 5",
						challenge_tag: "#챌린지 #테그",
						challenge_content: "챌린지 내용5",
						challenge_subscribe: false, //해당 유저가 챌린지 구독햇는지안햇는지
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
