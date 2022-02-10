import { getPostList, createPost } from '@/api/post.js';

const postStore = {
  state: {
      postList: []
  },
  getters: {
      
  },
  mutations: {
    SET_POST_LIST(state, postList) {
      state.postList = postList;
    }
  },
  actions: {
    getPostList({ commit }, challengeNo) {
      getPostList(
        challengeNo,
        (response) => {
          const { data } = response;
          commit('SET_POST_LIST', data.data);
        },
        () => {}
      )
    },
    createPost(context, { challengeNo, post }) {
      createPost(
        challengeNo,
        post,
        () => {},
        () => {}
      );
    }
  }
}

export default postStore