import { getChallenge, confirmChallengeName, createChallenge } from '@/api/challenge.js';
import { createPost } from '../../api/post';

const challengeStore = {
  state: {
    challenge: {},
    possibleChallengeName: false,
    confirmedButImpossibleName: false,
  },
  getters: {

  },
  mutations: {
    SET_CHALLENGE(state, challenge) {
      state.challenge = challenge;
    },
    CONFIRM_CHALLENGE_NAME(state) {
      state.possibleChallengeName = true;
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
        () => { }
      )
    },
    confirmChallengeName({ commit }, challengeName) {
      if (challengeName.length === 1) {
        return;
      }
      confirmChallengeName(
        challengeName,
        (response) => {
          const { data } = response;
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
        user_no: rootState.userStore.userNo,
        challengers: challenge.challengers,
        challenge_title: challenge.challengeName,
        challenge_content: challenge.description,
        challenge_end: challenge.endDate,
        challenge_type: challenge.fileType,
        title_name: challenge.titleName
      }
      createChallenge(
        challengeItem,
        (response) => {
          const { data } = response;
          console.log("챌린지 생성!");
          createPost(
            data.data.challenge_no,
            post,
            () => {
              console.log("포스트 생성!");
            },
            () => {
              // 아직 백에서 challenge_no가 안 넘어와서 생성 실패하는 상태입니다.
              console.log("포스트 생성 실패");
            }
          )

        },
        () => { }
      )
    }
  }
}

export default challengeStore