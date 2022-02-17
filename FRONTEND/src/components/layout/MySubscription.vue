<template>
  <!-- 로그인 X -->
  <div v-if="!isLoggedIn" class="title-width">
    <v-card-subtitle>
      <h2>구독</h2>
    </v-card-subtitle>
    <v-list-item-content>
      <h4 class="main-side-menu-contents">챌린지를 구독해보세요!</h4>
    </v-list-item-content>
  </div>
  <!-- 로그인 O -->
  <v-list v-else class="title-width">
    <v-list-group v-model="active" @click="toggle">
  <template v-if="!active && listSubscription.length == 0" v-slot:activator class="title-background">
      <v-card-subtitle
        class="title-width"
      >
        <h2>구독</h2>
      </v-card-subtitle>
      </template>
      <template v-else v-slot:activator class="title-background">
        <v-card-subtitle class="title-width">
          <h2>구독</h2>
        </v-card-subtitle>
      </template>
      <v-chip
        class="subscription-one"
        v-for="challenge in listSubscription"
        :key="challenge.challenge_no"
        :value="challenge"
        v-model="challenge.isOpen"
        color="primary"
        outlined
        close
		@click="goChallengePage(challenge.challenge_no)"
        @click:close="remove(challenge.challenge_no)"
      >
        {{ challenge.challenge_title }}
      </v-chip>
    </v-list-group>
  </v-list>
</template>


<script>
import { mapState, mapActions } from "vuex";
// import { getSubscription } from "@/api/user.js";
const userStore = "userStore";
export default {
  name: "MySubscription",
  props: { search: String },
  computed: {
    ...mapState(userStore, ["userInfo", "listSubscription"]),
    isLoggedIn() {
      return this.$store.state.userStore.isLoggedIn;
    },
  },
  data() {
    return {
    	active: true,
    };
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    remove(no) {
      // this.listsubscription.splice(idx, 1);
      // this.index++; //카운트 해줘야 다음 태그 제대로 지워짐
      // // 이렇게 하고, 페이지 refresh 해서 태그 다시 받아와야함.....
      // this.listsubscription = [...this.listsubscription];
      this.$store.dispatch("userStore/deleteSubscription", {
        no,
        token: sessionStorage.getItem("Authorization"),
      });
      setTimeout(() => {
        this.$store.dispatch("userStore/getSubscription", {
          token: sessionStorage.getItem("Authorization"),
        });
      }, 300);
    },
    toggle() {
      this.active = true;
    },
	goChallengePage(challengeNo) {
		window.location.href = `/challenge/${challengeNo}`;
	},
  },
  created() {
    if (this.$store.state.userStore.userInfo) {
      this.$store.dispatch("userStore/getSubscription", {
        token: sessionStorage.getItem("Authorization"),
      });
    }
  },
};
</script>


<style scoped>
.title-width {
  width: 100%;
  background: transparent;
  padding-right: 0px;
}
.main-side-menu-contents {
  text-align: center;
  margin: 1%;
  padding-left: 5%;
  padding-right: 7%;
}
.subscription-one {
  margin: 3px;
}
</style>

