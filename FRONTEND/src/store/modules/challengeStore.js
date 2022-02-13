import { getChallenge, confirmChallengeName, createChallenge } from '@/api/challenge.js';
import { createPost } from '../../api/post';

const challengeStore = {
  namespaced: true,
  state: {
    challenge: {},
    possibleChallengeName: false,
    confirmedButImpossibleName: false,
  },
  getters: {

  },
  mutations: {
    SET_CHALLENGE(state, challenge) {

      state.challenge = {
        ...challenge,
        challenge_start: challenge.challenge_start.split("T")[0].replace(/-/g, "."),
        challenge_end: challenge.challenge_end.split("T")[0].replace(/-/g, "."),
      };
    },
    CONFIRM_CHALLENGE_NAME(state) {
      state.possibleChallengeName = true;
      state.confirmedButImpossibleName = false;
    },
    REFUSE_CHALLENGE_NAME(state) {
      state.possibleChallengeName = false;
      state.confirmedButImpossibleName = true;
    },
    RESET_POSSIBLE_STATUS(state) {
      state.possibleChallengeName = false;
    }
  },
  actions: {
    getChallenge({ commit }, challengeNo) {
      getChallenge(
        challengeNo,
        (response) => {
          const { data } = response;
          commit('SET_CHALLENGE', data.data);
        },
        () => {
          console.log("챌린지 가져오기 실패");
        }
      )
    },
    confirmChallengeName({ commit }, challengeName) {
      if (challengeName.length < 1) {
        commit('REFUSE_CHALLENGE_NAME');
        return;
      }
      confirmChallengeName(
        challengeName,
        (response) => {
          const { data } = response;
          console.log(data);
          if (data.success) {
            commit('CONFIRM_CHALLENGE_NAME');
          } else {
            commit('REFUSE_CHALLENGE_NAME');
          }
        },
        () => { }
      )
    },
    createChallengeWithPost({ rootState }, { challenge, post }) {
      const challengeItem = {
        user_no: rootState.userStore.userInfo.user_no,
        challengers: challenge.challengers.length > 1 ? challenge.challengers.split() : [],
        challenge_title: challenge.challengeName,
        challenge_content: challenge.description,
        challenge_end: challenge.endDate,
        challenge_type: challenge.fileType,
        title_name: challenge.titleName
      }

      createChallenge(
        challengeItem,
        (response) => {
          console.log("챌린지 생성!");
          if(response.data.code == 200){
            createPost(
              response.data.data.challenge_no,
              post,
              () => {
                console.log("포스트 생성!");
              },
              () => {
                // 아직 백에서 challenge_no가 안 넘어와서 생성 실패하는 상태입니다.
                console.log("포스트 생성 실패");
              }
            )
          }
        },
        () => { }
      )
    },
  }
}

export default challengeStore