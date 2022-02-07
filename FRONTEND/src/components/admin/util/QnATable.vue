<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="qnaList"
			item-key="qna_no"
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
					label="문의 번호, 작성자, 내용으로 검색 "
					class="mx-8"
					append-icon="mdi-clipboard-search"
				></v-text-field>
			</template>
			<template v-slot:expanded-item="{ item }">
				<td :colspan="headers.length">
					<div>
						<v-list-item-title class="ma-2 ml-2 pr-2">
							[제목] {{ item.qna_title }}
						</v-list-item-title>
						<v-list-item-subtitle class="ma-2 ml-2">
							날짜 : {{ item.qna_question_regdate }}
						</v-list-item-subtitle>
						<v-list-item-subtitle class="ma-2 ml-2">
							작성자 :
							{{ item.user_nickname }}
						</v-list-item-subtitle>
						<v-list-item-subtitle class="ma-2 ml-2">
							[내용] {{ item.qna_question }}
						</v-list-item-subtitle>
					</div>
					<div v-if="item.qna_answer_regdate != ``">
						<v-divider />
						<div>
							<v-list-item-subtitle class="ma-2 ml-2">
								[답변] {{ item.qna_answer }}
							</v-list-item-subtitle>
						</div>
						<v-list-item-subtitle class="ma-2 ml-2">
							날짜 : {{ item.qna_answer_regdate }}
						</v-list-item-subtitle>
					</div>
					<div v-else>
						<v-divider />
						<div>
							<v-list-item-subtitle class="ma-2 ml-2">
								아직 등록된 답변이 없습니다.
							</v-list-item-subtitle>
						</div>
					</div>
				</td>
			</template>

			<!--search ends here-->
			<template #item.qna_no="{ value }">
				<div class="mr-8">{{ value }}</div>
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

			<template #item.qna_title="{ item }">
				<div
					style="
						text-overflow: ellipsis;
						overflow: hidden;
						width: 160px;
						height: 1.2em;
						white-space: nowrap;
					"
				>
					{{ item.qna_title }}
				</div>
			</template>

			<template v-slot:item.actions="props">
				<div v-if="props.item.qna_answer_regdate == ``">
					<v-btn
						dense
						elevation="0"
						color="primary"
						ripple="false"
						outlined
						@click="answer(props.item)"
						>답변하기</v-btn
					>
				</div>
				<div v-else>
					{{ props.item.qna_answer_regdate }}
				</div>
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
						value: "qna_no",
					},
					{
						text: "작성자",
						value: "user_nickname",
					},
					{
						text: "제목",
						sortable: false,
						width: "25%",
						value: "qna_title",
					},
					/*	{
						text: "내용",
						sortable: true ,
						value: "qna_question",
					},
					{
						text: "답변" ,
						value: "qna_answer",
					},*/
					{
						text: "등록일",
						value: "qna_question_regdate",
						align: "center",
					},
					{
						text: "답변일",
						//	value: "qna_answer_regdate",
						value: "actions",
						align: "center",
					},
				],
				qnaList: [
					{
						qna_no: 1,
						user_nickname: "김싸피",
						qna_title: "🥕🥕🥕🥕🥕🥕🥕🥕",
						qna_question: "도와주세여...",
						qna_answer: "네...",
						qna_question_regdate: "2022-01-28",
						qna_answer_regdate: "2022-02-29",
					},
					{
						qna_no: 2,
						user_nickname: "김싸피",
						qna_title: "문의사항 있습니다",
						qna_question: "문의내용ㅇ",
						qna_answer: "",
						qna_question_regdate: "2022-02-06",
						qna_answer_regdate: "",
					},
					{
						qna_no: 24,
						user_nickname: "청싸피",
						qna_title: "질문!",
						qna_question: "^^",
						qna_answer: "",
						qna_question_regdate: "2022-01-31",
						qna_answer_regdate: "",
					},
				],
			};
		},

		methods: {
			clicked(item, event) {
				if (event.isExpanded) {
					const index = this.expanded.findIndex((i) => i === item);
					this.expanded.splice(index, 1);
				} else {
					this.expanded = []; //single 직접 구현..
					this.expanded.push(item);
				}
			},
			answer(item) {
				alert(item.qna_title + "에 대한 답변 등록하기");
				console.log(item);
				//alert(item.qna_title + "애 대한 답변 하기 - 모달창으로 답변 등록 후 ");
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
