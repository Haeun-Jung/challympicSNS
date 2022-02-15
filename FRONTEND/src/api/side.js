import { apiInstance } from "./index.js";

const api = apiInstance();

/* 이달의 도전왕 */
function getChampions(success, fail) {
  api.get(`/challympic/champions`).then(success).catch(fail);
}

/* 인기 급상승 */
function getTrend(success, fail) {
  api.get(`/challympic/search/trending`).then(success).catch(fail);
}

export { getChampions, getTrend };
