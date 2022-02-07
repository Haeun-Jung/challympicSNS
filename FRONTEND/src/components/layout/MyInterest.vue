<template>
	<div>
		<v-card-subtitle>
			<h2>내 관심사</h2>
		</v-card-subtitle>
		<v-list-item-content>
			<!-- v-if  login 처리-->
			<h4 v-if="user" class="main-side-menu-contents">관심사 목록</h4>

			<h4 v-else class="main-side-menu-contents">
				관심사를 등록하고 관련 영상을 찾아보세요!
			</h4>
			<div class="main-side-menu-btn-container">
				<v-btn
					@click="clickLoginBtn"
					color="primary"
					class="main-side-menu-btn"
				>
					로그인
				</v-btn>
			</div>
		</v-list-item-content>
	</div>
</template>

<script>
	export default {
		name: "MyInterest",
		data() {
			return {
				isLoggedIn: false,
				user: false, //dummy data
			};
		},
		methods: {
			clickLoginBtn() {
				this.$router.push("/login");
			},
		},
	};
</script>

<style scoped>
	.main-side-menu-contents {
		margin: 1%;
		padding-left: 5%;
		padding-right: 7%;
	}
	.main-side-menu-btn-container {
		margin: 5%;
		padding-left: 30%;
	}
</style>
