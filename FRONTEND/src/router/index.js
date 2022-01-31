import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main.vue";

import Account from "../components/account/Account.vue";
/*프로필 */
/* Desktop */
import UpdateUserInfo from "../components/account/profile/desktop/ProfileUpdate.vue";
import UpdateUserPassword from "../components/account/profile/desktop/PasswordUpdate.vue";
import UpdatePushAlertSetting from "../components/account/profile/desktop/PushAlert.vue";
import DeleteUser from "../components/account/profile/desktop/DeleteUser.vue";
import QnAUser from "../components/account/profile/desktop/QnA.vue";
/*Mobile */
import UpdateUserInfoMobile from "../components/account/profile/mobile/ProfileUpdate.vue";
import DeleteUserMobile from "../components/account/profile/mobile/DeleteUser.vue";
import UpdateUserPasswordMobile from "../components/account/profile/mobile/PasswordUpdate.vue";
import UpdatePushAlertSettingMobile from "../components/account/profile/mobile/PushAlert.vue";
import QnAUserMobile from "../components/account/profile/mobile/QnA.vue";

import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";


Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
  },
  {
    path: "/user/account/",
    name: "Account",
    component: Account,
    redirect : "/user/account/:userNo/",
    children: [
      {
        path: "/user/account/:userNo/",
        name: "UpdateUserInfo",
        component : UpdateUserInfo,
      },
      {
        path: "/mobile/user/account/:userNo/",
        name: "UpdateUserInfoMobile",
        component : UpdateUserInfoMobile,
      },
     
      {
        path: "/user/account/:userNo/pwd",
        name: "UpdateUserPasasword",
        component: UpdateUserPassword,
      },
      {
        path: "/mobile/user/account/:userNo/pwd",
        name: "UpdateUserPasaswordMobile",
        component: UpdateUserPasswordMobile,
      },
      {
        path: "/user/account/:userNo/push-alert",
        name: "UpdatePushAlertSetting",
        component: UpdatePushAlertSetting,
      },
      {
        path: "/mobile/user/account/:userNo/push-alert",
        name: "UpdatePushAlertSettingMobile",
        component: UpdatePushAlertSettingMobile,
      },
      {
        path: "/user/account/:userNo/unsubscribe",
        name: "DeleteUser",
        component: DeleteUser,
      },
      {
        path: "/mobile/user/account/:userNo/unsubscribe",
        name: "DeleteUserMobile",
        component: DeleteUserMobile,
      },
      {
        path: "/user/account/:userNo/qna",
        name: "QnAUser",
        component: QnAUser,
      },
      {
        path: "/mobile/user/account/:userNo/qna",
        name: "QnAUserMobile",
        component: QnAUserMobile,
      },
    ],
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
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
