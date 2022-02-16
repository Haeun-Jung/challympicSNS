<template>
  <v-container>
    <!--
		프롭스로 {{ search }} 태그-> 해당 태그를 포함하는 포스트 가져오기
		 api(/challenge/{challengeNo}/post) 로
		
		-->
    <v-data-iterator
      :items="posts"
      :footer-props="{
        'items-per-page-options': [5, 10, 15, 20],
      }"
      :items-per-page="6"
    >
      <template v-slot:default="props">
        <v-row>
          <v-col
            v-for="item in props.items"
            :key="item.name"
            cols="12"
            sm="6"
            md="6"
            lg="4"
          >
            <tag-search-list :post="item" />
          </v-col>
        </v-row>
      </template>
    </v-data-iterator>
  </v-container>
</template>

<script>
import TagSearchList from "../util/TagSearchList.vue";
import { searchTagList } from "@/api/search.js";

export default {
  name: "TagSearchResult",
  components: {
    TagSearchList,
  },
  props: {
    search: String,
  },
  created() {
    const user_no = this.$store.state.userStore.userInfo.user_no;
    const tag_content = this.search.substring(1);
    console.log("user_no : " + this.$store.state.userStore.userInfo.user_no);
    searchTagList(
      { user_no, tag_content },
      (response) => {
        this.posts = response.data.data.postList;
        console.log(this.posts);
        console.log(this.searchKey);
      },
      (error) => {
        console.log(error);
      }
    );
  },
  data() {
    return {
      itemsPerPageArray: [3, 6, 9],
      searchProp: "",
      filter: {},
      sortDesc: false,
      page: 1,
      user_no: !this.$store.state.userStore.userInfo
        ? ""
        : this.$store.state.userStore.userInfo.user_no,
      searchKey: {
        user_no: this.user_no,
        tag_content: this.search.substring(1),
      },
      itemsPerPage: 6,
      posts: [],
    };
  },
};
</script>
