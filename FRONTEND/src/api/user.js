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
  api.defaults.headers["Authorization"] = localStorage.getItem("Authorization");
  await api.get(`/challympic/user/account/${user_no}`).then(success).catch(fail);
}

/* 관심사 추가 */
function save(user_no, tag_no, success, fail) {
  api.post(`/challympic/user/interest/${user_no}`, { tag_no }).then(success).catch(fail);
}
export { join, login, confirmEmail, confirmNickname, getUser , save };