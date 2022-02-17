import router from "@/router/index.js";
import jwt_decode from "jwt-decode";
import {
  login,
  join,
  confirmEmail,
  confirmNickname,
  getUser,
  changePassword,
  modifyUser,
  deleteUser,
  getInterest,
  deleteInterest,
  getSubscription,
  deleteSubscription,
  getQnA,
  registerQuestion,
  getAlertList,
  setInterests,
} from "@/api/user.js";

const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    loginFailed: false,
    userNo: "",
    userTitle: "",
    userNickname: "",
    userEmail: "",
    userInfo: null,
    tempUserEmail: "",
    filePath: "",
    fileSavedName: "",
    possibleEmail: false,
    possibleNickname: false,
    changePassword: false,
    pushAlert: true,
    listInterest: null,
    listSubscription: [""],
    listQnA: null,
    alertList: null,
  },
  getters: {},
  mutations: {
    LOGIN(state, data) {
      state.isLoggedIn = true;
      state.userNo = data.user_no;
      state.userTitle = data.user_title;
      state.userNickname = data.user_nickname;
      state.userEmail = data.user_email;
    },
    LOGIN_FAILED(state, fail) {
      state.loginFailed = fail;
    },
    LOGOUT(state) {
      state.isLoggedIn = false;
      state.userNo = 0;
      state.userInfo = null;
      state.filePath = "";
      state.fileSavedName = "";
      state.userEmail = "";
      state.listInterest = null;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
      state.filePath = userInfo.file_path;
      state.fileSavedName = userInfo.file_savedname;
    },
    CONFIRM_EMAIL(state, isDuplicated) {
      state.possibleEmail = !isDuplicated;
    },
    CONFIRM_NICKNAME(state, isDuplicated) {
      state.possibleNickname = !isDuplicated;
    },
    CHANGE_PASSWORD(state, changed) {
      state.changePassword = changed;
    },
    SET_INTERESTS(state, interests) {
      state.listInterest = interests;
    },
    SET_SUBSCRIPTION(state, subscriptions) {
      state.listSubscription = subscriptions;
    },
    SET_QNA(state, qna) {
      state.listQnA = qna;
    },
    SET_ALERT_LIST(state, alertList) {
      state.alertList = alertList;
    },
    DELETE_ALERT(state) {
      state.alertList = null;
    },
    TEMP_USER_EMAIL(state, tempUserEmail) {
      state.tempUserEmail = tempUserEmail;
    },
  },
  actions: {
    async login({ commit, dispatch }, user) {
      await login(
        user,
        (response) => {
          const { data } = response;
          if (data.success) {
            commit("LOGIN", data.data);
            commit("LOGIN_FAILED", false);
            dispatch("getAlertList", data.data.user_no);
            sessionStorage.setItem(
              "Authorization",
              response.headers["authorization"]
            );
            // router.push({ name: "Main" });
          } else {
            commit("LOGIN_FAILED", true);
          }
        },
        () => {
          commit("LOGIN_FAILED", true);
        }
      );
    },
    async join({ commit }, user) {
      await join(
        user,
        (response) => {
          const { data } = response;
          if (data.success) {
            commit("TEMP_USER_EMAIL", user.user_email);
            router.push({ name: "SetInterests" });
          }
        },
        (err) => {
          console.log(err);
        }
      );
    },
    async setInterests(state, info) {
      await setInterests(
        info.user_email,
        info.interests,
        () => {
          router.push({ name: "Login" });
        },
        (err) => {
          console.log(err);
        }
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      getUser(
        decode_token.user_no,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_USER_INFO", response.data.data);
            window.location.href = "/recent";
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async confirmEmail({ commit }, email) {
      await confirmEmail(
        email,
        (response) => {
          const { data } = response;
          commit("CONFIRM_EMAIL", data.data);
        },
        () => {}
      );
    },
    async confirmNickname({ commit }, nickname) {
      await confirmNickname(
        nickname,
        (response) => {
          const { data } = response;
          commit("CONFIRM_NICKNAME", data.data);
        },
        () => {}
      );
    },
    async changePassword({ commit }, tokenUser) {
      let token = tokenUser.token;
      let decode_token = jwt_decode(token);
      let user = {
        user_pwd: tokenUser.user_pwd,
        user_newpwd: tokenUser.user_newpwd,
      };
      await changePassword(
        decode_token.user_no,
        user,
        (response) => {
          if (response.data.code === 200) {
            commit("CHANGE_PASSWORD", true);
          } else if (response.data.code === 204) {
            commit("CHANGE_PASSWORD", false);
          }
        },
        (error) => {
          console.log("error", error);
        }
      );
    },
    async modifyUser({ commit }, tokenUser) {
      let token = tokenUser.token;
      let decode_token = jwt_decode(token);
      let user = tokenUser.file;
      await modifyUser(
        decode_token.user_no,
        user,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_USER_INFO", response.data.data);
          }
        },
        (error) => {
          console.log("error", error);
        }
      );
    },
    deleteUser({ commit }, token) {
      let decode_token = jwt_decode(token);
      deleteUser(
        decode_token.user_no,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_USER_INFO", null);
            sessionStorage.removeItem("Authorization");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getInterest({ commit }, token) {
      let decode_token = jwt_decode(token);
      await getInterest(
        decode_token.user_no,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_INTERESTS", response.data.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async deleteInterest(state, tokenTag) {
      let token = tokenTag.token;
      let decode_token = jwt_decode(token);
      let tag = tokenTag.no;
      await deleteInterest(
        decode_token.user_no,
        tag,
        () => {},
        (error) => {
          console.log("error", error);
        }
      );
    },
    async deleteSubscription(state, tokenSub) {
      let token = tokenSub.token;
      let decode_token = jwt_decode(token);
      let sub = tokenSub.no;
      await deleteSubscription(
        sub,
        decode_token.user_no,
        () => {},
        (error) => {
          console.log("error", error);
        }
      );
    },
    async getSubscription({ commit }, tokenSub) {
      let token = tokenSub.token;
      let decode_token = jwt_decode(token);
      await getSubscription(
        decode_token.user_no,
        (response) => {
          if (response.data.code == 200) {
            commit("SET_SUBSCRIPTION", response.data.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getQnA({ commit }, token) {
      let decode_token = jwt_decode(token);
      await getQnA(
        decode_token.user_no,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_QNA", response.data.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    registerQuestion({ commit }, tokenQnA) {
      let token = tokenQnA.token;
      let decode_token = jwt_decode(token);
      let qna = {
        qna_title: tokenQnA.qna_title,
        qna_question: tokenQnA.qna_question,
      };
      registerQuestion(
        decode_token.user_no,
        qna,
        (response) => {
          if (response.data.code === 200) {
            commit("SET_QNA", response.data.data.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getAlertList({ commit }, userNo) {
      getAlertList(
        userNo,
        (response) => {
          commit("SET_ALERT_LIST", response.data.data);
        },
        () => {}
      );
    },
  },
};

export default userStore;
