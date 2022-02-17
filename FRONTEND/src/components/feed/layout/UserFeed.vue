<template>
  <v-app>
    <v-main class="main-wrapper">
      <v-container class="user-wrapper">
        <user-profile
          :who_no="
            Number(
              decodeURIComponent(this.$router.currentRoute.path).split('/')[2]
            )
          "
          :userInfo="this.userInfo"
        ></user-profile>
      </v-container>
      <v-container>
        <v-tabs
          :background-color="$vuetify.theme.dark ? '#121212' : '#fff'"
          show-arrows
        >
          <v-tab
            v-for="item in items"
            :key="item.title"
            :to="item.link"
            class="font-weight"
          >
            {{ item.title }}
          </v-tab>
        </v-tabs>
      </v-container>
      <router-view
        :who_no="
          Number(
            decodeURIComponent(this.$router.currentRoute.path).split('/')[2]
          )
        "
      />
    </v-main>
    <fab-button></fab-button>
  </v-app>
</template>

<script>
import UserProfile from "@/components/feed/layout/UserProfile.vue";
import FabButton from "@/components/button/FabButton.vue";
import { getFeedUserInfo } from "@/api/feed.js";
export default {
  name: "UserFeed",
  components: {
    UserProfile,
    FabButton,
  },
  data() {
    return {
      items: [
        {
          title: "내가 참여한",
          link:
            "/feed/" +
            decodeURIComponent(this.$router.currentRoute.path).split("/")[2] +
            "/post",
        },
        {
          title: "내가 만든",
          link:
            "/feed/" +
            decodeURIComponent(this.$router.currentRoute.path).split("/")[2] +
            "/challenge",
        },
        {
          title: "좋아요",
          link:
            "/feed/" +
            decodeURIComponent(this.$router.currentRoute.path).split("/")[2] +
            "/like",
        },
        {
          title: "구독",
          link:
            "/feed/" +
            decodeURIComponent(this.$router.currentRoute.path).split("/")[2] +
            "/subscription",
        },
      ],
      isFollow: false,
      login_user: 0,
      userInfo: {},
    };
  },
  created() {
    this.login_user = this.$store.state.userStore.userInfo
      ? this.$store.state.userStore.userInfo.user_no
      : 0;
    getFeedUserInfo(
      decodeURIComponent(this.$router.currentRoute.path).split("/")[2],
      (response) => {
        this.userInfo = response.data.data;
      }
    );
  },
  computed: {},
};
</script>

<style scoped>
.main-wrapper {
  width: 935px;
  height: 100%;
  box-sizing: border-box;
  margin: 0 auto;
}
.user-wrapper {
  margin-top: 40px;
}
.font-weight {
  font-size: 16px;
  font-weight: bold;
}
</style>
