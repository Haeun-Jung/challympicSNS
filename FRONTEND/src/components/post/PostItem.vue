<template>
	<v-card class="my-5">
		<a :href="`#${post.post_no}`" :id="`${post.post_no}`"></a>
		<v-card-title class="d-flex justify-space-between">
			<div class="d-flex align-center">
				<img
					v-if="post.user_profile"
					class="profile mr-3"
					:src="`https://d3iu4sf4n4i2qf.cloudfront.net/${post.user_profile}`"
					alt="profile img"
				/>
				<img
					v-else
					class="profile mr-3"
					src="../../assets/profile.png"
					alt="profile img"
				/>
				<nick-name-module :itemno="post.user_no" :item="post.user_nickname" />
			</div>
			<!-- 'nickname'을 현재 로그인한 유저의 닉네임으로 수정 -->
			<div v-if="this.user">
				<div v-if="post.user_no == this.user.user_no">
					<v-btn @click="editPost" icon>
						<v-icon>mdi-pencil-outline</v-icon>
					</v-btn>
					<v-btn @click="doDeletePost" icon>
						<v-icon>mdi-close</v-icon>
					</v-btn>
				</div>
			</div>
		</v-card-title>
		<!-- 이미지/동영상 props 넘겨주기 -->

		<v-card-text class="px-0">
			<span v-if="post.challenge_type == 'video'">
				<player :fileName="post.file_savedname" :filePath="post.file_path" />
			</span>
			<span v-else>
				<post-image
					:fileName="post.file_savedname"
					:filePath="post.file_path"
				/>
			</span>
		</v-card-text>
		<v-card-text class="d-flex justify-space-between pt-0 pl-0 pb-0">
			<span>
				<v-btn @click="like(post)" class="icon-margin" icon>
					<v-icon :class="{ 'active-like-btn': post.isLike }" large>
						mdi-heart-outline
					</v-icon>
				</v-btn>
				<v-btn @click="toggleCommentShow" class="icon-margin" icon>
					<v-icon :class="{ 'active-comment-btn': showComment }" large>
						mdi-comment-outline
					</v-icon>
				</v-btn>
			</span>
			<span>
				<share-button :post-no="post.post_no" :challenge-no="post.challenge_no > 0 ? post.challenge_no : challengeNo" />
			</span>
		</v-card-text>
		<v-card-text class="py-2">
			<span
				:class="[
					'like-cnt',
					[$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
				]"
				@click="toggleLikeDialog"
			>
				좋아요 {{ post.likeCnt }}개
			</span>
		</v-card-text>
		<v-card-text class="py-1">
			<span class="mr-3">
				<nick-name-module
					class="text--bold"
					:itemno="post.user_no"
					:item="post.user_nickname"
				/>
			</span>
			<span
				:class="[$vuetify.theme.dark ? 'dark-mode-text' : 'black-text']"
				v-html="$options.filters.hashAnchor(this.post.post_content)"
			></span>
			<p>{{ post.post_regdate }}</p>
		</v-card-text>
		<v-divider />
		<v-expand-transition>
			<div v-show="showComment">
				<comment-list :comments="post.commentList" :post_no="this.post.post" />
				<v-divider />
			</div>
		</v-expand-transition>
		<v-card-text class="profile-img-and-comment-input">
			<img
				class="comment-profile mr-2"
				src="../../assets/profile.png"
				alt="profile img"
			/>
			<v-text-field
				v-model="commentInput"
				class="rounded-pill"
				placeholder="댓글을 달아보세요."
				append-outer-icon="mdi-arrow-up"
				outlined
				hide-details
				@click:append-outer="addComment"
			></v-text-field>
		</v-card-text>

		<post-upload
			v-if="postDialog"
			:propChallenge="challengePost"
			@close-modal="postDialog = false"
			type="modify"
		></post-upload>

		<follow-like-modal
			v-if="likeDialog"
			:users="likeList"
			:type="dialogType"
			@close-dialog="toggleLikeDialog"
			:propChallenge="challenge"
		/>
	</v-card>
</template>

<script>
	import PostImage from "./PostImage.vue";
	import Player from "./Player.vue";
	import FollowLikeModal from "../common/FollowLikeModal.vue";
	import CommentList from "./CommentList.vue";
	import ShareButton from "../button/ShareButton.vue";
	import { createComment } from "@/api/comment.js";
	import PostUpload from "@/components/upload/PostUpload.vue";
	import { deletePost, postLikeList } from "@/api/post.js";
	import NickNameModule from "@/components/admin/util/NickNameModule.vue";

	export default {
		name: "PostItem",
		components: {
			PostImage,
			Player,
			FollowLikeModal,
			CommentList,
			ShareButton,
			PostUpload,
			NickNameModule,
		},
		props: {
			type: String,
			post: Object,
      challengeNo: Number,
			user: Object,
		},
		data() {
			return {
				commentInput: "",
				showComment: false,
				likeDialog: false,
				dialogType: "like",
				postDialog: false,
				challengePost: {
					challengeName: "",
					fileType: "",
          post_content: "",
					challengeNo: "",
					fileNo: "",
				},
			};
		},
		created() {
			// console.log("debug-user");
			// console.log(this.user);
			console.log("debug-post");
			console.log(this.post);
			// console.log("debug-challengePost");
			// console.log(this.challengePost);
		},
		computed: {
			likeList() {
				return this.$store.state.postStore.likeList;
			},
		},
		methods: {
			like(post) {
				if (!this.user) {
					alert("로그인이 필요한 서비스입니다.");
					return;
				}

				// 좋아요 API 요청
				if (post.isLike && post.likeCnt > 0) {
					// 클릭된 상태
					post.likeCnt -= 1;
				} else {
					post.likeCnt += 1;
				}

				post.isLike = !post.isLike;

				postLikeList(
					this.post.post_no,
					this.user.user_no,
					(response) => {
						console.log(response);
					},
					(error) => {
						console.log(error);
					}
				);
			},
			toggleCommentShow() {
				this.showComment = !this.showComment;
				if (this.showComment) {
					// this.$store.dispatch('getCommentList', this.post.post_no);
				}
			},
			toggleLikeDialog() {
				this.likeDialog = !this.likeDialog;
				if (this.likeDialog) {
					this.$store.dispatch("postStore/getLikeList", {
						postNo: this.post.post_no,
						userNo: this.$store.state.userStore.userInfo.user_no,
					});
				}
			},
			editPost() {
				// 포스트 수정 API 요청
				console.log("edit");
				this.challengePost.challengeName = this.post.challenge_name;
				this.challengePost.fileType = this.post.challenge_type.toUpperCase();
				this.challengePost.post_content = this.post.post_content;
				this.challengePost.challengeNo = this.post.challenge_no;
				this.challengePost.fileNo = this.post.file_no;
				this.challengePost.postNo = this.post.post_no;

				this.postDialog = true;
				// type modify로 전송
			},
			doDeletePost() {
				// 포스트 삭제 API 요청
				if (!confirm("정말 삭제하시겠습니까?")) {
					return;
				}

				deletePost(
					this.post.post_no,
					(response) => {
						console.log(response);
						window.location.href = "/recent";
					},
					(error) => {
						console.log(error);
					}
				);
			},
			exportPost() {
				// 공유하기 dialog 열기
				return;
			},
			addComment() {
				console.log("댓글 등록 호출");

				if (this.commentInput.length == 0) {
					return;
				}

				let post_no = this.post.post_no;
				let user_no = this.user.user_no;
				let comment_content = this.commentInput;

				// 댓글 작성 API 호출
				createComment(
					post_no,
					user_no,
					comment_content,
					(response) => {
						const { data } = response;
						// TODO: 리턴받은 댓글 정보에 + user_no, user_nickname, user_profile 추가해서 this.post.commentList에 append
						this.post.commentList.push(data.data);
						console.log(data.data);
					},
					() => {}
				);
				this.commentInput = "";
			},
		},
		filters: {
			hashAnchor(str) {
				// TODO: anchor 태그에서 href base url 주석 처리된 url로 변경!!!!!
				if (!str.includes("#") && !str.includes("@")) {
					return str;
				}
				str = str.replace(
					/#[^\s]+/g,
					'<a class="text-decoration-none" href="http://192.168.0.8:8080/search/$&">$&</a>'
				);
				// str = str.replace(
				//   /#[^\s]+/g,
				//   '<a class="text-decoration-none" href="http://i6b101.p.ssafy.io/search/$&">$&</a>'
				// );
				return str.replace(/\/#/g, "/");
			},
		},
	};
</script>

<style scoped>
	.dark-mode-text {
		color: rgb(255, 255, 255, 0.6);
	}
	.black-text {
		color: black;
	}
	.profile {
		width: 40px;
		height: 40px;
		border-radius: 50%;
	}
	.icon-margin {
		margin: 0 0.5rem;
	}
	.comment-profile {
		width: 30px;
		height: 30px;
		border-radius: 50%;
	}
	.active-like-btn {
		color: red !important;
	}
	.active-comment-btn {
		color: #3396f4 !important;
	}
	.like-cnt {
		cursor: pointer;
	}
	.profile-img-and-comment-input {
		display: flex;
		align-items: center;
	}
</style>
