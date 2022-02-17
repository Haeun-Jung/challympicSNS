import { getChallenge, confirmChallengeName } from "@/api/challenge.js";

const challengeStore = {
  namespaced: true,
  state: {
    challenge: {},
    possibleChallengeName: false,
    confirmedButImpossibleName: false,
    currentChallengeNo: 0,
  },
  getters: {},
  mutations: {
    SET_CHALLENGE_LIST(state, challengeList) {
      state.challengeList = challengeList.map((challenge) => {
        return {
          text: challenge.challenge_title,
          value: challenge.challenge_no,
        };
      });
    },
    SET_CHALLENGE(state, challenge) {
      state.challenge = {
        ...challenge,
        challenge_start: challenge.challenge_start
          .split("T")[0]
          .replace(/-/g, "."),
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
    },
  },
  actions: {
    getChallenge({ commit }, challengeNo) {
      getChallenge(
        challengeNo,
        (response) => {
          const { data } = response;
          commit("SET_CHALLENGE", data.data);
        },
        () => {}
      );
    },
    confirmChallengeName({ commit }, challengeName) {
      if (challengeName.length < 1) {
        commit("REFUSE_CHALLENGE_NAME");
        return;
      }
      confirmChallengeName(
        challengeName,
        (response) => {
          const { data } = response;
          if (data.success) {
            commit("CONFIRM_CHALLENGE_NAME");
          } else {
            commit("REFUSE_CHALLENGE_NAME");
          }
        },
        () => {}
      );
    },
  },
};

export default challengeStore;
