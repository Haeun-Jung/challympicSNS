import { apiInstance } from './index.js';

const api = apiInstance();

function getPostList(challengeNo, success, fail) {
  api.get(`/challympic/challenge/${challengeNo}/post`).then(success).catch(fail);
}

function createPost(challengeNo, post, success, fail) {
  api.headers.post['Content-Type'] = 'multipart/form-data';
  console.log(post);
  api.get(`/challympic/challenge/${challengeNo}/post`, JSON.stringify(post)).then(success).catch(fail);
}

export { getPostList, createPost };