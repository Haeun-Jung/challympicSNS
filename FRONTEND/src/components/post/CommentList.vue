<template>
	<div>
		<v-alert v-model="alert" :value="alert" type="info">
			신고가 접수되었습니다.
		</v-alert>
		<!-- <v-card-text>
      <span>{{ comments.length }}개의 댓글이 있습니다.</span>
    </v-card-text> -->
		<!-- <v-divider></v-divider> -->
		<v-card-text
			v-for="comment in comments"
			class="content-and-btns py-2"
			:key="comment.comment_no"
		>
			<div class="profile-img-and-comment">
				<img
					class="profile mr-2"
					src="../../assets/profile.png"
					alt="profile img"
				/>
				<span>
					<div>
						<span class="font-weight-bold">
							<router-link
								:to="{ path: `/feed/${comment.user_no}` }"
								style="text-decoration: none; color: inherit; mr-2"
							>
								{{ comment.user_nickname }}
							</router-link>
						</span>
						<span>{{ comment.comment_content }}</span>
					</div>
					<div class="comment-info">
						<span class="mr-2">{{ comment.regdate }}</span>
						<span class="mr-2">좋아요 {{ comment.like_cnt }}</span>
						<span @click="confirmReportDialog = true" class="report-btn"
							>신고하기</span
						>
					</div>
				</span>
			</div>
			<div>
				<span v-if="comment.user_nickname === nickname">
					<v-btn @click="editComment(comment.comment_no, comment.comment_content)" icon>
						<v-icon small>mdi-pencil-outline</v-icon>
					</v-btn>
					<v-btn @click="deleteComment(comment.comment_no)" icon>
						<v-icon small>mdi-close</v-icon>
					</v-btn>
				</span>
				<span v-else>
					<v-btn @click="likeComment(comment.comment_no)" icon>
						<v-icon :class="{ 'active-like-btn': comment.isLiked }" small>
							mdi-heart-outline
						</v-icon>
					</v-btn>
				</span>
			</div>
		</v-card-text>
		<confirm-report
			:confirm-report-dialog="confirmReportDialog"
			@close-dialog="confirmReportDialog = false"
			@reported="showReportedAlert"
		/>
	</div>
</template>

<script>
	import ConfirmReport from "../report/ConfirmReport.vue";
	import { updateComment, deleteComment, commentLike, commentReport } from '@/api/comment.js';
	export default {
		components: { ConfirmReport },
		name: "CommentList",
		props: {
			comments: Array,
			post_no: Number,
		},
		data() {
			return {
				nickname: "박싸피",
				confirmReportDialog: false,
				alert: false,
			};
		},
		watch: {},
		methods: {
			editComment(comment_no, comment_content) {
				// 댓글 수정 API 호출
				// emit event => 현재 comment에 대한 content 값 수정
				updateComment(
					comment_no,
					comment_content,
					this.post_no,
					(response) => {
						this.comments = response.data
					}
				)
			},
			deleteComment(comment_no) {
				// 댓글 삭제 API 호출
				// emit event => 현재 comment 삭제?
				deleteComment(
					comment_no,
					this.post_no,
					(response) => {
						this.comments = response.data
					}
				)
			},
			likeComment(comment_no) {
				// 댓글 좋아요 API 호출
				// emit event => 현재 comment에 대한 isLiked 값 수정
				commentLike(
					this.$store.state.userStore.userInfo.user_no,
					comment_no,
					(response) => {
						console.log(response)
					}
				)
			},
			showReportedAlert(comment_no) {
				this.alert = true;
				setTimeout(() => {
					this.alert = false;
				}, 3000);
				// 댓글 신고 API 호출
				commentReport(
					comment_no,
					(response) => {
						console.log(response)
					}
				)
			},
		},
	};
</script>

<style scoped>
	.profile {
		width: 30px;
		height: 30px;
		border-radius: 50%;
	}
	.comment-info {
		font-size: 11px;
	}
	.content-and-btns {
		display: flex;
		justify-content: space-between;
	}
	.profile-img-and-comment {
		display: flex;
		align-items: center;
	}
	.active-like-btn {
		color: red !important;
	}
	.report-btn {
		cursor: pointer;
	}
</style>
