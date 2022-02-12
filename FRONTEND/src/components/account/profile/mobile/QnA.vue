<template>
	<v-card
		height="100vh"
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
			<mobile-qn-a-table />
		</v-container>
	</v-card>
</template>

<script>
import { mapActions } from "vuex";
	import MobileQnATable from "../util/MobileQnATable.vue";
	const userStore = "userStore";
	export default {
		components: { MobileQnATable },
		methods: {
			...mapActions(userStore, ["registerQuestion", "getQnA"]),
			onAsk() {
				if (!this.questionTitle || !this.questionContent) {
					return;
				}
				this.questionContent.replace(/(?:\r\n|\r|\n)/g, "<br />");
				this.registerQuestion({ qna_title: this.questionTitle, qna_question: this.questionContent, token: sessionStorage.getItem('Authorization') })
				this.getQnA(sessionStorage.getItem("Authorization"));
				window.location.reload();
				this.dialog = false;
				this.questionTitle = '';
				this.questionContent = '';
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
			};
		},
	};
</script>

<style>
	.mobile-qna-dialog {
	}
</style>
