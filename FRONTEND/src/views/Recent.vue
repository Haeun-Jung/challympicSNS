<template>
	<div>
		최근 등록된 포스트나 챌린지 넣을 공간 !-> 추천 알고리즘 비디오 대신 넣기로함
    <post-item
      v-for="post in recentPostList"
      :post="post"
      :type="challenge.challenge_type"
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

<style></style>
