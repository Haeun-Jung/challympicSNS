<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="challengeList"
			item-key="challenge_no"
			class="elevation-1"
			:search="search"
			:sort-by.sync="sortBy"
			:sort-desc.sync="sortDesc"
		>
			<template v-slot:top>
				<v-text-field
					v-model="search"
					label="챌린지 번호, 챌린지명, 작성자로 챌린지 검색"
					class="mx-8"
					append-icon="mdi-clipboard-search"
				></v-text-field>
			</template>
			<!--search ends here-->
			<template #item.challenge_no="{ value }">
				<div class="mr-8">{{ value }}</div>
			</template>
			<template #item.challenge_report="{ value }">
				<div class="ml-2 justify-center">{{ value }}</div>
			</template>
			<template #item.challenge_title="{ item }">
				<router-link
					:to="{ path: `/challenge/${item.challenge_no}` }"
					style="text-decoration: none; color: inherit; mr-2"
				>
					{{ item.challenge_title }}
				</router-link>
			</template>
			<template #item.user_nickname="{ item }">
				<router-link
					v-if="item.user_nickname != `챌림픽`"
					:to="{ path: `/feed/${item.user_no}` }"
					style="text-decoration: none; color: inherit; mr-2"
				>
					{{ item.user_nickname }}
				</router-link>
				<router-link
					v-else
					to=""
					style="text-decoration: none; color: inherit; mr-2"
				>
					{{ item.user_nickname }}
				</router-link>
			</template>
			<template #item.duration="{ item }">
				{{ item.challenge_start }} ~ <br />
				{{ item.challenge_end }}
			</template>
			<template #item.actions="props">
				<div class="ml-3">
					<v-checkbox
						v-if="props.item == true"
						v-model="props.item.challenge_official"
						@click="makeOfficial(props.item)"
						color="success"
					></v-checkbox>
					<v-checkbox
						v-else
						v-model="props.item.challenge_official"
						color="success"
						@click="makeOfficial(props.item)"
					></v-checkbox>
				</div>
			</template>

			<template v-slot:item.delete="props">
				<v-icon small @click="goodbye(props.item)"> mdi-trash-can</v-icon>
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
				sortBy: "status",
				sortDesc: false,
				checkbox: true,
				//api 통신전 dummy data
				headers: [
					{
						text: "번호",
						align: "center",
						value: "challenge_no",
					},
					{
						text: "챌린지명",
						sortable: false,
						value: "challenge_title",
					},
					{
						text: "작성자",
						sortable: false,
						align: "center",
						value: "user_nickname",
					},
					{
						text: "참여수",
						sortable: true /*디비 이름 확인하기 */,
						value: "challenge_participants",
					},
					{
						text: "구독수" /*db 이름 확인하기 */,
						value: "challenge_subscribe_no",
					},

					{
						text: "신고수",
						value: "challenge_report",
					},
					{
						text: "기간",

						sortable: false,
						value: "duration",
					},
					{
						text: "공식 설정",
						value: "actions",
						align: "center",
						sortable: false,
					},
					{
						text: "삭제",
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
