<template>
	<!-- 정보는 호버했을 때에 오버레이 씌워서 잘보이게 하기 -->
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
							height="auto"
							ref="videoContainer"
						>
							<v-carousel
								hide-delimiter-background
								delimiter-icon="mdi-minus"
								width="444px"
								height="200px"
								style="vertical-align: middle"
							>
								<!--리스트 들어가심-->
								<div
									style="
										background-color: black;
										display: table-cell;
										vertical-align: middle;
									"
								>
									<div class="holder">
										<v-carousel-item
											v-for="post in item.postList"
											:key="post.post_no"
										>
											<video-component
												class="py-2"
												v-if="item.challenge_type === 'VIDEO'"
												:post="post"
											/>
											<!-- 이미지 비율때매 넣은겁니당 -->
											<v-img
												class="responsive-media"
												v-else
												max-width="100%"
												max-height="100%"
												:src="
													'https://d3iu4sf4n4i2qf.cloudfront.net/' +
													post.file_path +
													'/' +
													post.file_savedname
												"
											/>

											<!--포스트 정보 -->
											<div class="bar">
												<v-card-title>
													<router-link
														:to="{ path: `/feed/${post.user_no}` }"
														style="text-decoration: none; color: white"
													>
														<h3
															style="
																text-shadow: 1px 1px 3px #424242;
																-webkit-text-stroke-width: 0.1px;
																-webkit-text-stroke-color: #424242;
															"
														>
															{{ post.user_nickname }}
														</h3>
													</router-link>
													<v-spacer />
												</v-card-title>

                        <v-card-subtitle
                          style="
                            text-shadow: 1px 1px 3px #424242;
                            -webkit-text-stroke-width: 0.1px;
                            -webkit-text-stroke-color: #424242;
                          "
                        >
                          <strong>
                            좋아요 {{ post.post_like_count }} 개 댓글
                            {{ post.comment_count }}개</strong
                          >
                        </v-card-subtitle>
                      </div>
                      <!-- 좋아요-->
                      <div class="bar-heart">
                        <v-btn @click="pushLike(post)" icon>
                          <v-icon
                            :color="post.isLike ? 'red' : 'grey lighten-3'"
                            size="32"
                          >
                            mdi-heart-outline
                          </v-icon>
                        </v-btn>
                      </div>
                    </v-carousel-item>
                  </div>
                </div>
              </v-carousel>
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
											<v-btn icon @click="setSubscribe(item)">
												<v-icon
													:color="item.subscription ? 'blue' : 'grey lighten-3'"
													size="32"
													>mdi-bookmark-outline</v-icon
												>
											</v-btn>
										</span>
									</v-list-item-action>
								</v-list-item>
							</v-list>
						</v-card>
					</v-col>
				</v-row>
			</template>
		</v-data-iterator>
	</v-container>
</template>
<script>
	// import ChallengeList from "../util/ChallengeList.vue";
	import { searchTagList } from "@/api/search.js";
	import { setSubscription } from "@/api/challenge.js";
	import { setLike } from "@/api/post.js";
	import VideoComponent from "../util/VideoComponent.vue";
	export default {
		name: "ChallengeSearchResult",
		components: {
			VideoComponent,
		},
		props: {
			search: String,
		},
		methods: {
			setSubscribe(challenge) {
				// console.log(challenge);
				challenge.subscription = !challenge.subscription;
				setSubscription(
					challenge.challenge_no,
					challenge.user_no,
					(response) => {
						console.log(response);
					},
					(error) => {
						console.log(error);
					}
				);
			},
			pushLike(post) {
				console.log(post);
				setLike(
					post.post_no,
					post.user_no,
					(response) => {
						console.log(response);
						// post.isLike = !post.isLike;
					},
					(error) => {
						console.log(error);
					}
				);
			},
		},
		created() {
			searchTagList(
				this.searchKey,
				(response) => {
					this.challenges = response.data.data.challengeList;
					console.log(this.challenges);
				},
				(error) => {
					console.log(error);
				}
			);
		},
		data() {
			return {
				overlay: false,
				user_no: !this.$store.state.userStore.userInfo
					? ""
					: this.$store.state.userStore.userInfo.user_no,
				searchKey: {
					user_no: this.user_no,
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
	.responsive-media {
		position: absolute;
		margin: auto;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
	}
	.v-data-footer__select {
		display: none;
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
