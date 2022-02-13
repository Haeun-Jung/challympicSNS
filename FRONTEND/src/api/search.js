import { apiInstance } from './index.js';

const api = apiInstance();

/* 전체 검색 for All Tags */
function getSearchList(success, fail) {
    api.get(`/challympic/search`).then(success).catch(fail); //hes saying remove this 
}
/*태그 검색 결과 */
function searchTagList(searchKey,success, fail) { 
    api.post(`/challympic/search/tag`,JSON.stringify(searchKey)).then(success).catch(fail);
}


export { getSearchList , searchTagList };