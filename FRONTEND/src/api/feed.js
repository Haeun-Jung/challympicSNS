import { apiInstance } from "./index.js";

const api = apiInstance();

function checkFollow(user_no, follow_follower_no, success, fail) {
  api
    .get(`/challympic/${user_no}/follow/${follow_follower_no}`)
    .then(success)
    .catch(fail);
}

function setFollow(user_no, follow_follower_no, success, fail) {
  console.log("setFollow 실행");
  api.defaults.headers["Authorization"] =
    sessionStorage.getItem("Authorization");
  api
    .post(`/challympic/user/${user_no}/follow`, { follow_follower_no })
    .then(success)
    .catch(fail);
}

function getFollowCnt(user_no, success, fail) {
  api.get(`/challympic/${user_no}/follow`).then(success).catch(fail);
}

function getFollowerList(user_no, login_user, success, fail) {
  api
    .get(`/challympic/${user_no}/follower/${login_user}`)
    .then(success)
    .catch(fail);
}

function getFollowingList(user_no, login_user, success, fail) {
  api
    .get(`/challympic/${user_no}/following/${login_user}`)
    .then(success)
    .catch(fail);
}

function getUserMadePost(user_no, success, fail) {
  api.get(`/challympic/feed/${user_no}/post`).then(success).catch(fail);
}

function getFeedUserInfo(user_no, success, fail) {
  api.get(`/challympic/feed/${user_no}`).then(success).catch(fail);
}

function getUserMadeChallege(user_no, success, fail) {
  api.get(`/challympic/feed/${user_no}/challenge`).then(success).catch(fail);
}

function getUserSubscriptionChallenge(user_no, success, fail) {
  api.get(`/challympic/feed/${user_no}/subscription`).then(success).catch(fail);
}

function getUserLikedPost(user_no, success, fail) {
  api.get(`/challympic/feed/${user_no}/like`).then(success).catch(fail);
}

export {
  checkFollow,
  setFollow,
  getFollowCnt,
  getFollowerList,
  getFollowingList,
  getUserMadePost,
  getFeedUserInfo,
  getUserMadeChallege,
  getUserSubscriptionChallenge,
  getUserLikedPost,
};
