<template>
	<v-dialog v-model="dialog" persistent scrollable max-width="400px">
		<v-card>
			<v-card-title class="lighten-2 card-title-align">
				<h3>좋아요</h3>
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
					<div
						style="text-align: center"
						v-if="!this.$store.state.userStore.userInfo"
					>
						<h4>로그인하셔야 이용할 수 있는 서비스입니다</h4>
					</div>
					<div style="text-align: center" v-else-if="likeUserList.length == 0">
						<h4>첫 좋아요를 눌러주세요!</h4>
					</div>

					<v-list-item
						v-else
						v-for="(user, idx) in likeUserList"
						:key="user.user_no"
					>
						<img
							v-if="user.user_title"
							class="medal-icon"
							src="https://cdn-icons-png.flaticon.com/512/744/744922.png"
						/>
						<v-list-item-avatar class="user-image">
							<v-img
								v-if="user.file_savedname"
								:alt="`${user.user_nickname} avatar`"
								:src="`https://d384sk7z91xokb.cloudfront.net/${user.file_path}/${user.file_savedname}`"
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
							<a
								:href="`/feed/${user.user_no}`"
								:class="[
									'text-decoration-none',
									[$vuetify.theme.dark ? 'dark-mode-text' : 'black-text'],
								]"
							>
								<v-list-item-title
									v-text="user.user_nickname"
								></v-list-item-title>
							</a>
							<!-- 클릭 간격 조절 -->
						</v-list-item-content>
						<!-- 리스트에 있는 유저와 내(=로그인 유저)가 다를 때 -->
						<v-list-item-icon v-if="login_user.user_no != user.user_no">
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
	// import { getFollowerList, getFollowingList, setFollow } from "@/api/feed.js";
	import { setFollow } from "@/api/feed.js";

	export default {
		name: "FollowLikeModal",
		props: {
			dialog: Boolean,
			likeUserList: Array,
		},
		data() {
			return {
				login_user: null,
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
		},
		created() {
			this.login_user = this.$store.state.userStore.userInfo;
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
