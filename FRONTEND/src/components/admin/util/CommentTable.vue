<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="commentList"
			item-key="comment_no"
			class="elevation-1"
			:search="search"
			:sort-by.sync="sortBy"
			:sort-desc.sync="sortDesc"
			:expanded.sync="expanded"
			@click:row="clicked"
			show-expand
		>
			<template v-slot:top>
				<v-text-field
					v-model="search"
					label="댓글 번호, 댓글 내용, 작성자로 검색 "
					class="mx-8"
					append-icon="mdi-clipboard-search"
				></v-text-field>
			</template>
			<template v-slot:expanded-item="{ item }">
				<td :colspan="headers.length + 1">
					<div>
						<v-list-item-subtitle class="ma-2 ml-2 grey--text">
							<v-icon>mdi-calendar-month</v-icon>
							{{ item.comment_regdate }}
							<v-icon>mdi-clipboard-text-outline</v-icon>
							{{ item.user_nickname }}
						</v-list-item-subtitle>
						<div class="admin-comment-expansion">
							{{ item.comment_content }}
						</div>
					</div>
				</td>
			</template>

			<!--search ends here-->
			<template #item.comment_no="{ value }">
				<div class="mr-8">{{ value }}</div>
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
				<nick-name-module :item="item.user_nickname" :itemno="item.user_no" />
			</template>

			<template #item.comment_content="{ item }">
				<div
					style="
						text-overflow: ellipsis;
						overflow: hidden;
						width: 160px;
						height: 1.2em;
						white-space: nowrap;
					"
				>
					{{ item.comment_content }}
				</div>
			</template>

			<template v-slot:item.delete="props">
				<v-icon small @click="goodbye(props.item)"> mdi-trash-can</v-icon>
			</template>
		</v-data-table>
	</v-container>
</template>

<script>
	import NickNameModule from "./NickNameModule.vue";
	//import {deleteUser, userList} from "@/api/member";
	export default {
		name: "UserList",
		components: {
			NickNameModule,
		},
		data() {
			return {
				search: "",
				expanded: [],
				isExpanded: false,
				page: "",
				sortBy: "status",
				sortDesc: false,
				show: false,
				value: "comment_content",

				//api 통신전 dummy data
				headers: [
					{
						text: "번호",
						align: "center",
						value: "comment_no",
					},
					{
						text: "작성자",
						align: "center",
						value: "user_nickname",
					},
					{
						text: "내용",
						sortable: false,
						width: "25%",
						value: "comment_content",
					},
					{
						text: "좋아요",
						sortable: true /*디비 이름 확인하기 */,
						value: "comment_like",
					},
					{
						text: "신고수" /*db 이름 확인하기 */,
						value: "comment_report",
					},

					{
						text: "등록일",
						value: "comment_regdate",
						align: "center",
					},
					{
						text: "삭제",
						value: "delete",

						sortable: false,
					},
				],
				commentList: [
					{
						comment_no: 1,
						user_nickname: "김싸피",
						comment_content: "🥕🥕🥕🥕🥕🥕🥕🥕",
						comment_like: 154,
						comment_report: 1234,
						comment_regdate: "2022-01-28",
					},
					{
						comment_no: 136,
						user_nickname: "이싸피",
						comment_content:
							"🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕",
						comment_like: 3,
						comment_report: 1,
						comment_regdate: "2022-01-28",
					},
					{
						comment_no: 145,
						user_nickname: "청싸피",
						comment_content: "🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕🥕",
						comment_like: 14,
						comment_report: 12,
						comment_regdate: "2022-01-28",
					},
				],
			};
		},

		methods: {
			goodbye(item) {
				var message =
					"정말 " + item.user_nickname + " 님의 댓글을 삭제하시겠습니까?";
				if (confirm(message)) {
					alert("해당 댓글을 삭제하였습니다.");
					//item.user_no로 회원 정지 api통신
					//deleteUser(this.item);
				} else {
					alert("취소하였습니다.");
				}
				//locaation.reload();
			},
			/*	clicked(value) {
				this.expanded.push(value);
			},*/
			clicked(item, event) {
				if (event.isExpanded) {
					const index = this.expanded.findIndex((i) => i === item);
					this.expanded.splice(index, 1);
				} else {
					this.expanded = []; //single 직접 구현..
					this.expanded.push(item);
				}
			},
		},
		/*	computed: {
			show: {
				get() {
					return this.value !== null;
				},
			},
		},*/
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

<style scopoed>
	.admin-comment-expansion {
		margin: 1%;
		padding: 1%;
	}
</style>
