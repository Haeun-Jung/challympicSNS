import { apiInstance } from './index.js';

const api = apiInstance();

function getChallenge(challengeNo, success, fail) {
  api.get(`/challympic/challenge/${challengeNo}`).then(success).catch(fail);
}

function confirmChallengeName(challengeName, success, fail) {
  api.post('/challympic/confirm', JSON.stringify(challengeName)).then(success).catch(fail);
}

function createChallenge(challenge, success, fail) {
  api.get('/challympic/challenge', JSON.stringify(challenge)).then(success).catch(fail);
}

export { getChallenge, confirmChallengeName, createChallenge };