import router from '@/router/index.js';
import jwt_decode from "jwt-decode";
import { login, join, confirmEmail, confirmNickname, getUser } from '@/api/user.js';

const userStore = {
  namespaced: true,
  state: {
      isLoggedIn: false,
      loginFailed: false,
      userNo: '',
      userTitle: '',
      userNickname: '',
      userEmail: '',
      userInfo: null,
      possibleEmail: false,
      possibleNickname: false,
  },
  getters: {
      
  },
  mutations: {
      LOGIN(state, data) {
        state.isLoggedIn = true;
        state.userNo = data.user_no;
        state.userTitle = data.user_title;
        state.userNickname = data.user_nickname;
        state.userEmail = data.user_email;
      },
      LOGIN_FAILED(state) {
        state.loginFailed = true;
      },
      LOGOUT(state) {
        state.isLoggedIn = false;
      },
      SET_USER_INFO: (state, userInfo) => {
        state.userInfo = userInfo;
      },
      CONFIRM_EMAIL(state, isDuplicated) {
        state.possibleEmail = !isDuplicated;
      },
      CONFIRM_NICKNAME(state, isDuplicated) {
        state.possibleNickname = !isDuplicated;
      }
  },
  actions: {
    async login({ commit }, user) {
      await login(
        user,
        (response) => {
          const { data } = response;
          if (data.success) {
            commit('LOGIN', data);
            localStorage.setItem('Authorization', response.headers['authorization']);
            router.push({ name: 'Main'});
          }
        },
        () => {
          commit('LOGIN_FAILED');
        }
      )
    },
    async join(state, user) {
      await join(
        user,
        (response) => {
          const { data } = response;
          if (data.success) {
            router.push({ name: 'Login'});
          }
        },
        (err) => {
          console.log(err);
          console.log("회원가입 실패");
        }
      )
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      getUser(
        decode_token.user_no,
        (response) => {
          if (response.status === 200) {
            console.log(response.data.data);
            commit("SET_USER_INFO", response.data.data);
          } else {
            console.log("유저 정보 없음!!");
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
          commit('CONFIRM_EMAIL', data.data);
        },
        () => {}
      )
    },
    async confirmNickname({ commit }, nickname) {
      await confirmNickname(
        nickname,
        (response) => {
          const { data } = response;
          commit('CONFIRM_NICKNAME', data.data);
        },
        () => {}
      )
    }
  }
}

export default userStore;