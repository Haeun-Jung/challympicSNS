import { apiInstance } from "./index.js";

const api = apiInstance();

function getChallengeList(success, fail) {
  api.get("/challympic/challenge").then(success).catch(fail);
}

function getChallenge(challengeNo, success, fail) {
  api.get(`/challympic/challenge/${challengeNo}`).then(success).catch(fail);
}

function confirmChallengeName(challenge_title, success, fail) {
  api
    .post("/challympic/challenge/confirm", JSON.stringify({ challenge_title }))
    .then(success)
    .catch(fail);
}

async function createChallenge(challenge, success, fail) {
  await api
    .post("/challympic/challenge", JSON.stringify(challenge))
    .then(success)
    .catch(fail);
}

async function setSubscription(challengeNo, userNo, success, fail) {
  api
    .post(
      `/challympic/challenge/${challengeNo}/subscribe/${userNo}`,
      JSON.stringify({ challengeNo, userNo })
    )
    .then(await success)
    .catch(fail);
}

function removeSubscription(challengeNo, userNo, success, fail) {
  api
    .delete(
      `/challympic/challenge/${challengeNo}/subscribe/${userNo}`,
      JSON.stringify({ challengeNo, userNo })
    )
    .then(success)
    .catch(fail);
}

function isSubscribe(challengeNo, userNo, success, fail) {
  api
    .get(
      `/challympic/challenge/${challengeNo}/subscribe/${userNo}`,
      JSON.stringify({ challengeNo, userNo })
    )
    .then(success)
    .catch(fail);
}

export {
  getChallengeList,
  getChallenge,
  confirmChallengeName,
  createChallenge,
  setSubscription,
  removeSubscription,
  isSubscribe,
};
