<template>
	<v-card
		elevation="0"
		height="100vh"
		width="100%"
		hide-overlay
		absolute
		clipped-left
	>
		<!--
		<v-card-title class="text-h5 d-flex justify-center">
	 -->
		<v-card-title class="text-h6 d-flex justify-center">
			설 정

			<v-menu bottom left>
				<template v-slot:activator="{ on, attrs }">
					<v-btn dark icon v-bind="attrs" v-on="on">
						<v-icon color="#424242">mdi-cog</v-icon>
					</v-btn>
				</template>

				<v-list>
					<v-list-item v-for="(item, i) in items" :key="i" :to="item.link">
						<v-list-item-title>{{ item.title }}</v-list-item-title>
					</v-list-item>
				</v-list>
			</v-menu>
		</v-card-title>

		<v-divider></v-divider>

		<v-card>
			<router-view />
		</v-card>
	</v-card>
</template>

<script>
	export default {
		data() {
			return {
				items: [
					{ title: "프로필 수정", link: "/mobile/user/account/3" },
					{ title: "비밀번호 변경", link: "/mobile/user/account/:userNo/pwd" },
					{
						title: "푸시 알림",
						link: "/mobile/user/account/:userNo/push-alert",
					},
					{ title: "문의하기", link: "/mobile/user/account/:userNo/qna" },
					{
						title: "회원탈퇴",
						link: "/mobile/user/account/:userNo/unsubscribe",
					},
				],
				right: null,
			};
		},
	};
</script>

<style></style>
