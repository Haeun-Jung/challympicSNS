import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main.vue";
import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";
import ChallengeDetail from "../views/ChallengeDetail.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    // path: "/challenge/:challengeId",
    path: "/challenge",
    name: "ChallengeDetail",
    component: ChallengeDetail,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
