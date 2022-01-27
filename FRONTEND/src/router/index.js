import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main.vue";
import Account from "../views/Account.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/user/account/:userNo",
    name: "Account",
   component : Account,
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
