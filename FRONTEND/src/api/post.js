import { apiInstance } from './index.js';

const api = apiInstance();

function getPostList(challengeNo, userNo, success, fail) {
  api.post(`/challympic/challenge/post`, JSON.stringify({challengeNo, userNo})).then(success).catch(fail);
}

function createPost(challengeNo, post, success, fail) {
  api.post(`/challympic/challenge/${challengeNo}/post`, post,
    {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }).then(success).catch(fail);
}

// update, delete는 api만 있고 호출은 아직 작성 안 된 상태입니다.
function updatePost(challengeNo, postNo, post, success, fail) {
  api.put(`/challympic/challenge/${challengeNo}/post/${postNo}`, JSON.stringify({post})).then(success).catch(fail);
}

function deletePost(postNo, success, fail) {
  api.delete(`/challympic/post/${postNo}`).then(success).catch(fail);
}

function getLikeList(postNo, success, fail) {
  api.get(`challympic/post/${postNo}/like`).then(success).catch(fail);
}

export { getPostList, createPost, updatePost, deletePost, getLikeList };