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
import { mapActions } from "vuex";
import QnATable from "../util/QnATable.vue";
const userStore = "userStore";
export default {
	components: { QnATable },
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
		};
	},
};
</script>

<style>
	.v-data-table__expanded.v-data-table__expanded__content {
		box-shadow: none !important;
	}
</style>
