import { apiInstance } from './index.js';

const api = apiInstance();

function getPostList(challengeNo, success, fail) {
  api.get(`/challympic/challenge/${challengeNo}/post`).then(success).catch(fail);
}

function createPost(challengeNo, post, success, fail) {
  console.log(post);
  api.post(`/challympic/challenge/${challengeNo}/post`,
    {
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      params: {
        post
      }
    }).then(success).catch(fail);
}

export { getPostList, createPost };