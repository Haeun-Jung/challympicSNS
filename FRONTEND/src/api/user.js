import { apiInstance } from './index.js';

const api = apiInstance();

function join(user, success, fail) {
  api.post('/challympic/join', JSON.stringify(user)).then(success).catch(fail);
}

async function login(user, success, fail) {
  await api.post('/challympic/login', JSON.stringify(user)).then(success).catch(fail);
}

function confirmEmail(user_email, success, fail) {
  api.post('/challympic/confirm/email', JSON.stringify({user_email})).then(success).catch(fail);
}

function confirmNickname(user_nickname, success, fail) {
  api.post('/challympic/confirm/nickname', JSON.stringify({user_nickname})).then(success).catch(fail);
}

export { join, login, confirmEmail, confirmNickname };