<template>
	<!-- 하나의 챌린지 리스트
			challenge.challenge_no
		}}/post)롤 받기
		{{ challenge }} 프롭스 받았음 -> api(/challenge/{{
		위에 바 없는게 더 이쁜거 같아서 캐러솔 밑에 이거 뺌 <v-system-bar lights-out> </v-system-bar>
    
     -->
	<v-skeleton-loader
		v-if="!loaded"
		max-width="444px"
		height="360px"
		type="image, list-item-two-line"
		class="mx-auto"
	></v-skeleton-loader>
	<v-card
		v-else
		elevation="2"
		max-width="444px"
		class="mx-auto"
		ref="videoContainer"
	>
		<v-carousel
			hide-delimiter-background
			delimiter-icon="mdi-minus"
			height="auto"
			width="444px"
		>
			<div class="holder">
				<v-carousel-item v-for="(item, idx) in post" :key="item.id">
					<div>
						<!--	<video-player
							class="video-player-box"
							ref="videoPlayer"
							height="280!important"
							:playsinline="true"
							:options="playerOptions(item.content)"
						>
						</video-player>-->
						<video-component :videoinfo="item" />
					</div>

					<!-- 포스트 정보 -->
					<div class="bar">
						<v-card-title>
							<router-link
								:to="{ path: `/feed/${item.challenger_no}` }"
								style="text-decoration: none; color: inherit"
							>
								<h3>
									{{ item.challenger }}
								</h3>
							</router-link>
							<v-spacer />
						</v-card-title>

						<v-card-subtitle>
							좋아요 {{ item.post_likes }} 개 댓글 {{ item.post_comments }}개
						</v-card-subtitle>
					</div>
					<!-- 좋아요-->
					<div class="bar-heart">
						<v-btn @click="pushLike(item.id, idx)" icon>
							<!-- <v-icon :class="{ 'show-btns': hover }" :color="transparent">
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시
								:class="{ 'show-btns': hover }"
								-->
							<v-icon
								:color="item.post_like ? 'red' : 'grey lighten-3'"
								size="32"
							>
								mdi-heart-outline
							</v-icon>
						</v-btn>
					</div>
				</v-carousel-item>
			</div>
		</v-carousel>

		<v-list two-line>
			<v-list-item>
				<v-list-item-content>
					<v-list-item-title>
						<h3 class="title-block" @click="goChallenge">
							{{ challenge.challenge_title }}
						</h3>
					</v-list-item-title>
					<v-list-item-subtitle>
						{{ challenge.challenge_content }}
					</v-list-item-subtitle>
					<v-list-item-subtitle>
						{{ challenge.challenge_tag }}
					</v-list-item-subtitle>
				</v-list-item-content>
				<v-list-item-action>
					<span>
						<!-- v-if user_subscribe == true -> filled 된 애로 보여주기-->
						<v-btn icon @click="pushSubscribe">
							<v-icon
								:color="
									challenge.challenge_subscribe ? 'blue' : 'grey lighten-3'
								"
								size="32"
								>mdi-bookmark-outline</v-icon
							>
						</v-btn>
					</span>
				</v-list-item-action>
			</v-list-item>
		</v-list>
	</v-card>
</template>

<script>
	import VideoComponent from "./VideoComponent.vue";
	export default {
		components: { VideoComponent },
		name: "ChallengeList",
		props: {
			challenge: Object,
		},
		data() {
			return {
				overlay: false,
				playing: false,

				post: [],
				loaded: false,
			};
		},
		mounted: function () {
			var that = this;
			if (document.readyState === "complete") this.$set(that, "loaded", true);
			document.addEventListener("readystatechange", function () {
				if (document.readyState === "complete") that.$set(that, "loaded", true);
			});
		},
		created() {
			this.post = [
				//얘는 this.obj1 = response.data.data.challengeList.postList?이런식으로 가져오면되지 않을까?;
				/*{
					post_no: 1,
					post_like: true,
					challenge_title: "챌린지 제목 1",
					post_content: "내용에 태그가 포함??#프론트에서#분리해서뿌리나요?",
					post_likes: 300,
					post_comments: 3,
					challenger: "김싸피",
					challenger_no: 3, //challenger no ? 넘어오는거에 따라서 수정
					content:
					
						"http://d384sk7z91xokb.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
				},
				{
					post_no: 2,
					post_like: false,
					challenge_title: "아이스버킷챌린지",
					post_content: "저도..참여해봤어요..#차가움#공익#공식",
					challenger_no: 3, //challenger no ? 넘어오는거에 따라서 수정
					post_likes: 0,
					post_comments: 3,
					challenger: "이싸피",
					content:
						"http://d384sk7z91xokb.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
				},
				{
					post_no: 3,
					post_like: false,
					challenge_title: "미라클모닝#미이라크을",
					post_content: "#아침#ㅜㅜ",
					post_likes: 15,
					post_comments: 30,
					challenger: "박싸피",
					challenger_no: 3, //challenger no ? 넘어오는거에 따라서 수정
					content:
					
						"http://d384sk7z91xokb.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
				},
				{
					post_no: 4,
					post_like: false,
					challenge_title: "김싸피덤벼",
					post_content: "얍얍#1대1",
					challenger_no: 3, //challenger no ? 넘어오는거에 따라서 수정
					post_likes: 3521565,
					post_comments: 345465,
					challenger: "오싸피",
					content:
					
						"http://d384sk7z91xokb.cloudfront.net/output/media/20220209/092119/video/c88b277171a94d9c3ac7e32f82bb9d93.m3u8",
				},*/
			];
		},
		methods: {
			playerOptions(url) {
				this.playerOptions.sources.src = url;
				this.playerOptions.sources.type = "application/x-mpegURL";
			},
			makeUrl(original_url) {
				return original_url + "#t=0.9";
			},
			hello() {
				alert(this.getWidth);
			},
			pushLike(postid, arrIdx) {
				//arrInx는 화면 바로 바꾸는용도
				//postid로 좋아요 요청
				console.log(postid);
				this.post[arrIdx].post_like = !this.post[arrIdx].post_like;
				if (this.post[arrIdx].post_like) {
					this.post[arrIdx].post_likes++;
				} else {
					this.post[arrIdx].post_likes--;
				}
			},
			goChallenge() {
				/*alert(
					"챌린지 상세 페이지로 이동 where challenge_no:" +
						this.challenge.challenge_no
				);*/
				const path = "/challenge/" + this.challenge.challenge_no;
				this.$router.push(path);
			},
			pushSubscribe() {
				/*alert(this.challenge.challenge_subscribe);*/
				//해당 챌린지 challenge.challenge_no로 구독 취소나 좋아요 ㅇ ㅛ청 보내기
				this.challenge.challenge_subscribe =
					!this.challenge.challenge_subscribe;
			},
			play(idx) {
				this.playing = !this.playing;
				alert(this.playing);
				if (this.playing) {
					alert("play");
					alert(this.$refs.myvideo);
					this.$refs.myvideo[idx].play();
				} else {
					this.$refs.myvideos.pause();
				}
			},
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
