<template>
  <v-app>
    <!-- 모바일은 사이드 바가 없기 때문에 패딩 조절해줘야함 & span주면 키워드 길때 내려감-->
    <v-container fluid class="search-control-box-mobile hidden-sm-and-up">
      <v-list-item-title id="search-keyword">
        {{ keyword }}
      </v-list-item-title>
      <v-radio-group v-model="row" row>
        <v-radio
          label="챌린지"
          value="challenge"
          @click="callChallengeRouter"
        ></v-radio>
        <v-radio label="포스트" value="post"></v-radio>
      </v-radio-group>
      <div>
        <tag-search-result :search="keyword" />
      </div>
    </v-container>
    <!--desktop-->
    <v-container fluid class="search-control-box-desktop hidden-md-and-down">
      <v-radio-group v-model="row" row>
        <span>
          <v-list-item-title id="search-keyword">
            {{ keyword }}
          </v-list-item-title>
        </span>
        <v-radio
          label="챌린지"
          value="challenge"
          @click="callChallengeRouter"
        ></v-radio>
        <v-radio label="포스트" value="post"></v-radio>
      </v-radio-group>
      <div>
        <tag-search-result :search="keyword" />
      </div>
    </v-container>
  </v-app>
</template>

<script>
import TagSearchResult from "./desktop/TagSearchResult.vue";
export default {
  components: {
    TagSearchResult,
  },
  data() {
    return {
      row: "post",
    };
  },
  props: { search: String },
  watch: {
    $route(to, from) {
      if (to.path != from.path) {
        /* router path가 변경될 때마다 텍스트 리프레쉬. */
        this.keyword = "#" + this.$router.currentRoute.path.substring(8);
        this.$router.go();
      }
    },
    keyword: function () {
      this.keyword = "#" + this.$router.currentRoute.path.substring(8);
    },
  },
  computed: {
    keyword() {
      const temp = decodeURIComponent(this.$router.currentRoute.path);
      const chars = temp.split("/");
      //	return "#" + temp.substring(13);
      return "#" + chars[3];
    },
  },
  /* 페이지가 로딩되자마자 keyword 확인하기 */
  methods: {
    callChallengeRouter() {
      var pathKey = this.keyword.replace("#", "");
      var newPath = "/search/challenge/" + pathKey;
      window.location.replace(newPath);
    },
  },
};
</script>

<style></style>
