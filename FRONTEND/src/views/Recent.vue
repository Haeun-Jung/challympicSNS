<template>
	<div class="recent">
    <post-item
      v-for="post in recentPostList"
      :post="post"
      :type="post.challenge_type"
      :key="post.post_no"
    ></post-item>
	</div>
</template>

<script>
  import PostItem from "@/components/post/PostItem.vue";
	export default {
    name: "Recent",
    components: { PostItem },
		data() {
			return {};
		},
    computed: {
      recentPostList() {
        return this.$store.state.postStore.recentPostList;
      }
    },
    created() {
      this.$store.dispatch('postStore/getRecentPostList', this.$store.state.userStore.userInfo.user_no);
    }
	};
</script>

<style scoped>
</style>
