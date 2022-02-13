<template>
	<v-container>
		<v-data-table
			:headers="headers"
			:items="listQnA"
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
				<div v-if="props.item.qna_answer_regdate == props.item.qna_question_regdate">
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
import { mapState, mapActions } from "vuex";
	import AnswerModal from "./AnswerModal.vue";
	const userStore = "userStore";
	//import {deleteUser, userList} from "@/api/member";
	export default {
		components: { AnswerModal },
		name: "UserList",
		computed: {
			...mapState(userStore, ["listQnA"]),
		},
		mounted() {
			this.getQnA(localStorage.getItem("Authorization"));
		},
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
			};
		},

		methods: {
			...mapActions(userStore, ["getQnA"]),
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
