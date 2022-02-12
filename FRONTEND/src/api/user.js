import { apiInstance } from './index.js';

const api = apiInstance();

/* 회원가입 */
function join(user, success, fail) {
  api.post('/challympic/join', JSON.stringify(user)).then(success).catch(fail);
}

/* 로그인 */
async function login(user, success, fail) {
  await api.post('/challympic/login', JSON.stringify(user)).then(success).catch(fail);
}

/* 이메일 중복 체크 */
function confirmEmail(user_email, success, fail) {
  api.post('/challympic/confirm/email', JSON.stringify({user_email})).then(success).catch(fail);
}

/* 닉네임 중복 체크 */
function confirmNickname(user_nickname, success, fail) {
  api.post('/challympic/confirm/nickname', JSON.stringify({user_nickname})).then(success).catch(fail);
}

/* 회원 정보 가져오기(토큰 헤더에 보내기) */
async function getUser(user_no, success, fail) {
  api.defaults.headers["Authorization"] = sessionStorage.getItem("Authorization");
  await api.get(`/challympic/user/account/${user_no}`).then(success).catch(fail);
}

/* 회원 정보 수정 */
function modifyUser(user_no, user, success, fail) {
  api.put(`/challympic/user/account/${user_no}`, user, { headers: { 'Content-Type': 'multipart/form-data' } }).then(success).catch(fail);
}

/* 회원 비밀번호 수정 */
function changePassword(user_no, user, success, fail) {
  api.put(`/challympic/user/account/${user_no}/pwd`, JSON.stringify(user)).then(success).catch(fail);
}

/* 회원 탈퇴 */
function deleteUser(user_no, success, fail) {
  api.delete(`/challympic/user/account/${user_no}`).then(success).catch(fail);
}

/* 관심사 조회 */
function getInterest(user_no, success, fail) {
  api.get(`/challympic/user/interest/${user_no}`).then(success).catch(fail);
}

/* 관심사 삭제 */
function deleteInterest(user_no, tag_no, success, fail) {
  api.delete(`/challympic/user/interest/${user_no}/${tag_no}`).then(success).catch(fail);
}

/* 구독 삭제 */
function deleteSubscription(challenge_no, user_no, success, fail) {
  api.delete(`/challympic/challenge/${challenge_no}/subscribe/${user_no}`).then(success).catch(fail);
}

/* 문의하기 조회 */
function getQnA(user_no, success, fail) {
  api.get(`/challympic/user/${user_no}/qna`).then(success).catch(fail);
}

/* 문의하기 등록 */
function registerQuestion(user_no, qna, success, fail) {
  api.post(`/challympic/user/${user_no}/qna`, JSON.stringify(qna)).then(success).catch(fail);
}

/* 관심사 추가 */
function save (user_no, tag ,success, fail) {
  api.post(`/user/interest/${user_no}`,JSON.stringify(tag)).then(success).catch(fail);
}

export { 
  join, 
  login, 
  confirmEmail, 
  confirmNickname,
   getUser, 
   modifyUser,
   changePassword, 
   deleteUser,
   getInterest,
   deleteInterest,
   deleteSubscription,
   getQnA,
   registerQuestion,
   save,
  };