<template>
	<!-- 하나의 챌린지 리스트
			호버했을 때 : 해당 챌린지 바로가기 ㄱㄱ ? or 포스트 바로가기 ?
     -->
	<v-card elevation="2" max-width="444" class="mx-auto">
		<div class="holder">
			<video
				ref="myvideo"
				width="100%;!important"
				height="280px;!important"
				controls="controls"
				preload="metadata"
				:src="videoUrl"
			></video>
			<!-- 포스트 정보 -->
			<div class="bar">
				<v-card-title>
					<h3>{{ post.challenger }}</h3>
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
								v-if 문 추가해서 이미 하트 눌렀으면 빨갛게 표시-->
					<v-icon
						:class="{ 'show-btns': hover }"
						:color="post.post_like ? 'red' : 'grey lighten-3'"
						size="32"
					>
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
							<h3>{{ post.challenge_title }}</h3>
							<div class="bar-heart">
								<v-btn @click="goPost"> 더보기 </v-btn>
							</div>
						</v-list-item-title>
						<v-list-item-subtitle>
							{{ splitContents }}
						</v-list-item-subtitle>
						<v-list-item-subtitle> {{ splitTags }}</v-list-item-subtitle>
					</v-list-item-content>
					<v-list-item-action>
						<span> </span>
						<!--누르면 챌린지 상세 페이지로 이동
						-->
					</v-list-item-action>
					<v-list-item-contnet> </v-list-item-contnet>
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
		data() {
			return {
				videoUrl: this.post.content + "#t=0.9",
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
		methods: {
			goPost() {
				alert("상세 페이지로 이동 where postno = " + this.post.post_no);
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
	#app iframe {
		/*width: 60%;
		height: 100%;
		margin-left: 8%;
		height: 500px;
        */
		/*padding-right: 45%;*/
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
		position: absolute; /* or absolute */
		top: 43%;
		left: 48%;
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
	#hello:not(.on-hover) {
		opacity: 0.5;
	}

	.show-btns {
		/*
		color: rgba(255, 255, 255, 1) !important;
			color: pink !important;*/
		color: rgb(0, 4, 255) !important;
	}
</style>
