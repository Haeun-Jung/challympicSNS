<template>
	<v-container>
		<v-data-iterator
			:items="challenges"
			:footer-props="{
				'items-per-page-options': [5, 10, 15, 20],
			}"
			:items-per-page="6"
		>
			<template v-slot:default="props">
				<v-row>
					<v-col
						v-for="item in props.items"
						:key="item.name"
						cols="12"
						sm="6"
						md="6"
						lg="4"
					>
						<!-- carousel house -->
						<v-card
							elevation="2"
							max-width="444px"
							class="mx-auto"
							ref="videoContainer"
						>
							<v-carousel
								hide-delimiter-background
								delimiter-icon="mdi-minus"
								width="444px"
								height="auto"
							>
								<!--리스트 들어가심-->
								<div class="holder">
									<v-carousel-item
										v-for="(post, idx) in item.postList"
										:key="post.post_no"
									>
										<video-component
											v-if="item.challenge_type === 'VIDEO'"
											:video="
												'http://d384sk7z91xokb.cloudfront.net/' +
												post.file_path +
												'/video/' +
												post.file_savedname +
												'.m3u8'
											"
										/>
										<!--포스트 정보 -->
										<div class="bar">
											<v-card-title>
												<router-link
													:to="{ path: `/feed/${post.user_no}` }"
													style="text-decoration: none; color: inherit"
												>
													<h3>
														{{ post.user_nickname }}
													</h3>
												</router-link>
												<v-spacer />
											</v-card-title>

											<v-card-subtitle>
												<!--좋아요 {{ item.post_likes }} 개 댓글 {{ item.post_comments }}개
					-->
											</v-card-subtitle>
										</div>
										<!-- 좋아요-->
										<div class="bar-heart">
											<v-btn @click="pushLike(post.post_no, idx)" icon>
												<!-- <v-icon :class="{ 'show-btns': hover }" :color="transparent">
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시
								:class="{ 'show-btns': hover }"
								-->
												<v-icon
													:color="post.post_like ? 'red' : 'grey lighten-3'"
													size="32"
												>
													mdi-heart-outline
												</v-icon>
											</v-btn>
										</div>
									</v-carousel-item>
								</div>
							</v-carousel>
						</v-card>

						<v-list two-line>
							<v-list-item>
								<v-list-item-content>
									<v-list-item-title>
										<router-link
											:to="{ path: `/challenge/${item.challenge_no}` }"
											style="text-decoration: none; color: inherit; mr-2"
											class="title-block"
										>
											{{ item.challenge_title }}
										</router-link>
									</v-list-item-title>
									<v-list-item-subtitle>
										{{ item.challenge_content }}
									</v-list-item-subtitle>
									<v-list-item-subtitle>
										{{ item.challenge_tag }}
									</v-list-item-subtitle>
								</v-list-item-content>
								<v-list-item-action>
									<span>
										<!-- v-if user_subscribe == true -> filled 된 애로 보여주기
											지금 이게 없어서 작동 못함...-->
										<v-btn
											icon
											@click="
												item.challenge_subscribe = !item.challenge_subscribe
											"
										>
											<v-icon
												:color="
													item.challenge_subscribe ? 'blue' : 'grey lighten-3'
												"
												size="32"
												>mdi-bookmark-outline</v-icon
											>
										</v-btn>
									</span>
								</v-list-item-action>
							</v-list-item>
						</v-list>
					</v-col>
				</v-row>
			</template>
		</v-data-iterator>
	</v-container>
</template>

<script>
	//	import ChallengeList from "../util/ChallengeList.vue";
	//	import { searchTagList } from "@/api/search.js";
	import VideoComponent from "../util/VideoComponent.vue";
	export default {
		name: "ChallengeSearchResult",
		components: {
			//			ChallengeList,
			VideoComponent,
		},
		props: {
			search: String,
		},
		methods: {
			pushSubscribe() {},
		},
		created() {
			this.challenges = [
				//data.	challengeList: 에 한번 들어갔다고 치고 data뺌->challengelist만
				{
					challenge_no: 3,
					user_no: 3,
					challenge_start: "2022-02-13T13:25:25.000+00:00",
					challenge_end: "2022-02-22T00:00:00.000+00:00",
					challenge_access: "PUBLIC",
					challenge_type: "VIDEO",
					challenge_title: "#mockingbird",
					challenge_content: "chirp chirp :D #meow",
					challenge_official: false,
					challenge_report: 0,
					//여기서 postList 담아서 부르는거 가능한건가..?
					postList: [
						{
							post_no: 3,
							user_no: 3,
							user_nickname: "m1",
							user_title: null,
							challenge_no: 3,
							chalenge_title: "#mockingbird",
							file_no: 3,
							file_path: "output/media/20220213/222545",
							file_savedname: "5275796f4178151eaa83f4b0b6b712ff",
							post_content: "TestingTesting!!! #ice #life #warm",
							post_report: 0,
							post_regdate: "2022-02-13T13:25:47.000+00:00",
							post_update: "2022-02-13T13:25:47.000+00:00",
							post_like_count: 0,
							comment_count: 0,
						},
						{
							post_no: 4,
							user_no: 3,
							user_nickname: "m1",
							user_title: null,
							challenge_no: 4,
							chalenge_title: "#mockingbird",
							file_no: 4,
							file_path: "output/media/20220213/225013",
							file_savedname: "5275796f4178151eaa83f4b0b6b712ff",
							post_content: "Moose goes meow !!! #ice #life #warm",
							post_report: 0,
							post_regdate: "2022-02-13T13:50:15.000+00:00",
							post_update: "2022-02-13T13:50:15.000+00:00",
							post_like_count: 0,
							comment_count: 0,
						},
					],
				},
				{
					challenge_no: 4,
					user_no: 3,
					challenge_start: "2022-02-13T13:49:48.000+00:00",
					challenge_end: "2022-02-22T00:00:00.000+00:00",
					challenge_access: "PUBLIC",
					challenge_type: "VIDEO",
					challenge_title: "#singingdeer",
					challenge_content: "blah blah #meow",
					challenge_official: false,
					challenge_report: 0,

					postList: [
						{
							post_no: 4,
							user_no: 3,
							user_nickname: "m1",
							user_title: null,
							challenge_no: 4,
							chalenge_title: "#singingdeer",
							file_no: 4,
							file_path: "output/media/20220213/225013",
							file_savedname: "5275796f4178151eaa83f4b0b6b712ff",
							post_content: "Moose goes meow !!! #ice #life #warm",
							post_report: 0,
							post_regdate: "2022-02-13T13:50:15.000+00:00",
							post_update: "2022-02-13T13:50:15.000+00:00",
							post_like_count: 0,
							comment_count: 0,
						},
					],
				},
			];

			/*		searchTagList(
					this.searchKey,
					(response) => {
						this.challenges = response.data.data.challengeList;
					},
					(error) => {
						console.log(error);
					}
				);*/
		},
		data() {
			return {
				searchKey: {
					user_no: this.$store.state.userStore.userInfo.user_no,
					tag_content: this.search.substring(1),
				},
				itemsPerPageArray: [3, 6, 9],
				searchProp: "",
				filter: {},

				sortDesc: false,
				page: 1,

				itemsPerPage: 6,
				challenges: [],
			};
		},
	};
</script>
<style>
	video {
		object-fit: fill;
	}
	.bar {
		position: absolute;
		top: 1%;
		left: 0;
	}
	.bar-heart {
		position: absolute;
		top: 7%;
		right: 5%;
	}
	.title-block {
		display: inline-block;
		cursor: pointer;
	}

	/*	#hello:not(.on-hover) {
		opacity: 0.5;
	}

	.show-btns {

		color: rgba(255, 255, 255, 1) !important;
			color: pink !important;
		color: rgb(0, 4, 255) !important;
	}*/
</style>
