<template>
	<v-card height="85vh" elevation="0">
		<v-card-title
			>문의하기
			<!-- <v-spacer />-->
			<v-dialog v-model="dialog" width="500">
				<template v-slot:activator="{ on, attrs }">
					<v-btn icon v-bind="attrs" v-on="on"
						><v-icon>mdi-comment-question</v-icon></v-btn
					>
				</template>
				<v-card>
					<v-card-title class="text-h5 lighten-2"> 문의하기 </v-card-title>
					<v-divider />

					<v-card-text>
						<v-list-item-subtitle>제목</v-list-item-subtitle>
						<v-text-field
							v-model="questionTitle"
							dense
							outlined
							clearable
						></v-text-field>

						<v-list-item-subtitle>내용</v-list-item-subtitle>
						<v-textarea value="" solo dense outlined v-model="questionContent">
							<v-html></v-html>
						</v-textarea>
					</v-card-text>

					<v-divider></v-divider>

					<v-card-actions>
						<v-spacer></v-spacer>
						<v-btn color="primary" text @click="onAsk"> 문의하기 </v-btn>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</v-card-title>
		<v-divider />
		<v-row>
			<v-col md="1" />
			<!-- properties : 
				:single-expand="singleExpand"
                -->
			<v-col md="11">
				<qn-a-table />
			</v-col>
		</v-row>
	</v-card>
</template>

<script>
	import QnATable from "../util/QnATable.vue";
	export default {
		components: { QnATable },
		methods: {
			onAsk() {
				alert(this.questionContent);
				this.questionContent.replace(/(?:\r\n|\r|\n)/g, "<br />");
				console.log(this.questionContent);
				this.dialog = false;
				//alert(this.questionContent);
				//문의하기 api 연결
			},
		},
		data() {
			return {
				expanded: [],
				dialog: false,
				questionContent: ``,
				questionTitle: "",
				singleExpand: true,
				pagination: {
					rowsPerPage: 5,
				},
				dessertHeaders: [
					{
						text: "질문",
						align: "start",
						sortable: false,
						value: "name",
					},
					{ text: "답변 여부", value: "answerStatus" },

					{ text: "", value: "data-table-expand" },
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
	};
</script>

<style>
	.v-data-table__expanded.v-data-table__expanded__content {
		box-shadow: none !important;
	}
</style>
