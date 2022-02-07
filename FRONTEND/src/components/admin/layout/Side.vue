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
		name: "AdminSide",
		data() {
			return {
				items: [
					{ title: "회원 관리", link: "/admin/user/" },
					{ title: "챌린지 관리", link: "/admin/challenge/" },
					{ title: "포스트 관리", link: "/admin/post/" },
					{ title: "댓글 관리", link: "/admin/comment/" },
					{ title: "문 의 ? ", link: "" },
				],
				right: null,
			};
		},
	};
</script>

<style></style>
