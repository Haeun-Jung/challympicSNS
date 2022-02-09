import router from '@/router/index.js';
import { login, join, confirmEmail, confirmNickname } from '@/api/user.js';

const userStore = {
  state: {
      isLoggedIn: false,
      loginFailed: false,
      userNo: '',
      userTitle: '',
      userNickname: '',
      userEmail: '',
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
        (err) => {
          console.log(err);
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

export default userStore