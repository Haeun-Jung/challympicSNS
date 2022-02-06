<template>
	<!-- 하나의 챌린지 리스트
			challenge.challenge_no
		}}/post)롤 받기
		{{ challenge }} 프롭스 받았음 -> api(/challenge/{{
		위에 바 없는게 더 이쁜거 같아서 캐러솔 밑에 이거 뺌 <v-system-bar lights-out> </v-system-bar>
    
     -->
	<v-card elevation="2" max-width="444" class="mx-auto">
		<v-system-bar lights-out>
			<!--누르면 챌린지 상세 페이지로 이동-->
			<v-row class="justify-end">
				<v-btn
					color="blue lighten-2"
					depressed
					plain
					tile
					:ripple="false"
					dense
					@click="goChallenge"
				>
					<v-list-item-subtitle>챌린지 바로가기</v-list-item-subtitle>
				</v-btn>
			</v-row>
		</v-system-bar>
		<v-carousel
			hide-delimiter-background
			delimiter-icon="mdi-minus"
			height="280px"
		>
			<div class="holder">
				<v-carousel-item v-for="(item, idx) in post" :key="item.id">
					<!-- 동영상 재생 -->
					<video
						ref="myvideo"
						width="100%;!important"
						height="280px;!important"
						controls="controls"
						preload="metadata"
						:src="item.content"
					></video>
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
						<h3>{{ challenge.challenge_title }}</h3>
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
	export default {
		name: "ChallengeList",
		props: {
			challenge: Object,
		},
		data() {
			return {
				overlay: false,
				playing: false,
				post: [
					{
						post_no: 1,
						post_like: true,
						challenge_title: "챌린지 제목 1",
						post_content: "내용에 태그가 포함??#프론트에서#분리해서뿌리나요?",
						post_likes: 300,
						post_comments: 3,
						challenger: "김싸피",
						challenger_no: 3, //challenger no ? 넘어오는거에 따라서 수정
						content:
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"https://caiogondim.github.io/vertical-video-with-side-blur/example/vertical-video.mov",
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
							"https://caiogondim.github.io/vertical-video-with-side-blur/example/vertical-video-1.mp4",
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
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"https://caiogondim.github.io/vertical-video-with-side-blur/example/vertical-video.mov",
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
							/*확장자로 받는 경우 : controls 썸네일은 0.8초로 하자..*/
							"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
					},
				],
			};
		},
		methods: {
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
	/*	#app iframe {
		padding-right: 50%;
		position: relative;
	}
	#hello {
		transition: opacity 0.4s ease-in-out;
	}
	.holder {
		position: relative;
	}
	.play-box {
		position: absolute; 
		top: 43%;
		left: 48%;
	}*/
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
	/*	#hello:not(.on-hover) {
		opacity: 0.5;
	}

	.show-btns {
	
		color: rgba(255, 255, 255, 1) !important;
			color: pink !important;
		color: rgb(0, 4, 255) !important;
	}*/
</style>
