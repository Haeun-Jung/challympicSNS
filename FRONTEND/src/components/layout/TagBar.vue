<template>
  <v-toolbar dense fixed elevation="0" outlined app>
    <!-- 추천 태그 리스트 뿌리기ㅣ 이부분 어차피 나중에 template써서 다시 해야하니 지금은 관상용으로만 냅두기 -->
    <v-row>
      <v-col md="5" class="hidden-md-and-down" />
      <!--모바일 화면일 때 가운데로 밀어넣을 수 있는 공간 삽입하기-->
      <v-chip class="ma-2" color="#3396F4"> 요리 </v-chip>

      <v-chip class="ma-2" color="secondary"> 운동 </v-chip>
    </v-row>
  </v-toolbar>
</template>

<script>
export default {
  name: "TagBar",
};
</script>

<style></style>
