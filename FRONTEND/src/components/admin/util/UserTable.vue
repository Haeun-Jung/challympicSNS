<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="userList"
			item-key="userid"
			class="elevation-1"
			:search="search"
			:sort-by.sync="sortBy"
			:sort-desc.sync="sortDesc"
		>
			<template v-slot:top>
				<v-text-field
					v-model="search"
					label="닉네임, 이메일로 회원 검색"
					class="mx-8"
					append-icon="mdi-account-search"
				></v-text-field>
			</template>
			<!--search ends here-->
			<template #item.user_no="{ value }">
				<div class="mr-4">{{ value }}</div>
			</template>
			<template #item.user_nickname="{ value }">
				{{ value }}
			</template>
			<template #item.user_email="{ value }">
				<a :href="`mailto:${value}`"> {{ value }} </a>
			</template>

			<template #item.user_inactive="{ value }">
				<v-row>
					<v-chip v-if="value == true" color="grey" text-color="white">
						inactive
					</v-chip>
					<v-chip
						v-else
						color="blue lighten-1"
						text-color="white"
						text-align="center"
					>
						✓ active
					</v-chip>
				</v-row>
			</template>

			<template v-slot:item.actions="props">
				<v-icon small @click="goodbye(props.item)"> mdi-close-octagon</v-icon>
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

				//api 통신전 dummy data
				headers: [
					{
						text: "번호",
						align: "center",
						value: "user_no",
						sortable: false,
					},
					{
						text: "닉네임",
						sortable: false,
						value: "user_nickname",
					},
					{
						text: "이메일",
						sortable: false,
						value: "user_email",
					},
					{
						text: "신고 횟수",
						sortable: true,
						value: "user_reportCount",
					},
					{
						text: "정지 여부",
						value: "user_inactive",
					},

					{
						text: "회원 정지",
						value: "actions",
						align: "center",
						sortable: false,
					},
				],
				userList: [
					{
						user_no: 1,
						user_nickname: "김싸피",
						user_email: "kimSsafy@gmail.com",
						user_reportCount: 10121,
						user_inactive: false,
					},
					{
						user_no: 2,
						user_nickname: "이싸피",
						user_email: "leeSsafy@mail.com",
						user_reportCount: 21,
						user_inactive: false,
					},
					{
						user_no: 3,
						user_nickname: "박싸피",
						user_email: "parkSsafy@hanmail.net",
						user_reportCount: 5421,
						user_inactive: false,
					},
					{
						user_no: 4,
						user_nickname: "간싸피",
						user_email: "ganSsafy@gmail.com",
						user_reportCount: 11321,
						user_inactive: true,
					},
				],
			};
		},
		methods: {
			goodbye(item) {
				console.log(item.user_no);
				//this.ban = item;
				//this.ban = Object.assign({}, item);
				//console.log(this.ban.user_nickname);
				var message =
					"정말 " + item.user_nickname + "님을 비활성화 시키겠습니까?";
				if (confirm(message)) {
					alert("회원을 비활성화 시켰습니다.");
					//item.user_no로 회원 정지 api통신
					//deleteUser(this.item);
				} else {
					alert("취소하였습니다.");
				}

				//locaation.reload();
			},
			enterSelect() {
				this.checked = this.selected.map((e) => e.userid);
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
