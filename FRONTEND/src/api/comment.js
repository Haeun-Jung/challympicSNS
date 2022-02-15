import { apiInstance } from "./index.js";

const api = apiInstance();

function createComment(post_no, user_no, comment_content, success, fail) {
  // api.post(`/challympic/challenge/post`, JSON.stringify({post_no, user_no, comment_content})).then(success).catch(fail);
  api
    .post(
      `/challympic/comment`,
      JSON.stringify({ post_no, user_no, comment_content })
    )
    .then(success)
    .catch(fail);
}

function updateComment(comment_no, comment_content, post_no, success, fail) {
  api
    .put(
      `/challympic/comment`,
      JSON.stringify({ comment_no, comment_content, post_no })
    )
    .then(success)
    .catch(fail);
}

function deleteComment(comment_no, post_no, success, fail) {
  api
    .delete(`/challympic/comment`, JSON.stringify({ comment_no, post_no }))
    .then(success)
    .catch(fail);
}

function commentLike(user_no, comment_no, success, fail) {
  api
    .post(`/challympic/comment/like`, JSON.stringify({ comment_no, user_no }))
    .then(success)
    .catch(fail);
}

function commentReport(comment_no, success, fail) {
  api
    .put(`/challympic/report/comment`, JSON.stringify({ comment_no }))
    .then(success)
    .catch(fail);
}

export {
  createComment,
  updateComment,
  deleteComment,
  commentLike,
  commentReport,
};
