<template>
  <v-list dense nav>
    <v-list-item>
      <v-card-subtitle>
        <h2>인기 급상승</h2>
      </v-card-subtitle>
    </v-list-item>
    <!-- export 인기 급상승 list -->
    <v-list-item-content>
      <h4 class="main-side-menu-contents">이부분 맨 마지막에 리스트로 삽입</h4>
    </v-list-item-content>
    <v-divider />
    <v-list-item>
      <v-card-subtitle>
        <h2>내 관심사</h2>
      </v-card-subtitle>
    </v-list-item>
    <v-list-item-content>
      <v-if user> </v-if>
      <v-else>
        <h4 class="main-side-menu-contents">
          관심사를 등록하고 관련 영상을 찾아보세요!
        </h4>
        <div class="main-side-menu-btn-container">
          <v-btn color="primary" class="main-side-menu-btn"> 로그인 </v-btn>
        </div>
      </v-else>
    </v-list-item-content>
    <v-divider />
    <v-list-item>
      <v-card-subtitle>
        <h2>이달의 도전왕</h2>
      </v-card-subtitle>
    </v-list-item>
    <v-list-item-content>
      <v-if user> </v-if>
      <v-else>
        <h4 class="main-side-menu-contents">도전왕 유저 리스트</h4>
      </v-else>
    </v-list-item-content>
    <div class="main-side-switch-container">
      <v-list-item>
        <v-card-subtitle>
          <h3>다크모드</h3>
        </v-card-subtitle>
        <v-switch
          v-model="dark"
          color="#3396F4"
          class="dark-theme-switch"
          @click="$vuetify.theme.dark = !$vuetify.theme.dark"
        ></v-switch>
      </v-list-item>
    </div>
  </v-list>
</template>

<script>
export default {
  name: "SideContents",
  data() {
    return {
      dark: false,
    };
  },
};
</script>

<style scoped>
.main-side-switch-container {
  display: flex;
  justify-content: space-between;
}
.main-side-menu-contents {
  margin: 1%;
  padding-left: 7%;
  padding-right: 7%;
}
.main-side-menu-btn-container {
  margin: 5%;
  padding-left: 30%;
}
</style>
