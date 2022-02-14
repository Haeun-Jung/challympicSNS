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
      <v-col md="5" class="hidden-md-and-down" />
      <v-col sm="1" class="hidden-sm-and-up" />
      <v-chip-group>
        <v-chip
          v-for="tag in interests"
          class="ma-2 recommendation-chips"
          :key="tag.id"
          :value="tag"
          text-color="#3396F4"
          :href="`/search/${encodeURIComponent(tag.name)}`"
        >
          <!--:to="{ path: '/search/' + `${encodeURIComponent(tag.name)}` }"-->
          {{ tag.name }}
        </v-chip>
      </v-chip-group>
      <!-- 데스크탑 정렬 -->
      <v-col md="4" class="hidden-md-and-down" />
      <v-col sm="1" class="hidden-sm-and-up" />
    </v-row>
  </v-toolbar>
</template>

<script>
export default {
  name: "TagBar",
  methods: {},
  data() {
    return {
      interests: [
        //사용자 태그
        {
          id: 1,
          name: "운동",
        },
        {
          id: 2,
          name: "Food",
        },
        {
          id: 3,
          name: "Art",
        },
        {
          id: 4,
          name: "Shopping",
        },
      ],
    };
  },
};
</script>

<style scoped>
.meow {
  padding: 0;
}
</style>
