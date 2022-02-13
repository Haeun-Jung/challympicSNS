import { apiInstance } from './index.js';

const api = apiInstance();

/* 전체 검색 for All Tags */
function getSearchList(success, fail) {
    api.get(`/challympic/search`).then(success).catch(fail); //hes saying remove this 
}
function searchTagList(tag,success,user_no, fail) { 
    api.get(`/challympic/search/tag/${tag}`,{ user_no }).then(success).catch(fail);
}


export { getSearchList , searchTagList };