<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="qna"
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
				<td :colspan="headers.length + 1">
					<qn-a-expansion :item="item" />
				</td>
			</template>

			<!--search ends here-->
			<template #item.qna_no="{ value }">
				<div class="mr-8">{{ value }}</div>
			</template>
			<template #item.qna_question_regdate="{ value }">
				{{ value.substring(0, 10) }}
			</template>
			<!--
			<template #item.user_nickname="{ item }">
				<nick-name-module :item="item.user_nickname" :itemno="item.user_no" />
			</template>
-->

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
				<div
					v-if="
						props.item.qna_answer_regdate == props.item.qna_question_regdate
					"
				>
					<v-btn
						dense
						elevation="0"
						color="primary"
						ripple="false"
						outlined
						@click.stop="$set(dialogNote, props.item.qna_no, true)"
						>답변하기</v-btn
					>
					<answer-modal :item="props.item" :dialogNote="dialogNote" />
				</div>
				<div v-else>
					{{ props.item.qna_answer_regdate.substring(0, 10) }}
				</div>
			</template>
		</v-data-table>
	</v-container>
</template>

<script>
	import QnAExpansion from "./QnAExpansion.vue";
	//	import NickNameModule from "./NickNameModule.vue";
	import AnswerModal from "./AnswerModal.vue";
	import { qnaList } from "@/api/admin";

	export default {
		components: { QnAExpansion, AnswerModal },
		name: "UserList",
		data() {
			return {
				search: "",
				expanded: [],
				dialogNote: [],
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
						value: "user_email",
					},
					{
						text: "제목",
						sortable: false,
						width: "25%",
						value: "qna_title",
					},
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
				qna: [],
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
				console.log(item);
				this.show = !this.show;
			},
		},
		created() {
			qnaList(
				(response) => {
					this.qna = response.data.data;
					console.log(this.qna);
				},
				(error) => {
					if (error) console.log("er");
				}
			);
		},
	};
</script>

<style scopoed>
	.admin-comment-expansion {
		margin: 1%;
		padding: 1%;
	}
</style>
