import { apiInstance } from "./index.js";

const api = apiInstance();

/* user management*/
function userList(success, fail) {
  api.get(`/challympic/admin/users`).then(success).catch(fail);
}
function inactiveUser(user_no, success, fail) {
  api
    .put(`/challympic/admin/users`, JSON.stringify(user_no))
    .then(success)
    .catch(fail);
}
function challengeList(success, fail) {
  api.get(`challympic/admin/challenges`).then(success).catch(fail);
}
function deleteChallenge(challenge_no, success, fail) {
  api
    .delete(`/challympic/admin/challenges`, JSON.stringify(challenge_no))
    .then(success)
    .catch(fail);
}
function commentList(success, fail) {
  api.get(`challympic/admin/comments`).then(success).catch(fail);
}
function deleteComment(comment_no, success, fail) {
  api
    .delete(`challympic/admin/comments`, JSON.stringify(comment_no))
    .then(success)
    .catch(fail);
}
function qnaList(success, fail) {
  api.get(`challympic/admin/qna`).then(success).catch(fail);
}
function answer(qna_answer, success, fail) {
  api
    .put(`challympic/admin/qna`, JSON.stringify(qna_answer))
    .then(success)
    .catch(fail);
}
export {
  userList,
  inactiveUser,
  challengeList,
  deleteChallenge,
  commentList,
  deleteComment,
  qnaList,
  answer,
};
