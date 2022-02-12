import { apiInstance } from './index.js';

const api = apiInstance();

function createComment(post_no, user_no, comment_content, success, fail) {
  api.post(`/challympic/challenge/post`, JSON.stringify({post_no, user_no, comment_content})).then(success).catch(fail);
}

export { createComment };