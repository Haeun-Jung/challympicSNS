import { apiInstance } from './index.js';

const api = apiInstance();

function getChallenge(challengeNo, success, fail) {
  api.get(`/challympic/challenge/${challengeNo}`).then(success).catch(fail);
}

function confirmChallengeName(challenge_title, success, fail) {
  api.post('/challympic/challenge/confirm', JSON.stringify({ challenge_title })).then(success).catch(fail);
}

async function createChallenge(challenge, success, fail) {
  await api.get('/challympic/challenge', JSON.stringify(challenge)).then(success).catch(fail);
}

export { getChallenge, confirmChallengeName, createChallenge };