<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="qnaList"
			item-key="qna_no"
			class="elevation-1"
			:header-props="{ sortIcon: null }"
			:hide-default-footer="true"
		>
			<template #item.qna_no="{ value }">
				<div class="mr-8">{{ value }}</div>
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
						outlined
						@click.stop="$set(dialogNote, props.item.qna_no, true)"
						>답변 대기</v-btn
					>
					<answer-modal :item="props.item" :dialogNote="dialogNote" />
				</div>
				<div v-else>
					<v-btn
						dense
						elevation="0"
						color="primary"
						outlined
						@click.stop="$set(dialogNote, props.item.qna_no, true)"
						>답변 확인</v-btn
					>
					<answer-modal :item="props.item" :dialogNote="dialogNote" />
				</div>
			</template>
		</v-data-table>
	</v-container>
</template>

<script>
	import AnswerModal from "./AnswerModal.vue";
	//import {deleteUser, userList} from "@/api/member";
	export default {
		components: { AnswerModal },

		name: "UserList",
		data() {
			return {
				expanded: [],
				dialogNote: [],
				isExpanded: false,
				page: "",
				show: false,

				//api 통신전 dummy data
				headers: [
					{
						text: "제목",
						sortable: false,
						width: "40%",
						value: "qna_title",
					},
					{
						text: "답변여부",
						value: "actions",
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
						qna_question:
							"Lorem Ipsum is simply dummy text of the printing<br/> and typesetting industry. Lorem Ipsum <br/>has been the industry's standard dummy text ever since<br/> the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.<br/> It has survived not only five centuries, but also <br/>the leap into electronic typesetting, remaining <br/>essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets <br/>containing Lorem Ipsum passages, and more recently with desktop publishing software<br/> like Aldus PageMaker including versions of Lorem Ipsum.",
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
				//	alert(item.qna_title + "에 대한 답변 등록하기");
				console.log(item);
				this.show = !this.show;
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
