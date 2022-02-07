<template>
	<v-card
		height="90vh"
		width="100%"
		class="justify-center mobile-profile-delete-user-container"
	>
		<v-card-title class="justify-center"
			>문의 하기
			<v-dialog
				v-model="dialog"
				width="500"
				justify-center
				class="mobile-qna-dialog"
			>
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
						<v-textarea
							value=""
							solo
							dense
							outlined
							v-model="questionContent"
						></v-textarea>
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
		<!-- 문의 내용 -->
		<v-container class="mobile-profile-change-user-password-container">
			<v-data-table
				:headers="dessertHeaders"
				:expanded.sync="expanded"
				:items="desserts"
				item-key="name"
				show-expand
				single-expand="true"
				:items-per-page="5"
				class="elevation-1"
				mobile-breakpoint="0"
			>
				<template v-slot:top> </template>
				<template v-slot:expanded-item="{ headers }">
					<!-- 
                            
					<template v-slot:expanded-item="{ headers, item }">
                            여기에 item.answer로 갈아끼기 : A. {{item.answer}}-->
					<td :colspan="headers.length">
						여기에.. item.question도 마저 넣고.. A. challympic@ssafy.com으로
						연락주세요
					</td>
				</template>
			</v-data-table>
		</v-container>
	</v-card>
</template>

<script>
	export default {
		methods: {
			onAsk() {
				this.dialog = false;
				//alert(this.questionContent);
				//문의하기 api 연결
			},
		},
		data() {
			return {
				expanded: [],
				dialog: false,
				questionContent: "",
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
				desserts: [
					{
						name: "Q. 챌린지가 등록이 안되요1",
						answerStatus: 159,
					},
					{
						name: "Q. 챌린지가 등록이 안되요2",
						answerStatus: 237,
					},
					{
						name: "Q. 챌린지가 등록이 안되요3",
						answerStatus: 262,
					},
					{
						name: "Q. 챌린지가 등록이 안되요4",
						answerStatus: 305,
					},
					{
						name: "Q. 챌린지가 등록이 안되요5",
						answerStatus: 356,
					},
					{
						name: "Q. 챌린지가 등록이 안되요6",
						answerStatus: 375,
					},
				],
			};
		},
	};
</script>

<style>
	.mobile-qna-dialog {
	}
</style>
