<template>
	<div class="recent">
    <post-item
      v-for="post in recentPostList"
      :post="post"
      :type="post.challenge_type"
      :key="post.post_no"
      :user="user"
    ></post-item>
	</div>
</template>

<script>
  import PostItem from "@/components/post/PostItem.vue";
	export default {
    name: "Recent",
    components: { PostItem },
		data() {
			return {
        user: null,
      };
		},
    computed: {
      recentPostList() {
        return this.$store.state.postStore.recentPostList;
      }
    },
    created() {
      if(!this.$store.state.userStore.userInfo){
        this.$store.dispatch('postStore/getRecentPostList');
      } else {
        this.$store.dispatch('postStore/getRecentPostList', this.$store.state.userStore.userInfo.user_no);
        this.user = this.$store.state.userStore.userInfo;
      }
    }
	};
</script>

<style scoped>
</style>
