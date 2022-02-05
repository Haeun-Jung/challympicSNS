import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main.vue";
/*검색 화면 */
import Search from "../components/search/Search.vue";
/*프로필 */
import Account from "../components/account/Account.vue";
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

/*피드*/
import UserFeed from "../components/feed/layout/UserFeed.vue";
import Participated from "../components/feed/Participated.vue";
import Created from "../components/feed/Created.vue";
import Like from "../components/feed/Like.vue";
import Subscribe from "../components/feed/Subscribe.vue";

/*로그인 및 회원가입*/
import Join from "../views/user/Join.vue";
import Login from "../views/user/Login.vue";
import ChallengeDetail from "../views/ChallengeDetail.vue";


Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
    children: [
      {
        path: "/search/:keyword/",
        name: "Search",
        component:Search,
      }
    ]
  },
  {
    path: "/user/account/",
    name: "Account",
    component: Account,
    redirect: "/user/account/:userNo/",
    children: [
      {
        path: "/user/account/:userNo/",
        name: "UpdateUserInfo",
        component: UpdateUserInfo,
      },
      {
        path: "/mobile/user/account/:userNo/",
        name: "UpdateUserInfoMobile",
        component: UpdateUserInfoMobile,
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
    path: "/feed/:userNo/",
    name: "UserFeed",
    component: UserFeed,
    redirect : "/feed/:userNo/post",
    children: [
      {
        path: "/feed/:userNo/post",
        name: "Participated",
        component : Participated,
      },
      {
        path: "/feed/:userNo/challenge",
        name: "Created",
        component : Created,
      },
      {
        path: "/feed/:userNo/like",
        name: "Like",
        component : Like,
      },
      {
        path: "/feed/:userNo/subscription",
        name: "Subscribe",
        component : Subscribe,
      },
    ]
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
     path: "/challenge/:challengeNo",
   // path: "/challenge",
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
