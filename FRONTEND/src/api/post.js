import { apiInstance } from './index.js';

const api = apiInstance();

/* 최신 포스트 목록 */
<<<<<<< HEAD
async function getRecentPostList(user_no, success, fail) {
  // --------------------------
  api.get(`/challympic/main/recent/post`, JSON.stringify({user_no})).then(await success).catch(fail);
=======
function getRecentPostList(user_no, success, fail) {
  api.get('/challympic/main/recent/post', JSON.stringify({user_no})).then(success).catch(fail);
>>>>>>> 0a096243828c9d6e78199c8100ffe916f65e242a
}

/* 챌린지에 대한 포스트 목록 */
function getPostList(challenge_no, user_no, success, fail) {
  api.post(`/challympic/challenge/post`, JSON.stringify({challenge_no, user_no})).then(success).catch(fail);
}

/* 포스트 작성 */
async function createPost(challengeNo, post, success, fail) {
<<<<<<< HEAD
=======
  
  console.log(post);
  
>>>>>>> 0a096243828c9d6e78199c8100ffe916f65e242a
  api.post(`/challympic/challenge/${challengeNo}/post`, post,
  {
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  }).then(await success).catch(fail);
}

// update, delete는 api만 있고 호출은 아직 작성 안 된 상태입니다.
/* 포스트 수정 */
function updatePost(challengeNo, postNo, post, success, fail) {
  api.put(`/challympic/challenge/${challengeNo}/post/${postNo}`, JSON.stringify({post})).then(success).catch(fail);
}

/* 포스트 삭제 */
function deletePost(postNo, success, fail) {
  api.delete(`/challympic/post/${postNo}`).then(success).catch(fail);
}

/* 포스트 좋아요한 유저 목록 */
function getLikeList(postNo, userNo, success, fail) {
  api.get(`challympic/post/${postNo}/like/${userNo}`).then(success).catch(fail);
}

export {
  getRecentPostList,
  getPostList,
  createPost,
  updatePost,
  deletePost,
  getLikeList
};