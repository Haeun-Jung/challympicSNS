<template>
	<div class="detail-page">
		???s
		<v-alert v-model="alert" class="alert-position" :value="alert" type="info">
			신고가 접수되었습니다.
		</v-alert>
		<v-row class="side-and-main">
			<side />
			<v-col class="col-10 col-md-6">
				<div class="d-flex justify-space-between">
					<div class="d-flex align-center">
						<h1>#{{ challenge.name }}</h1>
						<span v-if="challenge.official">✅</span>
						<share-button />
						<v-btn
							v-if="isSubscribed"
							@click="subscribe"
							class="icon-margin"
							icon
						>
							<v-icon large>mdi-bookmark</v-icon>
						</v-btn>
						<v-btn v-else @click="subscribe" class="icon-margin" icon>
							<v-icon large>mdi-bookmark-outline</v-icon>
						</v-btn>
					</div>
					<div>
						<v-btn @click="confirmReportDialog = true" class="icon-margin" icon>
							<v-icon large>mdi-alarm-light-outline</v-icon>
						</v-btn>
						<v-btn @click="postDialog = true" outlined color="#3396F4">
							참여하기
						</v-btn>
					</div>
				</div>
				<p>기간: 00.00.00 ~ 00.00.00</p>
				<v-chip class="mb-2 challenge-chip" color="#3396F4"> 요리 </v-chip>
				<p>{{ challenge.content }}</p>
				<div>
					<v-data-iterator
						:items="postList"
						:items-per-page.sync="itemsPerPage"
						:sort-by="sortBy"
						:sort-desc="sortDesc"
						hide-default-footer
					>
						<template v-slot:header>
							<v-row class="justify-end">
								<v-col class="col-5 col-sm-3 col-lg-2">
									<v-select
										v-model="sortBy"
										:items="sortKeys"
										:sort-desc="sortDesc"
										class="align-end"
										flat
										solo-inverted
										hide-details
										label="정렬"
									></v-select>
								</v-col>
							</v-row>
						</template>
						<!-- props로 포스트 목록 내려주기 -->
						<template v-slot:default="props">
							<post-item
								v-for="post in props.items"
								:post="post"
								:type="challenge.type"
								:key="post.postInfo.postNo"
							></post-item>
						</template>
					</v-data-iterator>
				</div>
			</v-col>
		</v-row>
		<confirm-report
			:confirm-report-dialog="confirmReportDialog"
			@close-dialog="confirmReportDialog = false"
			@reported="showReportedAlert"
		/>
		<post-upload
			v-if="postDialog"
			:challengeName="challenge.name"
			@close-modal="postDialog = false"
		/>
	</div>
</template>

<script>
	import Side from "@/components/layout/Side.vue";
	import PostItem from "@/components/post/PostItem.vue";
	import ShareButton from "@/components/button/ShareButton.vue";
	import ConfirmReport from "@/components/report/ConfirmReport.vue";
	import PostUpload from "@/components/upload/PostUpload.vue";

	export default {
		name: "ChallengeDetail",
		components: { Side, PostItem, ShareButton, ConfirmReport, PostUpload },
		data() {
			return {
				confirmReportDialog: false,
				alert: false,
				isSubscribed: false,
				postDialog: false,
				linkInput: false,
				challenge: {
					name: "아이스버킷챌린지",
					content:
						"풍부하게 있는 그들은 밝은 않는 끓는 철환하였는가? 타오르고 날카로우나 뜨고, 구할 봄바람이다. 열매를 살 영원히 우는 힘차게 듣는다. 보내는 품고 쓸쓸한 굳세게 위하여 귀는 능히 사막이다. 그림자는 우리의 대고, 보이는 이상이 인생에 말이다. 인간은 지혜는 피고, 날카로우나 같이, 그들에게 것은 물방아 피다.",
					type: "image",
					official: true,
				},
				itemsPerPage: -1,
				// TODO: 기본 정렬 키 어떤 걸로 할지 결정
				sortBy: "postInfo.postNo",
				sortKeys: [
					{ text: "등록 순", value: "postInfo.postNo" },
					{ text: "최신 순", value: "postInfo.regDate" },
					{ text: "인기 순", value: "postInfo.likeCnt" },
					{ text: "댓글 순", value: "postInfo.comments.length" },
				],
				postList: [
					// 테스트용 데이터
					{
						postInfo: {
							postNo: 1,
							nickname: "nickname",
							content: "다음 타깃 @nickname1",
							regDate: "2022.01.02",
							likeCnt: 2,
							isLiked: false,
							fileName: "img2.png",
						},
						likedUsers: [
							{
								likeNo: 1,
								userNo: 1,
								avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
								name: "Jason Oner",
								title: "밥 잘먹는",
								isFollowing: true,
							},
							{
								likeNo: 2,
								userNo: 2,
								avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
								name: "Mike Carlson",
								title: "스쿼트 장인",
								isFollowing: false,
							},
						],
						comments: [
							{
								commentNo: 1,
								nickname: "박싸피",
								content: "댓글입니다.",
								regDate: "2021.11.11",
								likeCnt: 0,
								isLiked: false,
							},
							{
								commentNo: 2,
								nickname: "김싸피",
								content: "댓글입니다!",
								regDate: "2021.11.11",
								likeCnt: 1,
								isLiked: true,
							},
							{
								commentNo: 3,
								nickname: "최싸피",
								content: "댓글입니다!",
								regDate: "2021.11.12",
								likeCnt: 1,
								isLiked: true,
							},
						],
					},
					{
						postInfo: {
							postNo: 2,
							nickname: "nickname2",
							content:
								"그러나, 이에 #의하여 #민사상이나 #형사상의 책임이 면제되지는 아니한다. 모든 국민은 신체의 자유를 가진다. 누구든지 법률에 의하지 아니하고는 체포·구속·압수·수색 또는 심문을 받지 아니하며, 법률과 적법한 절차에 의하지 아니하고는 처벌·보안처분 또는 강제노역을 받지 아니한다.",
							regDate: "2022.01.20",
							likeCnt: 1,
							isLiked: false,
							fileName: "img1.jpg",
						},
						likedUsers: [
							{
								likeNo: 1,
								userNo: 3,
								avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
								name: "Jason Oner",
								title: "밥 잘먹는",
								isFollowing: true,
							},
						],
						comments: [
							{
								commentNo: 1,
								nickname: "박싸피",
								content: "댓글입니다.",
								regDate: "2021.11.11",
								likeCnt: 0,
								isLiked: false,
							},
							{
								commentNo: 2,
								nickname: "김싸피",
								content: "댓글입니다!",
								regDate: "2021.11.11",
								likeCnt: 0,
								isLiked: false,
							},
						],
					},
					{
						postInfo: {
							postNo: 3,
							nickname: "nickname3",
							content:
								"그러나, 이에 의하여 민사상이나 형사상의 책임이 면제되지는 아니한다. 모든 국민은 신체의 자유를 가진다. 누구든지 법률에 의하지 아니하고는 체포·구속·압수·수색 또는 심문을 받지 아니하며, 법률과 적법한 절차에 의하지 아니하고는 처벌·보안처분 또는 강제노역을 받지 아니한다.",
							regDate: "2022.01.30",
							likeCnt: 1,
							isLiked: false,
							fileName: "img2.png",
						},
						likedUsers: [
							{
								likeNo: 1,
								userNo: 3,
								avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
								name: "Jason Oner",
								title: "밥 잘먹는",
								isFollowing: true,
							},
						],
						comments: [
							{
								commentNo: 1,
								nickname: "박싸피",
								content: "댓글입니다.",
								regDate: "2021.11.11",
								likeCnt: 0,
								isLiked: false,
							},
						],
					},
				],
			};
		},
		methods: {
			showReportedAlert() {
				// 챌린지 신고 API 호출
				this.alert = true;
				this.report = true;
				setTimeout(() => {
					this.alert = false;
				}, 3000);
			},
			subscribe() {
				if (this.isSubscribed) {
					// 챌린지 구독 delete 요청
				} else {
					// 챌린지 구독 post 요청
				}
				this.isSubscribed = !this.isSubscribed;
			},
		},
		computed: {
			sortDesc() {
				if (
					this.sortBy === "postInfo.likeCnt" ||
					this.sortBy === "postInfo.regDate"
				) {
					return true;
				}
				return false;
			},
		},
		created() {
			// 포스트 목록 가져온 뒤 this.postList = 포스트 목록
		},
	};
</script>

<style scoped>
	.alert-position {
		position: absolute;
		top: 3.3rem;
		width: 100%;
		z-index: 1;
	}
	.detail-page {
		margin-top: 5rem;
	}
	.icon-margin {
		margin: 0 0.5rem;
	}
	.side-and-main {
		justify-content: space-evenly;
	}
	.challenge-chip {
		color: white;
	}
	.select {
		width: 120px;
	}
</style>
