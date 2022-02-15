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
      // 로그인 안 해도 action 호출 가능하도록 처리
      let userNo = "";
      if (this.$store.state.userStore.userInfo) {
        userNo = this.$store.state.userStore.userInfo.user_no;
      }
      this.$store.dispatch('postStore/getRecentPostList', userNo);
    }
	};
</script>

<style scoped>
</style>
