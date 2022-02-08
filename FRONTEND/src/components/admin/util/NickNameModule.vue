<template>
	<router-link
		v-if="item != `챌림픽`"
		:to="{ path: `/feed/${itemno}` }"
		style="text-decoration: none; color: inherit; mr-2"
	>
		{{ item }}
	</router-link>
	<router-link v-else to="" style="text-decoration: none; color: inherit; mr-2">
		{{ item }}
	</router-link>
</template>

<script>
	export default {
		name: "NickNameModule",
		props: {
			item: String,
			itemno: Number, //피드 번호
		},
	};
</script>

<style></style>
