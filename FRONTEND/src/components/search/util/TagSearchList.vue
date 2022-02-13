<template>
	<v-skeleton-loader
		v-if="!loaded"
		max-width="444px"
		height="360px"
		type="image, list-item-two-line"
		class="mx-auto"
	></v-skeleton-loader>
	<v-card v-else elevation="2" height="auto" width="444px">
		<div class="holder">
			<video-player
				class="video-player-box"
				ref="videoPlayer"
				height="280!important"
				:playsinline="true"
				:options="playerOptions"
			>
			</video-player>
			<!-- 포스트 정보 -->
			<div class="bar">
				<v-card-title>
					<router-link
						:to="{ path: `/feed/${post.challenger_no}` }"
						style="text-decoration: none; color: inherit"
					>
						<h3>
							{{ post.challenger }}
						</h3>
					</router-link>
					<v-spacer />
				</v-card-title>

				<v-card-subtitle>
					좋아요 {{ post.post_likes }} 개 댓글 {{ post.post_comments }}개
				</v-card-subtitle>
			</div>
			<!-- 좋아요-->
			<div class="bar-heart">
				<v-btn @click="pushLike" icon>
					<!-- <v-icon :class="{ 'show-btns': hover }" :color="transparent">
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시
						:class="{ 'show-btns': hover }"
								-->
					<v-icon :color="post.post_like ? 'red' : 'grey lighten-3'" size="32">
						mdi-heart-outline
					</v-icon>
				</v-btn>
			</div>
		</div>
		<v-card>
			<v-list two-line>
				<v-list-item>
					<v-list-item-content>
						<v-list-item-title>
							<h3 class="title-block" @click="goChallenge">
								{{ post.challenge_title }}
							</h3>
						</v-list-item-title>
						<v-list-item-subtitle>
							{{ splitContents }}
						</v-list-item-subtitle>
						<v-list-item-subtitle> {{ splitTags }}</v-list-item-subtitle>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-card>
	</v-card>
</template>

<script>
	export default {
		name: "TagSearchList",
		props: {
			post: Object,
		},
		created() {
			this.playerOptions = {
				preload: "auto",
				autoplay: true,
				muted: true,
				loop: true,
				aspectRatio: "4:3",
				sources: [
					{
						type: "application/x-mpegURL",
						src: this.post.content,
					},
				],
			};
		},
		data() {
			return {
				videoUrl: this.post.content,
				loaded: false,
				playerOptions: [],
			};
		},
		computed: {
			/*백앤드에서 태그는 내용에 포함된다고 함 */
			splitTags() {
				for (var i = 0; i < this.post.post_content.length; i++) {
					if (this.post.post_content.charAt(i) === "#") break;
				}
				return this.post.post_content.substring(i);
			},
			splitContents() {
				for (var i = 0; i < this.post.post_content.length; i++) {
					if (this.post.post_content.charAt(i) === "#") break;
				}
				if (this.post.post_content.substring(0, i) === "")
					return "포스트 내용이 없습니다.";
				else return this.post.post_content.substring(0, i);
			},
		},
		mounted: function () {
			var that = this;
			if (document.readyState === "complete") this.$set(that, "loaded", true);
			document.addEventListener("readystatechange", function () {
				if (document.readyState === "complete") that.$set(that, "loaded", true);
			});
			//	this.playerOptions.sources.src = this.post.content;
			//console.log(this.playerOptions.sources.src);
		},
		methods: {
			goPost() {
				alert("상세 페이지로 이동 where postno = " + this.post.post_no);
			},
			goChallenge() {
				const path = "/challenge/" + this.post.challenge_no;
				this.$router.push(path);
				this.$router.go();
			},
			pushLike() {
				this.post.post_like = !this.post.post_like;
				if (this.post.post_like) {
					this.post.post_likes++;
				} else {
					this.post.post_likes--;
				}
			},
			pushSubscribe() {
				alert(this.challenge.challenge_subscribe);
				//해당 챌린지 challenge.challenge_no로 구독 취소나 좋아요 ㅇ ㅛ청 보내기
				this.challenge.challenge_subscribe =
					!this.challenge.challenge_subscribe;
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
