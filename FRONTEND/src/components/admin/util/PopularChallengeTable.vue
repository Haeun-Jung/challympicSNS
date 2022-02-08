<template>
	<v-container class="official-table">
		<v-data-table
			:headers="headers"
			:items="challengeList"
			item-key="challenge_no"
			class="elevation-1 official-table"
			dense
			:hide-default-footer="true"
		>
			<template #item.challenge_title="{ item }">
				<router-link
					:to="{ path: `/challenge/${item.challenge_no}` }"
					style="text-decoration: none; color: inherit; mr-2"
				>
					<div
						style="
							text-overflow: ellipsis;
							overflow-x: hidden;
							overflow: hidden;
							white-space: nowrap;
						"
					>
						<div v-if="item.challenge_official == true">
							{{ item.challenge_title }}✅
						</div>
						<div v-else>
							{{ item.challenge_title }}
						</div>
					</div>
				</router-link>
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
				sortBy: "challenge_participants",
				sortDesc: true,
				checkbox: true,
				//api 통신전 dummy data
				headers: [
					{
						text: "챌린지명",
						width: "2%",
						sortable: false,
						value: "challenge_title",
					},
					{
						text: "참여",
						width: "7px",
						sortable: false /*디비 이름 확인하기 */,
						value: "challenge_participants",
					},
					{
						text: "구독" /*db 이름 확인하기 */,
						width: "7px",
						sortable: false,
						value: "challenge_subscribe_no",
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
