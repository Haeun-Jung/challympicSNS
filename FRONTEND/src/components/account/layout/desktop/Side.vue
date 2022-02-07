<template>
	<v-card
		height="89vh"
		width="100%"
		elevation="0"
		hide-overlay
		absolute
		clipped
		app
	>
		<v-card-title class="text-h5 d-flex justify-center">설 정</v-card-title>
		<v-divider />

		<v-list dense nav>
			<v-list-item v-for="item in items" :key="item.title" :to="item.link">
				<v-list-item-content>
					<v-list-item-title class="d-flex justify-center">{{
						item.title
					}}</v-list-item-title>
				</v-list-item-content>
			</v-list-item>
		</v-list>
	</v-card>
</template>

<script>
	export default {
		data() {
			return {
				items: [
					{ title: "프로필 수정", link: "/user/account/3" },
					{ title: "비밀번호 변경", link: "/user/account/:userNo/pwd" },
					{ title: "푸시 알림", link: "/user/account/:userNo/push-alert" },
					{ title: "문의하기", link: "/user/account/:userNo/qna" },
					{ title: "회원탈퇴", link: "/user/account/:userNo/unsubscribe" },
				],
				right: null,
			};
		},
	};
</script>

<style></style>
