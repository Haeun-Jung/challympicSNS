<template>
  <!--
	<v-toolbar dense fixed elevation="0"  app>
	<v-app-bar flat dense style="top: 8%" elevation="0" app>
	<v-app-bar flat dense style= "top: 600px" app elevation="0" class="meow">
	-->
  <v-toolbar
    :color="$vuetify.theme.dark ? 'grey darken-4' : '#fafafa'"
    dense
    fixed
    elevation="0"
    ><!-- 추천 태그 리스트 뿌리기ㅣ 이부분 어차피 나중에 template써서 다시 해야하니 지금은 관상용으로만 냅두기 -->
    <v-row>
      <v-col class="hidden-md-and-down" />
      <v-col class="hidden-sm-and-up" />
      <v-chip-group>
        <v-chip
          v-for="tag in tagList"
          class="s recommendation-chips"
          :key="tag.tag_no"
          :value="tag"
          text-color="#3396F4"
          @click="moveTag(tag.tag_content)"
        >
          <!--:to="{ path: '/search/' + `${encodeURIComponent(tag.name)}` }"-->
          {{ tag.tag_content }}
        </v-chip>
      </v-chip-group>
      <!-- 데스크탑 정렬 -->
      <v-col class="hidden-md-and-down" />
      <v-col class="hidden-sm-and-up" />
    </v-row>
  </v-toolbar>
</template>

<script>
import { getRecommend } from "@/api/search.js";
export default {
  name: "TagBar",
  created() {
	if(this.$store.state.userStore.userInfo){
		const userNo = this.$store.state.userStore.userInfo.user_no;
		getRecommend(
		userNo,
		(response) => {
			console.log(response);
			this.tagList = response.data.data.tagList;
		},
		(error) => {
			console.log(error);
		}
		);
	}
  },
  methods: {
    moveTag(tag_content) {
      const subTag = tag_content.substring(1);
      this.$router.push(`/search/${encodeURIComponent(subTag)}`);
    },
  },
  data() {
    return {
      tagList: [],
    };
  },
};
</script>

<style scoped>
.meow {
  padding: 0;
}
</style>
