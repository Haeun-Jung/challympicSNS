<template>
	<v-container>
		<v-data-iterator
			:items="challenges"
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
						<challenge-list :challenge="item" />
					</v-col>
				</v-row>
			</template>
		</v-data-iterator>
	</v-container>
</template>

<script>
	import ChallengeList from "../util/ChallengeList.vue";
	import { searchTagList } from "@/api/search.js";

	export default {
		name: "ChallengeSearchResult",
		components: {
			ChallengeList,
		},
		props: {
			search: String,
		},
		created() {
			console.log(
				"user_no " +
					this.$store.state.userStore.userInfo.user_no +
					"tag_content: " +
					this.search.substring(1)
			);
			searchTagList(
				this.searchKey,

				(response) => {
					this.challenges = response;
					console.log(this.challenges);
				},
				(error) => {
					console.log(error);
				}
			);
		},
		data() {
			return {
				searchKey: {
					user_no: this.$store.state.userStore.userInfo.user_no,
					tag_content: this.search.substring(1),
				},
				itemsPerPageArray: [3, 6, 9],
				searchProp: "",
				filter: {},

				sortDesc: false,
				page: 1,

				itemsPerPage: 6,
				challenges: [
					/*{
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
					},*/
				],
			};
		},
	};
</script>
