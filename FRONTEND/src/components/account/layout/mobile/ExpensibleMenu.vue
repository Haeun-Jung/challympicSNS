<template>
  <v-card width="100%" elevation="0" height="100vh" app>
    <!--
		<v-card-title class="text-h5 d-flex justify-center">
	 -->
    <v-card-title class="mt-2 text-h6 d-flex justify-center">
      설정
      <v-menu bottom origin="center center" transition="scale-transition">
        <template v-slot:activator="{ on, attrs }">
          <v-btn dark icon v-bind="attrs" v-on="on">
            <v-icon color="#424242">mdi-cog</v-icon>
          </v-btn>
        </template>

        <v-list>
          <v-list-item v-for="(item, i) in items" :key="i" :to="item.link">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-card-title>

    <v-divider></v-divider>

    <v-card elevation="0">
      <router-view />
    </v-card>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          title: "프로필 수정",
          link:
            "/mobile/user/account/" +
            this.$store.state.userStore.userInfo.user_no,
        },
        {
          title: "비밀번호 변경",
          link:
            "/mobile/user/account/" +
            this.$store.state.userStore.userInfo.user_no +
            "/pwd",
        },
        {
          title: "푸시 알림",
          link:
            "/mobile/user/account/" +
            this.$store.state.userStore.userInfo.user_no +
            "/push-alert",
        },
        {
          title: "문의하기",
          link:
            "/mobile/user/account/" +
            this.$store.state.userStore.userInfo.user_no +
            "/qna",
        },
        {
          title: "회원탈퇴",
          link:
            "/mobile/user/account/" +
            this.$store.state.userStore.userInfo.user_no +
            "/unsubscribe",
        },
      ],
      right: null,
    };
  },
};
</script>

<style></style>
