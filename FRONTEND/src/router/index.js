import Vue from "vue";
import VueRouter from "vue-router";
import Main from "../views/Main.vue";
/*검색 화면 */
import Search from "../views/Search.vue";
import ChallengeSearch from "../components/search/ChallengeRouter.vue";
import PostSearch from "../components/search/PostRouter.vue";
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
import SetInterests from "../views/user/SetInterests";
import Login from "../views/user/Login.vue";
import ChallengeDetail from "../views/ChallengeDetail.vue";

/*관리자 페이지 */
import Admin from "../views/Admin.vue";
import UserManagement from "@/components/admin/UserManagement.vue";
import ChallengeManagement from "@/components/admin/ChallengeManagement.vue";
import CommentManagement from "@/components/admin/CommentManagement.vue";
import QnAManagement from "@/components/admin/QnAManagement.vue";
import StatManagement from "@/components/admin/StatManagement.vue";

/*메인 화면 */
import Recent from "../views/Recent.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Main",
    component: Main,
    redirect: "/recent",
    children: [
      {
        path: "/recent",
        name: Recent,
        component: Recent,
      },
      {
        path: "/search/:keyword/",
        name: "Search",
        component: Search,
        redirect: "/search/challenge/:keyword",
        children: [
          {
            path: "/search/challenge/:keyword",
            name: "ChallengeSearch",
            component: ChallengeSearch,
          },
          {
            path: "/search/post/:keyword",
            name: "PostSearch",
            component: PostSearch,
          },
        ],
      },
    ],
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
    redirect: "/feed/:userNo/post",
    children: [
      {
        path: "/feed/:userNo/post",
        name: "Participated",
        component: Participated,
      },
      {
        path: "/feed/:userNo/challenge",
        name: "Created",
        component: Created,
      },
      {
        path: "/feed/:userNo/like",
        name: "Like",
        component: Like,
      },
      {
        path: "/feed/:userNo/subscription",
        name: "Subscribe",
        component: Subscribe,
      },
    ],
  },
  {
    path: "/join",
    name: "Join",
    component: Join,
  },
  {
    path: "/setInterests",
    name: "SetInterests",
    component: SetInterests,
  },
  {
    path: "/login",
    name: "Login",
    component: Login,
  },
  {
    path: "/challenge/:challengeNo",
    name: "ChallengeDetail",
    component: ChallengeDetail,
    props: true,
  },
  {
    path: "/admin/",
    name: "Admin",
    component: Admin,
    redirect: "/admin/user",
    //redirect를 stat으로 하기
    children: [
      {
        path: "/admin/user",
        name: "UserManagement",
        component: UserManagement,
      },
      {
        path: "/admin/challenge",
        name: "ChallengeManagement",
        component: ChallengeManagement,
      },
      {
        path: "/admin/comment",
        name: "CommentManagement",
        component: CommentManagement,
      },
      {
        path: "/admin/qna",
        name: "QnAManagement",
        component: QnAManagement,
      },
      {
        path: "/admin/stats",
        name: "StatManagement",
        component: StatManagement,
      },
    ],
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior(to) {
    if (to.hash) {
      return {
        selector: to.hash,
        behavior: "smooth",
      };
    }
    return { x: 0, y: 0 };
  },
});

export default router;
