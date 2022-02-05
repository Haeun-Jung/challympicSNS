<template>
  <v-app>
    <v-main class="main-wrapper">
        <v-container class="user-wrapper">
            <user-profile></user-profile>
        </v-container>
        <v-container>
            <v-tabs :background-color="$vuetify.theme.dark ? '#121212' : '#fff'" show-arrows>
              <v-tab v-for="item in items" :key="item.title" :to="item.link" class="font-weight">
                {{ item.title }}
              </v-tab>
            </v-tabs>
        </v-container>
        <router-view/>
    </v-main>
    <fab-button></fab-button>
  </v-app>

</template>

<script>
import UserProfile from './UserProfile.vue';
import FabButton from '../../button/FabButton.vue';

export default {
  name: "UserFeed",
  components: {
    UserProfile,
    FabButton
  },
  data: ()=> ({
    items: [
      { title: "내가 참여한", link: "/feed/:userNo/post" },
      { title: "내가 만든", link: "/feed/:userNo/challenge" },
      { title: "좋아요", link: "/feed/:userNo/like" },
      { title: "구독", link: "/feed/:userNo/subscription" },
    ]
  }),
}
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
  font-weight: bold;
}
</style>