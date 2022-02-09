<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="challengeList"
			item-key="challenge_no"
			class="elevation-1"
			dense
			:search="search"
			:sort-by.sync="sortBy"
			:sort-desc.sync="sortDesc"
			:hide-default-footer="true"
		>
			<!--search ends here-->
			<template #item.challenge_participants="{ item }">
				<div v-if="item.challenge_report === 0" class="mr-8"></div>
				<div v-else class="mr-8">{{ item.challenge_participants }}</div>
			</template>

			<template #item.challenge_title="{ item }">
				<div v-if="item.challenge_report === 0"></div>
				<div v-else>
					<router-link
						:to="{ path: `/challenge/${item.challenge_no}` }"
						style="text-decoration: none; color: inherit; mr-2"
					>
						{{ item.challenge_title }}
					</router-link>
				</div>
			</template>

			<template #item.challenge_report="{ item }">
				<div v-if="item.challenge_report === 0"></div>
				<div v-else>
					{{ item.challenge_report }}
				</div>
			</template>

			<template v-slot:item.delete="props">
				<v-icon
					v-if="props.item.challenge_report > 0"
					small
					@click="goodbye(props.item)"
				>
					mdi-trash-can</v-icon
				>
			</template>
		</v-data-table>
	</v-container>
</template>

<script>
	//import {deleteUser, userList} from "@/api/member";
	export default {
		name: "UserList",
		data() {
			return {
				search: "",
				page: "",
				sortBy: "challenge_report",
				sortDesc: true,
				//api 통신전 dummy data
				headers: [
					{
						text: "챌린지",
						sortable: false,
						value: "challenge_title",
					},
					{
						text: "참여",
						sortable: false,
						value: "challenge_participants",
					},
					{
						text: "신고",
						sortable: false,
						value: "challenge_report",
						//		value: "action",
					},
					{
						value: "delete",
						align: "center",
						sortable: false,
					},
				],
				challengeList: [
					{
						challenge_no: 1,
						challenge_title: "아이스버킷챌린지",
						user_nickname: "챌림픽",
						challenge_participants: 13254,
						challenge_subscribe_no: 43554,
						challenge_start: "2022-01-26",
						challenge_end: "2022-07-26",
						challenge_report: 4,
						challenge_official: true,
					},
					{
						challenge_no: 17,
						challenge_title: "김싸피덤벼",
						user_nickname: "이싸피",
						challenge_participants: 1,
						challenge_subscribe_no: 0,
						challenge_start: "2021-12-5",
						challenge_end: "2022-01-02",
						challenge_report: 415,
						challenge_official: false,
					},
					{
						challenge_no: 7,
						challenge_title: "공식",
						user_nickname: "챌림픽",
						challenge_participants: 0,
						challenge_subscribe_no: 0,
						challenge_start: "2022-02-05",
						challenge_end: "2022-06-30",
						challenge_report: 0,
						challenge_official: true,
					},
				],
			};
		},

		methods: {
			goodbye(item) {
				var message = "정말 " + item.challenge_title + "을 삭제하시겠습니까?";
				if (confirm(message)) {
					alert(item.challenge_title + "를 삭제하였습니다.");
					//item.user_no로 회원 정지 api통신
					//deleteUser(this.item);
				} else {
					alert("취소하였습니다.");
				}
				//locaation.reload();
			},
			makeOfficial(item) {
				if (!item.challenge_official) {
					alert("공식 챌린지를 해제했습니다.");
					this.item.challenge_official = false;
				} else {
					alert("공식 챌린지에 등록했습니다.");
					this.item.challenge_official = true;
				}
			},
		},
		/*	Api 통신 용
		created() {
			let param = {
				pg: 1,
				spp: 20,
				key: null,
				word: null,
			};
			userList(
				param,
				(response) => {
					this.userList = response.data;
				},
				(error) => {
					console.log(error);
				}
			);
		},*/
	};
</script>

<style></style>
