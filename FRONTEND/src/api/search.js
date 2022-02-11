import { apiInstance } from './index.js';

const api = apiInstance();

/* 전체 검색 for All Tags */
function getSearchList(success, fail) {
    api.get(`/challympic/search`).then(success).catch(fail); //hes saying remove this 
}
function searchTagList(tag,success, fail) { 
    api.get(`/challympic/search/tag/${tag}`).then(success).catch(fail);
}


export { getSearchList , searchTagList };