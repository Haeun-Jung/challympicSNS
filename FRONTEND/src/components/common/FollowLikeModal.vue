<template>
	<v-dialog v-model="dialog" persistent scrollable max-width="400px">
		<v-card>
			<v-card-title class="lighten-2 card-title-align">
				<span v-if="this.type === 'follower'">팔로워</span>
				<span v-else-if="this.type === 'following'">팔로잉</span>
				<span v-else>좋아요</span>
				<v-btn
					class="cancel-btn"
					absolute
					top
					right
					icon
					@click="$emit('close-dialog')"
				>
					<v-icon>mdi-close</v-icon>
				</v-btn>
			</v-card-title>

			<v-divider />

			<div>
				<v-list class="overflow-y-auto">
					<v-list-item v-for="(user, idx) in followList" :key="user.user_no">
						<img
							v-if="user.user_title != '도전자'"
							class="medal-icon"
							src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
						/>
						<v-list-item-avatar class="user-image">
							<v-img
								v-if="user.user_profile"
								:alt="`${user.user_nickname} avatar`"
								:src="`https://d384sk7z91xokb.cloudfront.net/${user.user_profile}`"
							></v-img>
							<v-img
								v-else
								:alt="`${user.user_nickname} avatar`"
								src="../../assets/profile.png"
							></v-img>
						</v-list-item-avatar>

						<v-list-item-content>
							<!-- 배지.. -->
							<!-- <v-img v-if="user.title" max-width="20" src="https://cdn-icons-png.flaticon.com/512/744/744922.png"/> -->
							<v-list-item-subtitle
								v-text="user.user_title"
								src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
							></v-list-item-subtitle>
							<v-list-item-title>
								<span @click="goFeed(user.user_no)" style="cursor: pointer">{{
									user.user_nickname
								}}</span>
							</v-list-item-title>
						</v-list-item-content>
						<!-- 리스트에 있는 유저와 내(=로그인 유저)가 다를 때 -->
						<v-list-item-icon v-show="login_user != user.user_no">
							<v-btn
								v-if="!user.follow"
								@click="follow(user.user_no, idx)"
								color="#3396F4"
								class="white--text rounded-xl"
								small
							>
								팔로우
							</v-btn>
							<v-btn
								v-else
								@click="follow(user.user_no, idx)"
								color="#3396F4"
								class="rounded-xl"
								small
								outlined
							>
								팔로잉
							</v-btn>
						</v-list-item-icon>
					</v-list-item>
				</v-list>
			</div>
		</v-card>
	</v-dialog>
</template>

<script>
	import { getFollowerList, getFollowingList, setFollow } from "@/api/feed.js";
	export default {
		name: "FollowLikeModal",
		props: {
			type: String,
			dialog: Boolean,
			login_user: Number,
			who_no: Number,
			followerCnt: Number,
			followingCnt: Number,
		},
		data() {
			return {
				followList: [],
			};
		},
		methods: {
			follow(userNo, idx) {
				// 팔로우 API 요청 보내기
				// 해당 유저에 대한 isFollowing 값 변경
				setFollow(this.login_user, userNo, () => {
					// who_no: 피드 주인
					// login_user: 로그인 한 유저
					// userNo: 팔로잉/팔로워 리스트에 있는 유저
					// 내 피드에서 팔로워/팔로잉 목록 볼 때만 부모 요소에서 값 변경 필요
					if (this.who_no === this.login_user) {
						// 이미 팔로우 중이라면 => 언팔로우
						if (this.followList[idx].follow) {
							this.$emit("decrementFollowingCnt");
							console.log("팔로잉 -1");
						} else {
							// 팔로우 중이 아니라면 => 팔로우
							this.$emit("incrementFollowingCnt");
							console.log("팔로잉 +1");
						}
					}
					this.followList[idx].follow = !this.followList[idx].follow;
				});
			},
			goFeed(userno) {
				this.$emit("close-dialog");
				this.dialog = false;
				location.href = "/feed/" + userno;
			},
		},
		watch: {
			dialog() {
				if (this.dialog === true) {
					getFollowerList(this.who_no, this.login_user, (response) => {
						if (this.type === "following") {
							console.log("following");
							this.followList = response.data.data;
						}
					});
					getFollowingList(this.who_no, this.login_user, (response) => {
						// console.log(response.data)
						if (this.type === "follower") {
							console.log("follower");
							this.followList = response.data.data;
						}
					});
				}
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
	.card-title-align {
		display: flex;
		justify-content: center;
	}
	.cancel-btn {
		margin-top: -4px;
		margin-right: -4px;
	}
	.cancel-icon {
		width: 16px;
	}
	.follow-btn {
		color: #fff;
	}
	.user-image {
		margin-right: 16px;
	}
	.medal-icon {
		position: absolute;
		top: 0.5rem;
		left: 2.3rem;
		width: 20px;
		height: 20px;
		z-index: 1;
	}
</style>
